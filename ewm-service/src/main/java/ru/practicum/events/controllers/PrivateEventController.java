package ru.practicum.events.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.events.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.ParticipationRequestDto;
import ru.practicum.events.dto.UpdateEventAdminAndUserRequestDTO;
import ru.practicum.events.service.EventServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@AllArgsConstructor
@Validated
public class PrivateEventController {
    private final EventServiceImpl eventService;

    @GetMapping
    public List<EventShortDto> getAllEventsForCurrentUser(
            @PathVariable("userId") @Min(0) long userId,
            @RequestParam(name = "from", defaultValue = "0") @Min(0) int from,
            @RequestParam(name = "size", defaultValue = "10") @Min(1) int size) {
        return eventService.getAllEventsForCurrentUser(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createNewEvent(@PathVariable("userId") @Min(0) long userId,
                                       @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.createNewEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdForCurrentUser(@PathVariable("userId") @Min(0) long userId,
                                                   @PathVariable("eventId") @Min(0) long eventId) {
        return eventService.getEventByIdForCurrentUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable("userId") @Min(0) long userId,
            @PathVariable("eventId") @Min(0) long eventId,
            @Valid @RequestBody UpdateEventAdminAndUserRequestDTO updateEventUserRequestDto) {
        return eventService.updateEvent(userId, eventId, updateEventUserRequestDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsForCurrentUser(@PathVariable("userId") @Min(0) long userId,
                                                                   @PathVariable("eventId") @Min(0) long eventId) {
        return eventService.getRequestsForCurrentUser(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResultDto updateRequestStatusForCurrentUser(
            @PathVariable("userId") @Min(0) long userId,
            @PathVariable("eventId") @Min(0) long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequestDto) {
        return updateRequestStatusForCurrentUser(userId, eventId, eventRequestStatusUpdateRequestDto);
    }
}
