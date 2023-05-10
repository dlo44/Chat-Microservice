package edu.msudenver.chat.trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TradeRequestRepository extends JpaRepository<TradeRequest, Long> {

    List<TradeRequest> findAllByOfferingPlayerIdOrReceivingPlayerId(Long offeringPlayer, Long receivingPlayer);

    TradeRequest findByTradeIdAndOfferingPlayerId(Long tradeId, Long offeringPlayerId);

    TradeRequest findByTradeIdAndReceivingPlayerId(Long tradeId, Long receivingPlayerId);

    TradeRequest findByTradeId(Long tradeId);
}
