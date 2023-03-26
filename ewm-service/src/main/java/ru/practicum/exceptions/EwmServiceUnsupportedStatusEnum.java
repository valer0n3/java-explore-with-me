package ru.practicum.exceptions;

public class EwmServiceUnsupportedStatusEnum extends RuntimeException {
    public EwmServiceUnsupportedStatusEnum(String message) {
        super(message);
    }
}
