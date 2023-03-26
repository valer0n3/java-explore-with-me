package ru.practicum.requests.mapper;

import org.mapstruct.Mapper;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.RequestModel;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    ParticipationRequestDto mapRequestModelToParticipationRequestDto(RequestModel requestModel);

    RequestModel mapParticipationRequestDtoToRequestModel(ParticipationRequestDto participationRequestDto);
}
