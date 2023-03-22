package ru.practicum.exceptions;

public class EwmServiceConflict extends RuntimeException{
    public EwmServiceConflict(String message) {
        super(message);
    }
}
