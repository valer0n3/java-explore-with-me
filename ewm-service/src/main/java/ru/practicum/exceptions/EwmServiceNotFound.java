package ru.practicum.exceptions;

public class EwmServiceNotFound extends RuntimeException {
    public EwmServiceNotFound(String message) {
        super(message);
    }
}
