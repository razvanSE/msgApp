package ro.visable.messagingapp.integration.repository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ro.visable.messagingapp.model.Message;
import ro.visable.messagingapp.model.User;
import ro.visable.messagingapp.repository.MessageRepository;
import ro.visable.messagingapp.repository.UserRepository;
import ro.visable.messagingapp.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class MessageRepositoryTest extends DatabaseTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    void testMessageRepository() {
        User firstUser = new User();
        firstUser.setNickname("chat4life");

        User secondUser = new User();
        secondUser.setNickname("chattyKathy");

        User thirdUser = new User();
        thirdUser.setNickname("lonerUser");

        userRepository.saveAll(List.of(firstUser, secondUser, thirdUser));

        Message newMessage = new Message();
        newMessage.setContent("From chat to Kathy");
        newMessage.setSender(firstUser);
        newMessage.setReceiver(secondUser);
        newMessage.setTimestamp(LocalDateTime.now());

        messageRepository.save(newMessage);
        assertNotNull(newMessage.getId());

        Message newerMessage = new Message();
        newerMessage.setContent("Halloechen");
        newerMessage.setSender(firstUser);
        newerMessage.setReceiver(secondUser);
        newerMessage.setTimestamp(LocalDateTime.now());
        messageRepository.save(newerMessage);

        List<Message> receivedBySecond = messageRepository.findByReceiverId(secondUser.getId());
        List<Message> receivedByThird = messageRepository.findByReceiverId(thirdUser.getId());
        assertEquals(2, receivedBySecond.size());
        assertEquals(0, receivedByThird.size());

        List<Message> sentByFirst = messageRepository.findBySenderId(firstUser.getId());
        List<Message> sentByThird = messageRepository.findBySenderId(thirdUser.getId());
        assertEquals(2, sentByFirst.size());
        assertEquals(0, sentByThird.size());

        List<Message> sentByFirstToSecond = messageRepository.findByReceiverIdAndSenderId(secondUser.getId(), firstUser.getId());
        List<Message> sentByFirstToSThird = messageRepository.findByReceiverIdAndSenderId(secondUser.getId(), thirdUser.getId());
        assertEquals(2, sentByFirstToSecond.size());
        assertEquals(0, sentByFirstToSThird.size());

    }
}