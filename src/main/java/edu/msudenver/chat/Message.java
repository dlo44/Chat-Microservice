package edu.msudenver.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.array.LongArrayType;
import org.hibernate.annotations.*;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@TypeDefs({
        @TypeDef(name = "Long-array", typeClass = LongArrayType.class)
})
public class Message {

    @Id
    @Column(name = "message_id", columnDefinition = "SERIAL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long messageId;

    @Column(name = "date_Sent", columnDefinition = "TIMESTAMP")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    private LocalDateTime dateSent;


    @Column(name = "sender_id")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull(message = "senderId cannot be null")
    private Long senderId;

    @Column(name = "message_body")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String messageBody;

    @Column(name = "message_type")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String messageType;

    public Message() {
    }

    public Message(Long messageId, LocalDateTime dateSent, Long senderId,  String messageBody, String messageType) {
        this.messageId = messageId;
        this.dateSent = dateSent;
        this.senderId = senderId;
        this.messageBody = messageBody;
        this.messageType = messageType;
    }
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }


    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}