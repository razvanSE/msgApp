package ro.visable.messagingapp.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.visable.messagingapp.model.User;
import ro.visable.messagingapp.repository.UserRepository;
import ro.visable.messagingapp.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setId(99L);
        user.setNickname("Nickdan");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userService.createUser(new User());
        assertNotNull(savedUser);
        assertEquals(99L, savedUser.getId());
        assertEquals("Nickdan", savedUser.getNickname());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User fetchedUser = userService.getUserById(1L);
        assertNotNull(fetchedUser);
        assertEquals(1L, fetchedUser.getId());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testGetUserByNickname() {
        User user = new User();
        user.setNickname("chatterBox");
        when(userRepository.findByNickname("chatterBox")).thenReturn(Optional.of(user));
        User fetchedUser = userService.getUserByNickname("chatterBox");
        assertNotNull(fetchedUser);
        assertEquals("chatterBox", fetchedUser.getNickname());
    }

    @Test
    void testGetUserByNickname_UserNotFound() {
        when(userRepository.findByNickname(anyString())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.getUserByNickname("ChattyKathy"));
    }
}
