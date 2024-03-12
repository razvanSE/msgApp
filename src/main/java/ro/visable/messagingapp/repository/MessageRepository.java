package ro.visable.messagingapp.repository;

import ro.visable.messagingapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderId(Long userId);

    List<Message> findByReceiverId(Long userId);

    List<Message> findByReceiverIdAndSenderId(Long senderId, Long receiverId);
}