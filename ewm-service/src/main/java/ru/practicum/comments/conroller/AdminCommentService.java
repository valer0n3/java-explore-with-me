package ru.practicum.comments.conroller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comments.service.CommentService;

@RestController
@RequestMapping("comments")
@AllArgsConstructor
public class AdminCommentService {
    private final CommentService commentService;

    @DeleteMapping("/all/event/{eventId}")
    public void deleteAllCommentForCurrentEvent(@PathVariable("eventId") Long eventId) {
    }
}
