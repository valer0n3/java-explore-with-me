package ru.practicum.events.service;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.events.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.UpdateEventAdminAndUserRequestDTO;
import ru.practicum.requests.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventShortDto> getAllEventsForCurrentUser(long userId, int from, int size);

    EventFullDto createNewEvent(long userId, NewEventDto newEventDto);

    EventFullDto getEventByIdForCurrentUser(long userId, long eventId);

    EventFullDto updateEvent(long userId, long eventId, UpdateEventAdminAndUserRequestDTO updateEventUserRequestDto);

    List<ParticipationRequestDto> getRequestsForCurrentUser(long userId, long eventId);

    EventRequestStatusUpdateResultDto updateRequestStatusForCurrentUser(long userId, long eventId, EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequestDto);

    List<EventFullDto> getAllEventsAdmin(List<Long> users, List<String> state, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto updateEventAndStatusAdmin(long eventId, UpdateEventAdminAndUserRequestDTO updateEventAdminAndUserRequestDTO);

    List<EventShortDto> getEventsWithFilter(
            String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
            LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size, HttpServletRequest httpServletRequest);

    EventFullDto getEventWithFilterById(Long eventId, HttpServletRequest httpServletRequest);
}
