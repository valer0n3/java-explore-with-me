package ru.practicum.requests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.events.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.RequestModel;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto mapRequestModelToParticipationRequestDto(RequestModel requestModel);

    EventRequestStatusUpdateResultDto mapConfirmedOrRejectedToEventRequestStatusUpdateResultDto(
            List<RequestModel> confirmedRequests, List<RequestModel> rejectedRequests);
    // RequestModel mapParticipationRequestDtoToRequestModel(ParticipationRequestDto participationRequestDto);
}
