package ro.visable.messagingapp.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.visable.messagingapp.model.Message;
import ro.visable.messagingapp.model.User;
import ro.visable.messagingapp.repository.MessageRepository;
import ro.visable.messagingapp.service.MessageService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() {
        User sender = new User();
        sender.setId(99L);
        sender.setNickname("here2chat");

        User receiver = new User();
        receiver.setId(91L);
        receiver.setNickname("2fast4u");

        Message message = new Message();
        message.setId(1L);
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setContent("ASL PLS");

        when(messageRepository.save(any(Message.class))).thenReturn(message);
        Message savedMessage = messageService.sendMessage(new Message());
        assertNotNull(savedMessage);
        assertEquals(1L, savedMessage.getId());
        assertEquals("ASL PLS", savedMessage.getContent());
        assertEquals(sender, savedMessage.getSender());
    }

    @Test
    void testGetSentMessages() {
        User user1 = new User();
        user1.setId(99L);
        user1.setNickname("here2chat");

        User user2 = new User();
        user2.setId(91L);
        user2.setNickname("2fast4u");


        Message message1 = new Message();
        message1.setId(1L);
        message1.setReceiver(user2);
        message1.setSender(user1);
        message1.setContent("ASL PLS");

        Message message2 = new Message();
        message2.setId(2L);
        message1.setReceiver(user2);
        message1.setSender(user1);
        message2.setContent("HEY?");

        List<Message> messageList = List.of(message1, message2);
        when(messageRepository.findBySenderId(99L)).thenReturn(messageList);
        List<Message> fetchedMessages = messageService.getSentMessages(99L);
        assertNotNull(fetchedMessages);
        assertEquals(2, fetchedMessages.size());
     }

    @Test
    void testGetReceivedMessages() {
        User user1 = new User();
        user1.setId(99L);
        user1.setNickname("here2chat");

        User user2 = new User();
        user2.setId(91L);
        user2.setNickname("2fast4u");


        Message message1 = new Message();
        message1.setId(1L);
        message1.setReceiver(user2);
        message1.setSender(user1);
        message1.setContent("ASL PLS");

        Message message2 = new Message();
        message2.setId(2L);
        message1.setReceiver(user2);
        message1.setSender(user1);
        message2.setContent("HEY?");

        List<Message> messageList = List.of(message1, message2);
        when(messageRepository.findByReceiverId(91L)).thenReturn(messageList);
        List<Message> fetchedMessages = messageService.getReceivedMessages(91L);
        assertNotNull(fetchedMessages);
        assertEquals(2, fetchedMessages.size());

    }

    @Test
    void testGetReceivedMessagesFromUser() {
        User user1 = new User();
        user1.setId(99L);
        user1.setNickname("here2chat");

        User user2 = new User();
        user2.setId(91L);
        user2.setNickname("2fast4u");


        Message message1 = new Message();
        message1.setId(1L);
        message1.setReceiver(user2);
        message1.setSender(user1);
        message1.setContent("ASL PLS");

        Message message2 = new Message();
        message2.setId(2L);
        message1.setReceiver(user2);
        message1.setSender(user1);
        message2.setContent("HEY?");

        List<Message> messageList = List.of(message1, message2);
        when(messageRepository.findByReceiverIdAndSenderId(91L, 99L)).thenReturn(messageList);
        List<Message> fetchedMessages = messageService.getReceivedMessagesFromUser(91L, 99L);
        assertNotNull(fetchedMessages);
        assertEquals(2, fetchedMessages.size());

    }
}
