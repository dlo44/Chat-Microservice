package edu.msudenver.chat.groupchat;


//import edu.msudenver.chat.broadcast.Broadcast;
import edu.msudenver.chat.privatechat.PrivateChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
@Service
public class GroupChatService {

    @Autowired
    private GroupChatRepository groupChatRepository;


    @PersistenceContext
    protected EntityManager entityManager;

    public List<GroupChat> getAllMessages(Long groupId) {
        return groupChatRepository.findGroupChatsByGroupId(groupId);
    }

    public List<GroupChat> getGroupChats(Long groupId, LocalDateTime start, LocalDateTime end) {
        try {
            if(start != null && end != null) return  groupChatRepository.findGroupChatsByDateSentBetweenAndGroupId( start, end, groupId);
            if(start != null && end == null)  return groupChatRepository.findGroupChatsByDateSentGreaterThanEqualAndGroupId(start, groupId);
            if(start == null && end == null) return groupChatRepository.findGroupChatsByGroupId(groupId);
            return null;
        } catch (NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public GroupChat saveGroupChat(GroupChat groupChat) {
        GroupChat groupChatSave = groupChatRepository.saveAndFlush(groupChat);
        entityManager.refresh(groupChatSave);
        return groupChat;
    }

    public boolean deleteGroupChat(Long groupId, LocalDateTime start, LocalDateTime end ) {
        try {
            if (groupChatRepository.existsGroupChatByGroupId(groupId)) {

                List<GroupChat> groupChats = null;

                if(start != null && end != null) {  groupChats =  groupChatRepository.findGroupChatsByDateSentBetweenAndGroupId( start, end, groupId); }
                if(start != null && end == null) {  groupChats =  groupChatRepository.findGroupChatsByDateSentGreaterThanEqualAndGroupId(start, groupId); }
                if(start == null && end == null) {  groupChats =  groupChatRepository.findGroupChatsByGroupId(groupId); }

                for (GroupChat groupChat : groupChats) {
                    groupChatRepository.deleteById(groupChat.getMessageId());
                }
                return true;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}