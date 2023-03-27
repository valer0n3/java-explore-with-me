package ru.practicum.requests.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.EventStateEnum;
import ru.practicum.enums.RequestStatusEnum;
import ru.practicum.events.model.EventModel;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.events.service.EventServiceImpl;
import ru.practicum.exceptions.EwmServiceConflictException;
import ru.practicum.exceptions.EwmServiceNotFound;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.mapper.RequestMapper;
import ru.practicum.requests.model.RequestModel;
import ru.practicum.requests.repository.RequestRepository;
import ru.practicum.users.model.UserModel;
import ru.practicum.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;
    private EventRepository eventRepository;
    private EventServiceImpl eventServiceImpl;
    private RequestMapper requestMapper;
    private UserRepository userRepository;

    @Override
    public List<ParticipationRequestDto> getAllRequestsOfcurrentUser(Long userId) {
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(requestMapper::mapRequestModelToParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto saveNewRequest(Long userId, Long eventId) {
        EventModel eventModel = findEventModel(eventId);
        checkIfEventBelongsToRequestor(userId, eventModel.getInitiator().getId());
        checkIfEventIsNotPublished(eventModel.getState());
        checkIfParticipantsLimitReached(eventModel.getId(), eventModel.getParticipantLimit());
        RequestModel requestModel = new RequestModel();
        requestModel.setRequester(findUserModel(userId));
        requestModel.setEvent(eventModel);
        requestModel.setStatus(checkIfRequestModerationIsNessesary(eventModel.getRequestModeration()));
        requestModel.setCreated(LocalDateTime.now());
        return requestMapper.mapRequestModelToParticipationRequestDto(requestRepository.save(requestModel));
    }

    private String checkIfRequestModerationIsNessesary(Boolean requestModeration) {
        if (requestModeration) {
            return RequestStatusEnum.PENDING.toString();
        } else {
            return RequestStatusEnum.CONFIRMED.toString();
        }
    }

    private EventModel findEventModel(Long event) {
        return eventRepository.findById(event)
                .orElseThrow(() -> new EwmServiceNotFound(String
                        .format("Event with id=%d was not found", event)));
    }

    private UserModel findUserModel(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EwmServiceNotFound(String
                .format("User id: %s is not existed in database", userId)));
    }

    private void checkIfEventBelongsToRequestor(Long userId, Long eventOwnerId) {
        if (eventOwnerId == userId) {
            throw new EwmServiceConflictException("Initiator of a event can't create a request for participation");
        }
    }

    private void checkIfEventIsNotPublished(String state) {
        if (!state.toUpperCase().equals(EventStateEnum.PUBLISHED.toString())) {
            throw new EwmServiceConflictException(String
                    .format("You can not participate in unpublished event. Event status: %s", state));
        }
    }

    private void checkIfParticipantsLimitReached(long eventId, int participantLimit) {
        if (participantLimit > 0) {
            int amountOfConfirmedParticipants = getAmountOfConfirmedParticipants(eventId,
                    RequestStatusEnum.CONFIRMED.toString());
            if (amountOfConfirmedParticipants >= participantLimit) {
                throw new EwmServiceConflictException(String
                        .format("Amount of current participants: %d reached limit: %d",
                                amountOfConfirmedParticipants, participantLimit));
            }
        }
    }

    private int getAmountOfConfirmedParticipants(long eventId, String toString) {
        return requestRepository.getAmountOfParticipants(eventId,
                RequestStatusEnum.CONFIRMED.toString());
    }

    @Override
    public ParticipationRequestDto updateRequestStatusToCancel(Long userId, Long requestId) {
        RequestModel requestModel = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new EwmServiceNotFound(String
                        .format("Request id: %s for user id: %s was not found", requestId, userId)));
        requestModel.setStatus(RequestStatusEnum.CANCELED.toString());
        return requestMapper.mapRequestModelToParticipationRequestDto(requestRepository.save(requestModel));
    }
}
