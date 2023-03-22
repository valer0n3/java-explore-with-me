package ru.practicum.exceptions;

public class EwmServiceBadRequestException extends RuntimeException {
    public EwmServiceBadRequestException(String message) {
        super(message);
    }
}
