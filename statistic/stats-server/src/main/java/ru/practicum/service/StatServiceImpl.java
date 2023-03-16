package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;

import java.util.List;
@Service
public class StatServiceImpl implements StatService{
    @Override
    public PostStatDto addNewStatistic(PostStatDto postStatDto) {
        return null;
    }

    @Override
    public List<GetStatDto> getStatistics(String start, String end, List<String> uris, boolean unique) {
        return null;
    }
}
