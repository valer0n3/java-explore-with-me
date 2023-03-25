package ru.practicum.events.mapper;

import org.mapstruct.Mapper;
import ru.practicum.events.dto.LocationDto;
import ru.practicum.events.model.LocationModel;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto mapLocationModelToLocationDto(LocationModel locationModel);

    LocationModel mapLocationDtoToLocationModel(LocationDto locationDto);
}
