package ro.visable.messagingapp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ro.visable.messagingapp.model.Message;

public class MessageDto {
    @NotBlank(message = "The receiver is required.")
    @NotNull(message = "The receiver cannot be null.")
    private Long receiverId;

    @NotBlank(message = "The content is required.")
    private String content;

    public MessageDto(Long receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message toMessage()
    {
        Message message = new Message();
        message.setContent(content);

        return message;
    }
}
