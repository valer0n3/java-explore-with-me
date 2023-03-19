package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.GetStatDto;
import ru.practicum.model.StatGetModel;

@Mapper(componentModel = "spring")
public interface StatGetMapper {
    GetStatDto mapStatGetModelToGetStatDto(StatGetModel statGetModel);
}
