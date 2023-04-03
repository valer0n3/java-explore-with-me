package ru.practicum.events.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.EventModel;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "category", ignore = true)
    EventModel mapNewEventDtoToEventModel(NewEventDto newEventDto);

    EventFullDto mapEventModelToEventFullDto(EventModel eventModel);

    EventFullDto mapEventModelToEventFullDto(EventModel eventModel, long confirmedRequests, long views);

    EventShortDto mapEventModelToEventShortDto(EventModel eventModel);
}
