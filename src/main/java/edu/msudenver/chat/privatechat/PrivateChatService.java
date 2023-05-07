package edu.msudenver.chat.privatechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PrivateChatService {
    @Autowired
    public PrivateChatRepository privateChatRepository;

    @PersistenceContext
    protected EntityManager entityManager;
    public List<PrivateChat> getPrivateChats(long senderId, long receiverId)
    {
        return privateChatRepository.findAllBySenderIdAndReceiverId(senderId, receiverId);

    }
    public List<PrivateChat> getPrivateChats(long senderId, long receiverId, LocalDateTime timestamp )
    {
        try {
            if(timestamp == null) return privateChatRepository.findAllBySenderIdAndReceiverId(senderId, receiverId);
            return privateChatRepository.findBySenderIdAndReceiverIdAndDateSentGreaterThanEqual(senderId, receiverId, timestamp);
        } catch(NoSuchElementException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public PrivateChat savePrivateChat( PrivateChat privateChat)
    {
        privateChat = privateChatRepository.saveAndFlush(privateChat);
        entityManager.refresh(privateChat);
        return privateChat;
    }

    public boolean deletePrivateChat(Long senderId, Long receiverId)
    {
        try {
            if(privateChatRepository.existsBySenderIdAndReceiverId(senderId, receiverId)){
                List<PrivateChat> privateChats =  privateChatRepository.findAllBySenderIdAndReceiverId(senderId, receiverId);
                for (PrivateChat privateChat : privateChats) {
                    privateChatRepository.deleteById(privateChat.getMessageId());
                }

                return true;
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

}