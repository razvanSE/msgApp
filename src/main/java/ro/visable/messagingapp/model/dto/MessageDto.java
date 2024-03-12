package ro.visable.messagingapp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageDto(
    @NotBlank(message = "The receiver is required.")
    @NotNull(message = "The receiver cannot be null.")
    Long receiverId,
    @NotBlank(message = "The content is required.")
    String content
){}