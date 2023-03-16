package ru.practicum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetStatDto {
    private String app;
    private String uri;
    private int hits;
}
