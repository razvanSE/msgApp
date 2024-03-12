package ro.visable.messagingapp.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}

