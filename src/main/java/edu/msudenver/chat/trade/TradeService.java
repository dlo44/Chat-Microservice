package edu.msudenver.chat.trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TradeService {
    @Autowired private TradeRequestRepository tradeRequestRepository;
    @Autowired private OfferingTradeItemsRepository offeringTradeItemsRepository;
    @Autowired private ReceivingTradeItemsRepository receivingTradeItemsRepository;

    @PersistenceContext
    protected EntityManager entityManager;
    @Transactional
    public TradeRequest createRequest(TradeRequest tradeRequest) {
         tradeRequest = tradeRequestRepository.saveAndFlush(tradeRequest);
         entityManager.refresh(tradeRequest);
         return tradeRequest;
    }

    public List<TradeRequest> getTrades(Long player) {
        return tradeRequestRepository.findAllByOfferingPlayerIdOrReceivingPlayerId(player, player);
    }

    public TradeRequest getTrade(Long tradeId) {
        try {
            return tradeRequestRepository.findByTradeId(tradeId);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TradeRequest addItemToOfferingTrade(Long tradeId, Long playerId, Long itemId, int quantity){

        Long offeringPlayer = tradeRequestRepository.findByTradeId(tradeId).getOfferingPlayerId();

        OfferingTradeItems offeringTradeItems = new OfferingTradeItems(tradeId, itemId, quantity);

        offeringTradeItemsRepository.save(offeringTradeItems);
        return tradeRequestRepository.findByTradeId(tradeId);
    }
    public TradeRequest addItemToReceivingTrade(Long tradeId, Long playerId, Long itemId, int quantity){

        Long receivingPlayer = tradeRequestRepository.findByTradeId(tradeId).getReceivingPlayerId();
        ReceivingTradeItems receivingTradeItems = new ReceivingTradeItems(tradeId, itemId,quantity);
        receivingTradeItemsRepository.save(receivingTradeItems);
        return tradeRequestRepository.findByTradeId(tradeId);
    }

    public TradeRequest updateItemQuantity(Long tradeId, Long playerId, Long tradeItemId, int quantity){
        Long offeringPlayer = tradeRequestRepository.findByTradeId(tradeId).getOfferingPlayerId();
        Long receivingPlayer = tradeRequestRepository.findByTradeId(tradeId).getReceivingPlayerId();
        if (quantity !=0){
            if(playerId == offeringPlayer){
                offeringTradeItemsRepository.findByTradeItemsId(tradeItemId).setQuantity(quantity);
            }
            else {
                receivingTradeItemsRepository.findByTradeItemsId(tradeItemId).setQuantity(quantity);
            }
        }else {
            if(playerId == offeringPlayer){
                offeringTradeItemsRepository.delete(offeringTradeItemsRepository.findByTradeItemsId(tradeItemId));
            } else {
                receivingTradeItemsRepository.delete(receivingTradeItemsRepository.findByTradeItemsId(tradeItemId));
            }
        }
        tradeRequestRepository.findByTradeId(tradeId).setReceivingPlayerAccepted(false);
        tradeRequestRepository.findByTradeId(tradeId).setOfferingPlayerAccepted(false);
        return tradeRequestRepository.findByTradeId(tradeId);
    }

    public boolean acceptTrade(Long tradeId, Long playerId){
        Long offeringPlayer = tradeRequestRepository.findByTradeId(tradeId).getOfferingPlayerId();
        Long receivingPlayer = tradeRequestRepository.findByTradeId(tradeId).getReceivingPlayerId();
        if (playerId == offeringPlayer){
            tradeRequestRepository.findByTradeId(tradeId).setOfferingPlayerAccepted(true);
            return true;
        } else if (playerId == receivingPlayer) {
            tradeRequestRepository.findByTradeId(tradeId).setReceivingPlayerAccepted(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean rejectTrade(Long tradeId){
        try {
            if(tradeRequestRepository.existsById(tradeId)){
                tradeRequestRepository.findByTradeId(tradeId).setStatus("rejected");
                return true;
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteTrade(Long tradeId) {
        try {
            if(tradeRequestRepository.existsById(tradeId)) {
                tradeRequestRepository.deleteById(tradeId);
                return true;
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}
