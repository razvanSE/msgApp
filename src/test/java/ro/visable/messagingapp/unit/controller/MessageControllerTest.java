package ro.visable.messagingapp.unit.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.visable.messagingapp.controller.MessageController;
import ro.visable.messagingapp.service.MessageService;
import ro.visable.messagingapp.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserService userService;

    @Test
    public void sendMessageToAnotherUserShouldWork() throws Exception {
        JSONObject requestBody = new JSONObject();

        requestBody.put("receiverId", 2);
        requestBody.put("content", "Great message");

        mockMvc.perform(post("/messages")
                        .header("userId", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void sendMessageToSameUserShouldFail() throws Exception {
        JSONObject requestBody = new JSONObject();

        requestBody.put("receiverId", 2);
        requestBody.put("content", "Message to myself");

        mockMvc.perform(post("/messages")
                        .header("userId", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andExpect(status().isBadRequest());
    }

}