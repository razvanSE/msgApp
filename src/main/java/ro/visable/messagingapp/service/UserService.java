package ro.visable.messagingapp.service;

import org.springframework.stereotype.Service;
import ro.visable.messagingapp.model.User;
import ro.visable.messagingapp.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(long userId) throws IllegalArgumentException {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User getUserByNickname(String nickname) throws IllegalArgumentException {
        return userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("User "+ nickname +" not found"));
    }

}