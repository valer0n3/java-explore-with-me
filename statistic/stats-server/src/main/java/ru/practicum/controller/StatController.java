package ru.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.constants.ProjectConstants.DATE_TIME_PATTERN;

@RestController
@AllArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public PostStatDto addNewStatistic(@Validated @RequestBody PostStatDto postStatDto) {
        return statService.addNewStatistic(postStatDto);
    }

    @GetMapping("/stats")
    public List<GetStatDto> getStatistics(@RequestParam(name = "start", required = false)
                                          @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
                                          @RequestParam(name = "end", required = false)
                                          @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
                                          @RequestParam(name = "uris", required = false) List<String> uris,
                                          @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return statService.getStatistics(start, end, uris, unique);
    }

    @GetMapping("/test")
    public List<GetStatDto> getStatistics2(@RequestParam(name = "start", required = false)
                                           @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
                                           @RequestParam(name = "end", required = false)
                                           @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
                                           @RequestParam(name = "uris", required = false) List<String> uris,
                                           @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return null;
    }
}
