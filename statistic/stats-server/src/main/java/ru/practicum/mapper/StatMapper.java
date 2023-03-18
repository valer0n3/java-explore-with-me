package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;
import ru.practicum.model.StatPostModel;

@Mapper(componentModel = "spring")
public interface StatMapper {
    StatMapper INSTANCE = Mappers.getMapper(StatMapper.class);

    StatPostModel mapGetStatDtoToStatModel(GetStatDto getStatDto);

    GetStatDto getStatModelToGetStatDto(StatPostModel statPostModel);

    StatPostModel mapPostStatDtoToStatModel(PostStatDto postStatDto);

    PostStatDto mapStatModelToPostStatDto(StatPostModel statPostModel);
}
