package ru.practicum.comments.conroller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comments.dto.ReturnCommentDto;
import ru.practicum.comments.service.CommentService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class PublicCommentController {
    private final CommentService commentService;

    @GetMapping("/event/{eventId}")
    public List<ReturnCommentDto> getAllCommentsForCurrentEvent(
            @PathVariable("eventId") Long eventId,
            @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) int from,
            @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) int size) {
        return commentService.getAllCommentsForCurrentEvent(eventId, from, size);
    }
}
