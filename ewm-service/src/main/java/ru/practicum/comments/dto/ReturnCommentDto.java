package ru.practicum.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.events.model.EventModel;
import ru.practicum.users.model.UserModel;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReturnCommentDto {
    private int id;
    private String text;
    private EventModel event;
    private UserModel user;
    private LocalDateTime created;
}
