package ru.practicum.service;

import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    PostStatDto addNewStatistic(PostStatDto postStatDto);

    List<GetStatDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
