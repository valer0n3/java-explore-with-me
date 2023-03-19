package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;
import ru.practicum.mapper.StatGetMapper;
import ru.practicum.mapper.StatPostMapper;
import ru.practicum.model.StatGetModel;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;
    private final StatGetMapper statGetMapper;
    private final StatPostMapper statPostMapper;

    @Override
    public PostStatDto addNewStatistic(PostStatDto postStatDto) {
        return statPostMapper.mapStatModelToPostStatDto(statRepository
                .save(statPostMapper.mapPostStatDtoToStatModel(postStatDto)));
    }

    @Override
    public List<GetStatDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<StatGetModel> listStatGetModel;
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                listStatGetModel = statRepository.getStatWithUniqIpWithoutURI(start, end);
            } else {
                listStatGetModel = statRepository.getStatWithNotUniqIpWithoutURI(start, end);
            }
        } else {
            if (unique) {
                listStatGetModel = statRepository.getStatWithUniqIpWithURI(start, end, uris);
            } else {
                listStatGetModel = statRepository.getStatWithoutUniqIpWithURI(start, end, uris);
            }
        }
        return listStatGetModel.stream()
                .map(statGetMapper::mapStatGetModelToGetStatDto)
                .collect(Collectors.toList());
    }
}
