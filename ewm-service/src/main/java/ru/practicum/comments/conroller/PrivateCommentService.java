package ru.practicum.comments.conroller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comments.dto.PostCommentDto;
import ru.practicum.comments.dto.ReturnCommentDto;
import ru.practicum.comments.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class PrivateCommentService {
    private final CommentService commentService;

    @PostMapping("/event/{eventId}/user/{userId}")
    public ReturnCommentDto createNewComment(@PathVariable("eventId") Long eventId,
                                             @PathVariable("userId") Long userId,
                                             @Valid @RequestBody PostCommentDto commentDto) {
        return commentService.createNewComment(eventId, userId, commentDto);
    }

    @DeleteMapping("/{commentId}/user/{userId}")
    public void deleteComment(@PathVariable("commentId") Long commentId,
                              @PathVariable("userId") Long userId) {
        commentService.deleteComment(commentId, userId);
    }

    @PatchMapping("/{commentId}/user/{userId}")
    public ReturnCommentDto updateComment(@PathVariable("commentId") Long commentId,
                                          @PathVariable("userId") Long userId,
                                          @Valid @RequestBody PostCommentDto commentDto) {
        return commentService.updateComment(commentId, userId, commentDto);
    }
}
