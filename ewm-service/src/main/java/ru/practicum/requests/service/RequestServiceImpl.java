package ru.practicum.requests.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.repository.RequestRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;

    @Override
    public List<ParticipationRequestDto> getAllRequestsOfcurrentUser(Long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto saveNewRequest(Long userId, ParticipationRequestDto participationRequestDto) {
        return null;
    }

    @Override
    public ParticipationRequestDto updateRequestStatusToCancel(Long userId, Long requestId, ParticipationRequestDto participationRequestDto) {
        return null;
    }
}
