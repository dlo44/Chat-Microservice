package edu.msudenver.chat.privatechat;

import edu.msudenver.TestConfig;
import edu.msudenver.chat.privatechat.PrivateChat;
import edu.msudenver.chat.privatechat.PrivateChatRepository;
import edu.msudenver.chat.privatechat.PrivateChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PrivateChatService.class)
public class PrivateChatServiceTest {
    @MockBean
    private PrivateChatRepository privateChatRepository;
    @MockBean
    private EntityManagerFactory entityManagerFactory;
    @MockBean
    private EntityManager entityManager;
    @Autowired
    private PrivateChatService privateChatService;

    @BeforeEach
    private void setUp() {
        privateChatService.entityManager = entityManager;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse("2023-01-01 00:00:00", formatter);

    @Test
    public void testGetPrivateChats() throws Exception {
        PrivateChat testMessage = new PrivateChat();
        testMessage.setMessageId(1L);
        testMessage.setSenderId(1L);
        testMessage.setReceiverId(2L);
        testMessage.setDateSent(LocalDateTime.now());

        Mockito.when(privateChatRepository.findAllBySenderIdAndReceiverId(1L, 2L)).thenReturn(Collections.singletonList(testMessage));

        Mockito.when(privateChatRepository.findAllBySenderIdAndReceiverId(Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(testMessage));
        List<PrivateChat> privateChats = privateChatService.getPrivateChats(testMessage.getSenderId(), testMessage.getReceiverId());
        assertEquals(1, privateChats.size());
        assertEquals(1L, privateChats.get(0).getMessageId().longValue());
    }

    @Test
    public void testGetPrivateChat() throws Exception {
        PrivateChat testMessage = new PrivateChat();
        testMessage.setMessageId(1L);
        testMessage.setSenderId(1L);
        testMessage.setDateSent(LocalDateTime.now());

        testMessage.setReceiverId(1L);
        testMessage.setMessageBody("Hello");

        Mockito.when(privateChatRepository.findBySenderIdAndReceiverIdAndDateSentGreaterThanEqual(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(testMessage));
        List<PrivateChat> privateChats = privateChatService.getPrivateChats(testMessage.getSenderId(), testMessage.getReceiverId(), LocalDateTime.now());
        assertEquals(1, privateChats.size());
        assertEquals("Hello", privateChats.get(0).getMessageBody());
    }

    @Test
    public void testGetPrivateChatNotFound() {
        Mockito.when(privateChatRepository.findBySenderIdAndReceiverIdAndDateSentGreaterThanEqual(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Collections.emptyList());
        List<PrivateChat> privateChats = privateChatService.getPrivateChats(1L, 2L, LocalDateTime.now());
        assertTrue(privateChats.isEmpty());
    }

    @Test
    public void testSavePrivateChat() {
        PrivateChat privateChat = new PrivateChat();
        privateChat.setMessageId(1L);
        privateChat.setSenderId(1L);
        privateChat.setReceiverId(2L);
        privateChat.setMessageBody("Hello");
        privateChat.setDateSent(localDateTime);

        Mockito.when(privateChatRepository.saveAndFlush(Mockito.any())).thenReturn(privateChat);
        PrivateChat savedChat = privateChatService.savePrivateChat(privateChat);
        assertNotNull(savedChat);
        assertEquals(1L, savedChat.getMessageId());
        assertEquals(1L, savedChat.getSenderId());
        assertEquals(2L, savedChat.getReceiverId());
        assertEquals("Hello", savedChat.getMessageBody());
        assertNotNull(savedChat.getDateSent());
    }

    @Test
    public void testSavePrivateChatBadRequest() {
        PrivateChat privateChat = new PrivateChat();
        privateChat.setSenderId(1L);
        privateChat.setReceiverId(null);

        Mockito.when(privateChatRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        Mockito.when(privateChatRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            privateChatService.savePrivateChat(privateChat);
        });
    }
    @Test
    public void testDeletePrivateChat() throws Exception {
        long senderId = 1L;
        long receiverId = 2L;


        Mockito.when(privateChatRepository.existsBySenderIdAndReceiverId(senderId, receiverId)).thenReturn(true);
        boolean result = privateChatService.deletePrivateChat(senderId, receiverId);
        assertTrue(result);
//        Mockito.verify(privateChatRepository, times(1)).deleteById(messageId);
    }

    @Test
    public void testDeletePrivateChatNotFound() {
        long senderId = 1L;
        long receiverId = 2L;

        Mockito.when(privateChatRepository.existsBySenderIdAndReceiverId(senderId, receiverId)).thenReturn(false);
        boolean deleted = privateChatService.deletePrivateChat(senderId, receiverId);
        assertFalse(deleted);
    }

}