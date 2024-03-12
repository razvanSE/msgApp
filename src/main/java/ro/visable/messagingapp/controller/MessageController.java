package ro.visable.messagingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.visable.messagingapp.model.dto.MessageDto;
import ro.visable.messagingapp.exception.BadRequestException;
import ro.visable.messagingapp.exception.ResourceNotFoundException;
import ro.visable.messagingapp.model.Message;
import ro.visable.messagingapp.model.User;
import ro.visable.messagingapp.service.MessageService;
import ro.visable.messagingapp.service.UserService;

import java.util.List;
import java.util.Objects;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/messages")
    List<Message> all() {
        return messageService.getAll();
    }

    @GetMapping("/messages/sent")
    List<Message> sent(@RequestHeader(value="userId", required=false) Long userId) {
        if (userId == null) {
            throw new BadRequestException("UserId Header is missing");
        }
        return messageService.getSentMessages(userId);
    }

    @GetMapping("messages/received")
    List<Message> receivedFrom(
            @RequestHeader(value="userId", required=false) Long userId,
            @RequestParam(name = "senderId", required = false) Long senderId
    ) {
        if (userId == null) {
            throw new BadRequestException("UserId Header is missing");
        }

        if (senderId == null) {
            return messageService.getReceivedMessages(userId);
        }

        try {
            User sender = userService.getUserById(senderId);
            return messageService.getReceivedMessagesFromUser(userId, sender.getId());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    Message save(@RequestHeader(value="userId", required=false) Long userId, @RequestBody MessageDto messageDto) {
        if (userId == null) {
            throw new BadRequestException("UserId Header is missing");
        }

        if (Objects.equals(userId, messageDto.getReceiverId())) {
            throw new BadRequestException(
                "Sender user and receiver user cannot be the same. Please send different user info"
            );
        }
        try {
            User sender = userService.getUserById(userId);
            User receiver = userService.getUserById(messageDto.getReceiverId());

            Message message = new Message(sender, receiver, messageDto.getContent());
            return messageService.sendMessage(message);

        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}