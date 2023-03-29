package ru.practicum.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationDto {
    private EventShortDto events;
    @NotNull
    @NotBlank
    private Long id;
    @NotNull
    @NotBlank
    private Boolean pinned;
    @NotNull
    @NotBlank
    private String title;
}
