package ro.visable.messagingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.visable.messagingapp.exception.BadRequestException;
import ro.visable.messagingapp.exception.ResourceNotFoundException;
import ro.visable.messagingapp.model.Message;
import ro.visable.messagingapp.model.User;
import ro.visable.messagingapp.model.dto.MessageDto;
import ro.visable.messagingapp.service.MessageService;
import ro.visable.messagingapp.service.UserService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/sent")
    List<Message> sent(@RequestHeader(value="userId") Long userId) {
        return messageService.getSentMessages(userId);
    }

    @GetMapping("/received")
    List<Message> received(
            @RequestHeader(value="userId") Long userId
    ) {
        return messageService.getReceivedMessages(userId);
    }

    @GetMapping(value = "/received", params="senderId")
    List<Message> receivedFrom(
            @RequestHeader(value="userId") Long userId,
            @RequestParam(name="senderId") Long senderId
    ) {
        try {
            User sender = userService.getUserById(senderId);
            return messageService.getReceivedMessagesFromUser(userId, sender.getId());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Message save(@RequestHeader(value="userId") Long userId, @RequestBody MessageDto messageDto) {
        if (Objects.equals(userId, messageDto.receiverId())) {
            throw new BadRequestException(
                "Sender user and receiver user cannot be the same. Please send different user info"
            );
        }
        try {
            User sender = userService.getUserById(userId);
            User receiver = userService.getUserById(messageDto.receiverId());

            Message message = new Message(sender, receiver, messageDto.content());
            return messageService.sendMessage(message);

        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}