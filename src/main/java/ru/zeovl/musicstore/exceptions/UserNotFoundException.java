package ru.zeovl.musicstore.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends RuntimeException {

    private final long requestedUserId;

    public UserNotFoundException(long requestedUserId) {
        super("User not found with id: " + requestedUserId);
        this.requestedUserId = requestedUserId;
    }

}
