package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;
import ru.practicum.model.StatModel;

@Mapper(componentModel = "spring")
public interface StatMapper {
    StatMapper INSTANCE = Mappers.getMapper(StatMapper.class);

    StatModel mapGetStatDtoToStatModel(GetStatDto getStatDto);

    GetStatDto getStatModelToGetStatDto(StatModel statModel);

    StatModel mapPostStatDtoToStatModel(PostStatDto postStatDto);

    PostStatDto mapStatModelToPostStatDto(StatModel statModel);
}
