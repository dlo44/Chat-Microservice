package edu.msudenver.chat.trade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferingTradeItemsRepository extends JpaRepository<OfferingTradeItems, Long> {
    List<OfferingTradeItems> findAllByTradeId(Long tradeId);
    OfferingTradeItems findByTradeItemsId(Long tradeItemsId);
}
