package ru.practicum.comments.service;

import ru.practicum.comments.dto.PostCommentDto;
import ru.practicum.comments.dto.ReturnCommentDto;

import java.util.List;

public interface CommentService {
    ReturnCommentDto createNewComment(Long eventId,
                                      Long userId,
                                      PostCommentDto commentDto);

    void deleteComment(Long commentId,
                       Long userId);

    ReturnCommentDto updateComment(Long commentId,
                                   Long userId,
                                   PostCommentDto commentDto);

    void deleteAllCommentForCurrentEvent(Long eventId);

    List<ReturnCommentDto> getAllCommentsForCurrentEvent(
            Long eventId,
            Long userId,
            int from,
            int size);
}
