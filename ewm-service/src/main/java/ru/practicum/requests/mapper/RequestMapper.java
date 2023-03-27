package ru.practicum.requests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.RequestModel;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto mapRequestModelToParticipationRequestDto(RequestModel requestModel);
    // RequestModel mapParticipationRequestDtoToRequestModel(ParticipationRequestDto participationRequestDto);
}
