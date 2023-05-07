package edu.msudenver.chat.groupchat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {

    List<GroupChat> findGroupChatsByDateSentBetweenAndGroupId(LocalDateTime from, LocalDateTime to, Long groupId);

    List<GroupChat> findGroupChatsByDateSentGreaterThanEqualAndGroupId(LocalDateTime date, Long groupId);

    List<GroupChat> findGroupChatsByGroupId(Long groupId);
    boolean existsGroupChatByGroupId(Long groupId);


}