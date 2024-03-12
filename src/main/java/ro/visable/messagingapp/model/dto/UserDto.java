package ro.visable.messagingapp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import ro.visable.messagingapp.model.User;

public class UserDto {

    @NotBlank(message = "The nickname is required.")
    @NotEmpty(message = "The nickname cannot be empty.")
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public User toUser() {
        User user = new User();
        user.setNickname(nickname);
        return user;
    }
}