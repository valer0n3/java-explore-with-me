package ru.practicum;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetStatDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;
    private boolean unique;
}
