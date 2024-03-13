package ro.visable.messagingapp.unit.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.visable.messagingapp.controller.UserController;
import ro.visable.messagingapp.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    //test user is created
    @Test
    public void saveShouldCreateUser() throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("nickname", "first");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldFailWhenNicknameIsEmpty() throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("nickname", "");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andExpect(status().isBadRequest());
    }

}