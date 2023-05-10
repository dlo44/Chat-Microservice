package edu.msudenver.chat.groupchat;

import edu.msudenver.chat.Message;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Entity
@Table(name = "group_chat")
public class GroupChat extends Message {


    @Column(name = "group_id")
    @NotNull(message = "name cannot be null")
    private Long groupId;
    public GroupChat (){
    }

    public GroupChat(Long groupId) {
        super();
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}