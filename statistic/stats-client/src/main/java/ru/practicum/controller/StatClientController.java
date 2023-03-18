package ru.practicum.controller;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;

import java.time.LocalDateTime;
import java.util.List;

public class StatClientController {
    private final WebClient webClient;

    public StatClientController() {
        this.webClient = WebClient.create("http://localhost:9090");
    }

    public PostStatDto addNewStatistic(PostStatDto postStatDto) {
        PostStatDto objectResponseEntity = webClient.post()
                .uri("/hit")
                .body(BodyInserters.fromValue(postStatDto))
                .retrieve()
                .bodyToMono(PostStatDto.class)
                .block();
        return objectResponseEntity;
    }

    public List<GetStatDto> getStatistic(LocalDateTime start,
                                         LocalDateTime end,
                                         List<String> uris,
                                         boolean unique) {
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

