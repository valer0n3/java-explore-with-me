package ru.practicum.exceptions;

public class EwmServiceForbiddenException extends RuntimeException {
    public EwmServiceForbiddenException(String message) {
        super(message);
    }
}
