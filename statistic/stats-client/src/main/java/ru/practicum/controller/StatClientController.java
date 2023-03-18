package ru.practicum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.PostStatDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
public class StatClientController {
    private final WebClient webClient;

    public StatClientController() {
        this.webClient = WebClient.create("http://localhost:9091");
    }



   @PostMapping("/hit")
    public ResponseEntity<Object> addNewStatistic(HttpServletRequest httpServletRequest) {
        PostStatDto postStatDto = new PostStatDto();
        postStatDto.setApp("ewm-main-service");
        postStatDto.setUri(httpServletRequest.getRequestURI());
        postStatDto.setIp(httpServletRequest.getRemoteAddr());
        postStatDto.setTimestamp(LocalDateTime.now());
        ResponseEntity<Object> objectResponseEntity = webClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(postStatDto))
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(Object.class)
                                .map(body -> ResponseEntity.status(HttpStatus.CREATED).body(body));
                    } else {
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                })
                .block();
        System.out.println("********************:" + objectResponseEntity);
        return objectResponseEntity;
    }
}

