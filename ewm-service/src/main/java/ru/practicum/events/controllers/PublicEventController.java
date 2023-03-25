package ru.practicum.events.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.service.EventServiceImpl;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
@Validated
public class PublicEventController {
    private final EventServiceImpl eventService;

    @GetMapping
    public List<EventShortDto> getEventsWithFilter(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "paid", required = false) boolean paid,
            @RequestParam(name = "rangeStart", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(name = "onlyAvailable", required = false, defaultValue = "false") boolean onlyAvailable,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) int from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) int size) {
        return eventService.getEventsWithFilter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventWithFilterById(@PathVariable("id") @Min(0) long id) {
        return eventService.getEventWithFilterById(id);
    }
}
