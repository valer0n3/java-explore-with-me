package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;
import ru.practicum.mapper.StatMapper;
import ru.practicum.repository.StatRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public PostStatDto addNewStatistic(PostStatDto postStatDto) {
        System.out.println("*************:" + postStatDto);
        return StatMapper.INSTANCE.mapStatModelToPostStatDto(statRepository
                .save(StatMapper.INSTANCE.mapPostStatDtoToStatModel(postStatDto)));
    }

    @Override
    public List<GetStatDto> getStatistics(String start, String end, List<String> uris, boolean unique) {
        return null;
    }
}
