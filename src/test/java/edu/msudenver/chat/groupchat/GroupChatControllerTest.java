//package edu.msudenver.chat.groupchat;
//
//import edu.msudenver.chat.groupchat.GroupChat;
//import edu.msudenver.chat.groupchat.GroupChatController;
//import edu.msudenver.chat.groupchat.GroupChatService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.sql.Time;
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.time.format.DateTimeFormatter;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = GroupChatController.class)
//public class GroupChatControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private GroupChatService groupChatService;
//
//    @BeforeEach
//    public void setup() {
//    }
//
//    @Test
//    public void testGetGroupChat() throws Exception {
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/chat/groups/" + 1L)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        GroupChat testMessage = new GroupChat();
//        testMessage.setMessageId(1L);
//        testMessage.setDateSent(Timestamp.valueOf(LocalDateTime.now()));
//        testMessage.setSenderId(1L);
//        testMessage.setGroupId(1L);
//        testMessage.setMessageBody("Hello World");
//
//        Mockito.when(groupChatService.getAllMessages(1L)).thenReturn(Arrays.asList(testMessage));
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertTrue(response.getContentAsString().contains("Hello World"));
//    }
//
//    @Test
//    public void testGetGroupChatSince() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/chat/groups/since/1?since=DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now())")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        Date date = Timestamp.valueOf(LocalDateTime.parse("2023_01_01_00_00", formatter));
//
//        GroupChat testMessage = new GroupChat();
//        testMessage.setMessageId(1L);
//        testMessage.setDateSent(Timestamp.valueOf(LocalDateTime.now()));
//        testMessage.setSenderId(1L);
//        testMessage.setGroupId(1L);
//        testMessage.setMessageBody("Hello World");
//
//        Mockito.when(groupChatService.getGroupChats(1L, LocalDateTime.now(), LocalDateTime.now())).thenReturn(Arrays.asList(testMessage));
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertTrue(response.getContentAsString().contains("Hello"));
//    }
//
//    @Test
//    public void testGetGroupChatBetween() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/chat/groups/between/")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        GroupChat testMessage = new GroupChat();
//        testMessage.setMessageId(1L);
//        testMessage.setDateSent(Timestamp.valueOf(LocalDateTime.now()));
//        testMessage.setSenderId(1L);
//        testMessage.setGroupId(1L);
//        testMessage.setMessageBody("Hello World");
//
//        Mockito.when(groupChatService.getAllMessages(1l)).thenReturn(Arrays.asList(testMessage));
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertTrue(response.getContentAsString().contains("Hello World"));
//    }
//
//
//    @Test
//    public void testGetGroupChatNotFound() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu_MM_dd_HH_mm");
//
//        // Parse the date string using the formatter
//        LocalDateTime dateTime = LocalDateTime.parse("2023_01_01_00_00", formatter);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/groups/" + 1L)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        Mockito.when(groupChatService.getAllMessages(Mockito.any())).thenReturn(null);
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
//        assertTrue(response.getContentAsString().isEmpty());
//    }
//
//    @Test
//    public void testCreateGroupChat() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
//
//        // Parse the date string using the formatter
//        Date dateTime = Timestamp.valueOf(LocalDateTime.parse("2023_01_01_01_01", formatter));
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/chat/groups/")
//                .accept(MediaType.APPLICATION_JSON)
//                .content("{\"groupId\":\"1\"," + "\"timeStamp\":\"2023_01_01_01_01\"}")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        GroupChat testMessage = new GroupChat();
//        testMessage.setMessageId(1L);
//        testMessage.setDateSent(dateTime);
//        testMessage.setSenderId(1L);
//        testMessage.setGroupId(1L);
//        testMessage.setMessageBody("Hello World");
//
//        Mockito.when(groupChatService.saveGroupChat(Mockito.any())).thenReturn(testMessage);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//        assertTrue(response.getContentAsString().contains("Hello World"));
//    }
//
//    @Test
//    public void testCreateGroupChatBadRequest() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/chat/groups/")
//                .accept(MediaType.APPLICATION_JSON)
//                .content("{\"timeStamp\": \"2023_01_01_00_01\"}")
//                .contentType(MediaType.APPLICATION_JSON);
//        GroupChat testMessage = new GroupChat();
//
//        Mockito.when(groupChatService.saveGroupChat(Mockito.any())).thenThrow(IllegalArgumentException.class);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
//        assertTrue(response.getContentAsString().contains("Exception"));
//    }
//
//    @Test
//    public void testDeleteGroupChat() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
//        LocalDateTime dateTime = LocalDateTime.parse("2023_01_01_00_01", formatter);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .delete("/chat/groups/" + 1L)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        Mockito.when(groupChatService.deleteGroupChat(1L,LocalDateTime.now(),LocalDateTime.now())).thenReturn(true);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
//    }
//
//    @Test
//    public void testDeleteGroupChatNotFound() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
//        LocalDateTime dateTime = LocalDateTime.parse("2023_01_01_00_01", formatter);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .delete("/chat/groups/" + 1L)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        Mockito.when(groupChatService.deleteGroupChat(1L,LocalDateTime.now(),LocalDateTime.now())).thenReturn(false);
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
//        assertTrue(response.getContentAsString().isEmpty());
//    }
//}