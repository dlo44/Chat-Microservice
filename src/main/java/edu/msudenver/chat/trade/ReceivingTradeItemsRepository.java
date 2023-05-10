package edu.msudenver.chat.trade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ReceivingTradeItemsRepository extends JpaRepository<ReceivingTradeItems, Long> {
    List<ReceivingTradeItems> findAllByTradeId(Long tradeId);
    ReceivingTradeItems findByTradeItemsId(Long tradeItemsId);

}
