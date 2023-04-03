package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public EwmServiceException conflict(final EwmServiceConflictException ewmServiceConflictException) {
        log.warn("Error 409: {}", ewmServiceConflictException.getMessage());
        return new EwmServiceException(HttpStatus.CONFLICT.toString(),
                "Integrity constraint has been violated.",
                ewmServiceConflictException.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public EwmServiceException conflictIntegrityViolation(DataIntegrityViolationException dataIntegrityViolationException) {
        log.warn("Error 409: {}", dataIntegrityViolationException.getMessage());
        return new EwmServiceException(HttpStatus.CONFLICT.toString(),
                "Integrity constraint has been violated.",
                dataIntegrityViolationException.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public EwmServiceException conflictValidation(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.warn("Error 409: {}", methodArgumentNotValidException.getMessage());
        if (methodArgumentNotValidException.getMessage().contains("eventDate")) {
            return new EwmServiceException(HttpStatus.CONFLICT.toString(),
                    "Integrity constraint has been violated.",
                    methodArgumentNotValidException.getMessage(),
                    LocalDateTime.now());
        } else {
            throw new EwmServiceBadRequestException("WTF");
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EwmServiceException notFound(final EwmServiceNotFound ewmServiceNotFound) {
        log.warn("Error 404: {}", ewmServiceNotFound.getMessage());
        return new EwmServiceException(HttpStatus.NOT_FOUND.toString(),
                "The required object was not found.",
                ewmServiceNotFound.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public EwmServiceException forbidden(final EwmServiceForbiddenException ewmServiceForbiddenException) {
        log.warn("Error 400: {}", ewmServiceForbiddenException.getMessage());
        return new EwmServiceException(HttpStatus.FORBIDDEN.toString(),
                "For the requested operation the conditions are not met.",
                ewmServiceForbiddenException.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EwmServiceException unsupportedStatus(final EwmServiceUnsupportedStatusEnum ewmServiceUnsupportedStatusEnum) {
        log.warn("Error 400: {}", ewmServiceUnsupportedStatusEnum.getMessage());
        return new EwmServiceException(HttpStatus.BAD_REQUEST.toString(),
                "The enum status is uncorrect",
                ewmServiceUnsupportedStatusEnum.getMessage(),
                LocalDateTime.now());
    }
}
