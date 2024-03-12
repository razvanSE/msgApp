package ro.visable.messagingapp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import ro.visable.messagingapp.model.User;

public record UserDto(
        @NotBlank(message = "The nickname is required.")
        @NotEmpty(message = "The nickname cannot be empty.")
        String nickname
){
    public User toUser() {
        User user = new User();
        user.setNickname(nickname);
        return user;
    }
}