package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @Min(20)
    @Max(2000)
    @NotNull
    @NotBlank
    private String annotation;
    @NotNull
    private long category;
    @Min(20)
    @Max(7000)
    @NotNull
    @NotBlank
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime eventDate;
    @NotNull
    private LocationDto location;
    private boolean paid = false;
    private int participantLimit;
    @NotNull
    @Min(3)
    @Max(120)
    private String title;
}
