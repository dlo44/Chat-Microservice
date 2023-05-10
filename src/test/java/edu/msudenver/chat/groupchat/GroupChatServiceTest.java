package edu.msudenver.chat.groupchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = GroupChatService.class)
public class GroupChatServiceTest {

    @MockBean
    private GroupChatRepository groupChatRepository;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    private GroupChatService groupChatService;

    @BeforeEach
    private void setup() {
        groupChatService.entityManager = entityManager;
    }

    @Test
    public void testGetGroupChats() throws Exception {
        GroupChat testMessage = new GroupChat();
        testMessage.setMessageId(1L);
        testMessage.setSenderId(1L);
        testMessage.setDateSent(LocalDateTime.now());
        testMessage.setGroupId(1L);
        testMessage.setMessageBody("Hello World");

        Mockito.when(groupChatRepository.findGroupChatsByGroupId(Mockito.any())).thenReturn(Arrays.asList(testMessage));
        List<GroupChat> groupChats = groupChatService.getAllMessages(1L);
        assertEquals(1, groupChats.size());
        assertEquals("Hello World", groupChats
                .get(0)
                .getMessageBody());
    }


    @Test
    public void testGetGroupChatNotFound() throws Exception {
        Mockito.when(groupChatRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertTrue(groupChatService.getAllMessages(1L).isEmpty());
    }

    @Test
    public void testSaveGroupChat() throws Exception {
        GroupChat testMessage = new GroupChat();
        testMessage.setMessageId(1L);
        testMessage.setSenderId(1L);
        testMessage.setDateSent(LocalDateTime.now());
        testMessage.setSenderId(1L);
        testMessage.setGroupId(1L);
        testMessage.setMessageBody("Hello World");

        Mockito.when(groupChatRepository.saveAndFlush(Mockito.any())).thenReturn(testMessage);
        Mockito.when(groupChatRepository.save(Mockito.any())).thenReturn(testMessage);

        assertEquals(testMessage, groupChatService.saveGroupChat(testMessage));
    }

    @Test
    public void testSaveGroupChatBadRequest() throws Exception {
        GroupChat testMessage = new GroupChat();
        testMessage.setMessageId(1L);
        testMessage.setSenderId(1L);
        testMessage.setDateSent(LocalDateTime.now());
        testMessage.setSenderId(1L);
        testMessage.setGroupId(1L);
        testMessage.setMessageBody("Hello World");

        Mockito.when(groupChatRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        Mockito.when(groupChatRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            groupChatService.saveGroupChat(testMessage);
        });
    }

    @Test
    public void testDeleteGroupChat() throws Exception {
        GroupChat testMessage = new GroupChat();
        testMessage.setMessageId(1L);
        testMessage.setSenderId(1L);
        testMessage.setDateSent(LocalDateTime.now());
        testMessage.setSenderId(1L);
        testMessage.setGroupId(1L);
        testMessage.setMessageBody("Hello World");
        Mockito.when(groupChatRepository.findById(Mockito.any())).thenReturn(Optional.of(testMessage));
        Mockito.when(groupChatRepository.existsById(1L)).thenReturn(true);

        assertFalse(groupChatService.deleteGroupChat(1L,LocalDateTime.now(),LocalDateTime.now()));
    }

    @Test
    public void testDeleteGroupChatNotFound() throws Exception {
        Mockito.when(groupChatRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(groupChatRepository.existsById(Mockito.any())).thenReturn(false);
        Mockito.doThrow(IllegalArgumentException.class)
                .when(groupChatRepository)
                .deleteById(Mockito.any());

        assertFalse(groupChatService.deleteGroupChat(1L,LocalDateTime.now(),LocalDateTime.now()));
    }
}