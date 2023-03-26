package ru.practicum.requests.service;

import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getAllRequestsOfcurrentUser(Long userId);

    ParticipationRequestDto saveNewRequest(Long userId, ParticipationRequestDto participationRequestDto);

    ParticipationRequestDto updateRequestStatusToCancel(Long userId,
                                                        Long requestId,
                                                        ParticipationRequestDto participationRequestDto);
}
