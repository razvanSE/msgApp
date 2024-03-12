package ro.visable.messagingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import ro.visable.messagingapp.model.dto.UserDto;
import ro.visable.messagingapp.exception.BadRequestException;
import ro.visable.messagingapp.model.User;
import ro.visable.messagingapp.service.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userss")
    List<User> all() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    User save(@Valid @RequestBody UserDto userDto) {
        try {
            return userService.createUser(userDto.toUser());
        } catch (Exception ex) {
            throw new BadRequestException("Cannot save user. Nickname might be used already.");
        }
    }
}