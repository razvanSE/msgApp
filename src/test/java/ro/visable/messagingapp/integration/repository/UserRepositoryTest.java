package ro.visable.messagingapp.integration.repository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ro.visable.messagingapp.model.User;
import ro.visable.messagingapp.repository.UserRepository;
import ro.visable.messagingapp.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest extends DatabaseTest {

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testUserRepository() {
        User newUser = new User();
        newUser.setNickname("chattyMouth");
        userRepository.save(newUser);
        assertNotNull(newUser.getId());

        User user2 = new User();
        user2.setNickname("Second");

        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
        assertTrue(users.contains(newUser));
        assertTrue(users.contains(user2));

        User user = new User();
        user.setNickname("nickname.minaj");
        userRepository.save(user);
        User foundUser = userRepository.findByNickname("nickname.minaj").orElse(null);
        assertEquals(user, foundUser);

        userRepository.save(user);
        User notFoundUser = userRepository.findByNickname("noone").orElse(null);
        assertNull(notFoundUser);
    }
}