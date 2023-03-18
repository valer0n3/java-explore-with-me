package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
