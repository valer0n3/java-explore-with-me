package ru.practicum.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.events.dto.EventCommentDto;
import ru.practicum.users.dto.UserShortDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReturnCommentDto {
    private int id;
    private String text;
    private EventCommentDto event;
    private UserShortDto user;
    private LocalDateTime created;
}
