package ru.practicum.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.GetStatDto;
import ru.practicum.PostStatDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class StatClientController {
    private final WebClient webClient;
    private final String appName;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public StatClientController(@Value("${stats-server.url}") String statServerUrl,
                                @Value("${appName}") String appName) {
        this.webClient = WebClient.create(statServerUrl);
        this.appName = appName;
    }

    public PostStatDto addNewStatistic(HttpServletRequest httpServletRequest) {
        PostStatDto postStatDto = PostStatDto.builder()
                .app(appName)
                .uri(httpServletRequest.getRequestURI())
                .ip(httpServletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
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
                                         Boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start.format(formatter))
                        .queryParam("end", end.format(formatter))
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(GetStatDto.class)
                .collectList()
                .block();
    }
}

