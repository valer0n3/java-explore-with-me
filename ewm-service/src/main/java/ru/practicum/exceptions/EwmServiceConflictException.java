package ru.practicum.exceptions;

public class EwmServiceConflictException extends RuntimeException {
    public EwmServiceConflictException(String message) {
        super(message);
    }
}
