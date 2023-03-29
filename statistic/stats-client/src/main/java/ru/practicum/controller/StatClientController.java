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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Long, Long> getStatistic(LocalDateTime start,
                                        LocalDateTime end,
                                        List<Long> uris,
                                        Boolean unique) {
        StringBuilder uriInRequestParameter = new StringBuilder();
        for (int i = 0; i < uris.size(); i++) {
            if (i < uris.size() - 2) {
                uriInRequestParameter.append("&uris=/events/").append(uris.get(i)).append(",");
            } else {
                uriInRequestParameter.append("&uris=/events/").append(uris.get(i));
            }
        }
        System.out.println("*********:&&&&&: " + uriInRequestParameter);
        List<GetStatDto> getStatDtoList = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start.format(formatter))
                        .queryParam("end", end.format(formatter))
                        .queryParam("uris", uriInRequestParameter)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(GetStatDto.class)
                .collectList()
                .block();
        if (getStatDtoList == null || getStatDtoList.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        Map<Long, Long> mapOfEventIdAndAmountOfView = new HashMap<>();

        for (GetStatDto getStatDto : getStatDtoList) {
            long eventId = Long.parseLong(getStatDto.getUri().replace("/events/", ""));
            mapOfEventIdAndAmountOfView.put(eventId, getStatDto.getHits());
        }
        return mapOfEventIdAndAmountOfView;
    }
}

