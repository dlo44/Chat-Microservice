package edu.msudenver.chat.trade;



import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/chat/trade")

public class TradeController {
    @Autowired
    private TradeService tradeService;
    @Autowired
    private TradeRequestRepository tradeRequestRepository;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TradeRequest> createTradeRequest (@RequestBody TradeRequest tradeRequest){

        try {
            return new ResponseEntity<>(tradeService.createRequest(tradeRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{playerId}", produces = "application/json")
    public ResponseEntity<List<TradeRequest>> getTrades (@PathVariable Long playerId){
        try {
            return ResponseEntity.ok(tradeService.getTrades(playerId));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path = "/{tradeId}", produces = "application/json")
    public ResponseEntity<TradeRequest> getTradeRequest(@PathVariable Long tradeId){
        try {
            TradeRequest tradeRequest = tradeService.getTrade(tradeId);
            return new ResponseEntity<>(tradeRequest, tradeRequest == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/{tradeId}/{playerId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TradeRequest> addItemToTrade(@PathVariable Long tradeId, @PathVariable Long playerId,
                                                                     @RequestBody Long itemId, @RequestBody int quantity){
        try {
            if (!tradeRequestRepository.findByTradeId(tradeId).getStatus().equals("active")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (playerId == tradeRequestRepository.findByTradeId(tradeId).getOfferingPlayerId()) {
                return new ResponseEntity<>(tradeService.addItemToOfferingTrade(tradeId, playerId, itemId, quantity), HttpStatus.CREATED);
            } else if (playerId == tradeRequestRepository.findByTradeId(tradeId).getReceivingPlayerId()){
                return new ResponseEntity<>(tradeService.addItemToReceivingTrade(tradeId, playerId, itemId, quantity), HttpStatus.CREATED);
            }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{tradeId}/{playerId}/{tradeItemsId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TradeRequest> updateItemQuantity(@PathVariable Long tradeId, @PathVariable Long playerId, @PathVariable Long tradeItemsId,
                                                           @RequestBody int quantity){

        try {
            if (!tradeRequestRepository.findByTradeId(tradeId).getStatus().equals("active")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (playerId == tradeRequestRepository.findByTradeId(tradeId).getOfferingPlayerId() ||
                    playerId == tradeRequestRepository.findByTradeId(tradeId).getReceivingPlayerId()) {
                return new ResponseEntity<>(tradeService.updateItemQuantity(tradeId, playerId, tradeItemsId, quantity), HttpStatus.OK);
            } else {return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/{tradeId}/{playerId}/accept")
    public ResponseEntity<HttpStatus> acceptTrade(@PathVariable Long tradeId, @PathVariable Long playerId){
        try {
            if (!tradeRequestRepository.findByTradeId(tradeId).getStatus().equals("active")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(tradeService.acceptTrade(tradeId, playerId) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path = "/{tradeId}/reject")
    public ResponseEntity<HttpStatus> rejectTrade(@PathVariable Long tradeId){
        try {
            return ResponseEntity.ok(tradeService.rejectTrade(tradeId) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping(path = "/{tradeId}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long tradeId){
        try {
            return new ResponseEntity<>(tradeService.deleteTrade(tradeId) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }
}
