package ru.practicum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StatClientController {
    private final WebClient webClient;

    public StatClientController() {
        this.webClient = WebClient.create("http://localhost:9090");
    }

    @PostMapping("/test")
    public PostStatDto addNewStatistic(@RequestBody PostStatDto postStatDto) {
        System.out.println("********** Client Post recieved" + postStatDto);
        PostStatDto objectResponseEntity = webClient.post()
                .uri("/hit")
                .body(BodyInserters.fromValue(postStatDto))
                .retrieve()
                .bodyToMono(PostStatDto.class)
                .block();
        return objectResponseEntity;
    }

    @GetMapping("/test")
    public List<GetStatDto> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<GetStatDto> getStatDtoList = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(GetStatDto.class)
                .collectList()
                .block();
        return getStatDtoList;
    }
}

