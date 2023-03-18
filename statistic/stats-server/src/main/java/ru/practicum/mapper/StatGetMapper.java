package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.GetStatDto;
import ru.practicum.model.StatGetModel;

@Mapper(componentModel = "spring")
public interface StatGetMapper {
    StatGetMapper INSTANCE = Mappers.getMapper(StatGetMapper.class);

    GetStatDto mapStatGetModelToGetStatDto(StatGetModel statGetModel);
}
