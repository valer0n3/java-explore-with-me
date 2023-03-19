package ru.practicum.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatClientController {
    private final WebClient webClient;

    public StatClientController(@Value("{stats-server.url}") String statServerUrl) {
        this.webClient = WebClient.create(statServerUrl);
    }

    public PostStatDto addNewStatistic(PostStatDto postStatDto) {
        return webClient.post()
                .uri("/hit")
                .body(BodyInserters.fromValue(postStatDto))
                .retrieve()
                .bodyToMono(PostStatDto.class)
                .block();
    }

    public List<GetStatDto> getStatistic(LocalDateTime start,
                                         LocalDateTime end,
                                         List<String> uris,
                                         boolean unique) {
        return webClient.get()
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
    }
}

