package edu.msudenver.chat.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class ReceivingTradeItems {

    @Id
    @Column(name = "trade_items_id", columnDefinition = "SERIAL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long tradeItemsId;

    @Column(name = "trade_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long tradeId;

    @ManyToOne
    @JoinColumn(name = "trade_id", referencedColumnName = "trade_id",insertable = false, updatable = false)
    private TradeRequest tradeRequest;

    @Column(name = "item_id")
    private Long itemId;
    @Column
    private Integer quantity;

    public TradeRequest getTradeRequest() {
        return tradeRequest;
    }

    public void setTradeRequest(TradeRequest tradeRequest) {
        this.tradeRequest = tradeRequest;
    }

    public ReceivingTradeItems() {
    }

    public ReceivingTradeItems(Long tradeId, Long itemId, Integer quantity) {
        this.tradeId = tradeId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getTradeItemsId() {
        return tradeItemsId;
    }

    public void setTradeItemsId(Long tradeItemsId) {
        this.tradeItemsId = tradeItemsId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}