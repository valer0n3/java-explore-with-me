package ru.practicum.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.practicum.constants.ProjectConstants.DATE_TIME_PATTERN;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EwmServiceException {
    private String status;
    private String reason;
    private String message;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestamp;
}
