package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;
import ru.practicum.model.StatPostModel;

@Mapper(componentModel = "spring")
public interface StatPostMapper {
    StatPostModel mapGetStatDtoToStatModel(GetStatDto getStatDto);

    GetStatDto getStatModelToGetStatDto(StatPostModel statPostModel);

    StatPostModel mapPostStatDtoToStatModel(PostStatDto postStatDto);

    PostStatDto mapStatModelToPostStatDto(StatPostModel statPostModel);
}
