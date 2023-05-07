package edu.msudenver.chat.privatechat;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {

    List<PrivateChat> findAllBySenderIdAndReceiverId(Long senderId, Long receiverId);

    Boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<PrivateChat> deleteAllBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<PrivateChat> findBySenderIdAndReceiverIdAndDateSentGreaterThanEqual(Long senderId, Long receiverId, LocalDateTime dateSent);
}
