package edu.msudenver.chat.privatechat;

import edu.msudenver.chat.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "private_chat")
public class PrivateChat extends Message {

    @Column (name = "receiver_id")
    @NotNull(message = "receiver_id cannot be null")
    private Long receiverId;

    public PrivateChat() {
    }

    public PrivateChat(Long receiverId) {
        super();
        this.receiverId = receiverId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}