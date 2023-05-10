package edu.msudenver.chat.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class TradeRequest {
    @Id
    @Column(name = "trade_id", columnDefinition = "SERIAL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;

    @OneToMany(mappedBy = "tradeRequest", targetEntity = OfferingTradeItems.class)
    private List<OfferingTradeItems> offeredItems;

    @OneToMany(mappedBy = "tradeRequest", targetEntity = ReceivingTradeItems.class)
    private List<ReceivingTradeItems> receivingItems;

    @Column(name = "receiving_player")
    private Long receivingPlayerId;

    @Column(name ="offering_player")
    private Long offeringPlayerId;

    @Column(name = "offering_player_accepted", nullable = false)
    private boolean offeringPlayerAccepted = false;

    @Column(name = "receiving_player_accepted", nullable = false)
    private boolean receivingPlayerAccepted = false;

    @Column(name = "date_created", columnDefinition = "TIMESTAMP")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "date_modified", columnDefinition = "TIMESTAMP")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @UpdateTimestamp
    private Date dateModified;

    @Column(name = "status", nullable = false)
    private String status = "active";

    public void setReceivingItems(List<ReceivingTradeItems> receivingItems) {
        this.receivingItems = receivingItems;
    }


    public TradeRequest() {
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long id) {
        this.tradeId = id;
    }

    public List<OfferingTradeItems> getOfferedItems() {
        return offeredItems;
    }

    public void setOfferedItems(List<OfferingTradeItems> offeredItems) {
        this.offeredItems = offeredItems;
    }

    public List<ReceivingTradeItems> getReceivingItems() {
        return receivingItems;
    }

    public Long getReceivingPlayerId() {
        return receivingPlayerId;
    }

    public void setReceivingPlayerId(Long receivingPlayerId) {
        this.receivingPlayerId = receivingPlayerId;
    }

    public Long getOfferingPlayerId() {
        return offeringPlayerId;
    }

    public void setOfferingPlayerId(Long offeringPlayerId) {
        this.offeringPlayerId = offeringPlayerId;
    }

    public boolean isOfferingPlayerAccepted() {
        return offeringPlayerAccepted;
    }

    public void setOfferingPlayerAccepted(boolean offeringPlayerAccepted) {
        this.offeringPlayerAccepted = offeringPlayerAccepted;
    }

    public boolean isReceivingPlayerAccepted() {
        return receivingPlayerAccepted;
    }

    public void setReceivingPlayerAccepted(boolean receivingPlayerAccepted) {
        this.receivingPlayerAccepted = receivingPlayerAccepted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

