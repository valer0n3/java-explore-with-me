package ru.practicum.service;

import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;

import java.util.List;

public interface StatService {
    PostStatDto addNewStatistic(PostStatDto postStatDto);

    List<GetStatDto> getStatistics(String start, String end, List<String> uris, boolean unique);
}
