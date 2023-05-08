package edu.msudenver.chat.privatechat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PrivateChatController.class)

public class PrivateChatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrivateChatService privateChatService;

    @BeforeEach
    public void setup() {
    }

    // Create a custom formatter
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu_MM_dd_HH_mm");
    // Parse the date string using the formatter
    LocalDateTime dateTime = LocalDateTime.parse("2023_01_01_00_00", formatter);
    @Test
    public void testGetPrivateChats() throws Exception {


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/private/1/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);


        PrivateChat testMessage = new PrivateChat();
        testMessage.setMessageId(1L);
        testMessage.setDateSent(dateTime);
        testMessage.setSenderId(1L);
        testMessage.setMessageBody("Hello World");

        Mockito.when(privateChatService.getPrivateChats(Mockito.anyLong(),Mockito.anyLong(),Mockito.any())).thenReturn(Arrays.asList(testMessage));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Hello World"));
    }

    @Test
    public void testGetPrivateChat() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/private/1/2")
                .queryParam("timestamp", "2023-04-27T10:15:30")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        PrivateChat testMessage = new PrivateChat();
        testMessage.setMessageId(1L);
        testMessage.setDateSent(dateTime);
        testMessage.setSenderId(1L);
        testMessage.setReceiverId(2L);
        testMessage.setMessageBody("Hello World");

        Mockito.when(privateChatService.getPrivateChats(Mockito.anyLong(),Mockito.anyLong(),Mockito.any())).thenReturn(Arrays.asList(testMessage));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Hello World"));
    }


    @Test
    public void testGetPrivateChatNotFound() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/private/1/2/" + dateTime.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(privateChatService.getPrivateChats(Mockito.anyLong(),Mockito.anyLong(),Mockito.any())).thenReturn(null);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testCreatePrivateChat() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/chat/private/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"timeStamp\":\"22022-10-26T04:06:24.344Z\"}")
                .contentType(MediaType.APPLICATION_JSON);

        PrivateChat testMessage = new PrivateChat();
        testMessage.setMessageId(1L);
        testMessage.setDateSent(dateTime);
        testMessage.setSenderId(1L);
        testMessage.setMessageBody("Hello World");

        Mockito.when(privateChatService.savePrivateChat(Mockito.any())).thenReturn(testMessage);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Hello World"));
    }

    @Test
    public void testCreatePrivateChatBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/chat/private/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"timeStamp\":\"2022-10-26T04:06:24.344Z\"}")
                .contentType(MediaType.APPLICATION_JSON);


        Mockito.when(privateChatService.savePrivateChat(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testDeletePrivateChat() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/chat/private/1/2" )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(privateChatService.deletePrivateChat(Mockito.anyLong(),Mockito.anyLong())).thenReturn(true);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    public void testDeletePrivateChatNotFound() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/chat/private/1/2" )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(privateChatService.deletePrivateChat(Mockito.anyLong(),Mockito.anyLong())).thenReturn(false);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }
}