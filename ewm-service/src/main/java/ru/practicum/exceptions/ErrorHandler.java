package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EwmServiceException badRequest(final EwmServiceBadRequestException ewmServiceBadRequestException) {
        log.warn("Error 400: {}", ewmServiceBadRequestException.getMessage());
        return new EwmServiceException(HttpStatus.BAD_REQUEST.toString(),
                "Incorrectly made request.",
                ewmServiceBadRequestException.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public EwmServiceException conflict(final EwmServiceConflict ewmServiceConflict) {
        log.warn("Error 409: {}", ewmServiceConflict.getMessage());
        return new EwmServiceException(HttpStatus.CONFLICT.toString(),
                "Integrity constraint has been violated.",
                ewmServiceConflict.getMessage(),
                LocalDateTime.now());
    }
}
