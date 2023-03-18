package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class StatClientApp {
    public static void main(String[] args) {
        SpringApplication.run(StatClientApp.class, args);
    }

 /*   @Bean
    public WebClient test() {
        return WebClient.create("http://localhost:9091");
    }*/
}
