package ru.practicum.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;
import ru.practicum.service.StatService;

import java.util.List;

@RestController
@AllArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    public PostStatDto addNewStatistic(@RequestBody PostStatDto postStatDto) {
        return statService.addNewStatistic(postStatDto);
    }

    @GetMapping("/stats")
    public List<GetStatDto> getStatistics(@RequestParam(name = "start", required = true) String start,
                                          @RequestParam(name = "end", required = true) String end,
                                          @RequestParam(name = "uris", required = false) List<String> uris,
                                          @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        return statService.getStatistics(start, end, uris, unique);
    }
}