package ru.practicum.events.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.UpdateEventAdminAndUserRequestDTO;
import ru.practicum.events.service.EventServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.constants.ProjectConstants.DATE_TIME_PATTERN;

@RestController
@RequestMapping("/admin/events")
@AllArgsConstructor
@Validated
public class AdminEventController {
    private final EventServiceImpl eventService;

    @GetMapping
    public List<EventFullDto> getAllEventsAdmin(@RequestParam(name = "users", required = false) List<Long> users,
                                                @RequestParam(name = "states", required = false) List<String> state,
                                                @RequestParam(name = "categories", required = false) List<Long> categories,
                                                @RequestParam(name = "rangeStart", required = false)
                                                @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                                @RequestParam(name = "rangeEnd", required = false)
                                                @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                                @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) int from,
                                                @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) int size) {
        return eventService.getAllEventsAdmin(users, state, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventAndStatusAdmin(
            @PathVariable("eventId") @Min(0) long eventId,
            @Valid @RequestBody UpdateEventAdminAndUserRequestDTO updateEventAdminAndUserRequestDTO) {
        return eventService.updateEventAndStatusAdmin(eventId, updateEventAdminAndUserRequestDTO);
    }
}
