package ro.visable.messagingapp.service;

import org.springframework.stereotype.Service;
import ro.visable.messagingapp.model.Message;
import ro.visable.messagingapp.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    public List<Message> getSentMessages(Long userId) {
        return messageRepository.findBySenderId(userId);
    }

    public List<Message> getReceivedMessages(Long userId) {
        return messageRepository.findByReceiverId(userId);
    }

    public List<Message> getReceivedMessagesFromUser(Long receiverId, Long senderId) {
        return messageRepository.findByReceiverIdAndSenderId(receiverId, senderId);
    }
}
