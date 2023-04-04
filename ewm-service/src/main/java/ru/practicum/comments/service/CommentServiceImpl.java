package ru.practicum.comments.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.dto.PostCommentDto;
import ru.practicum.comments.dto.ReturnCommentDto;
import ru.practicum.comments.mapper.CommentMapper;
import ru.practicum.comments.model.CommentModel;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.events.model.EventModel;
import ru.practicum.events.service.EventServiceImpl;
import ru.practicum.exceptions.EwmServiceConflictException;
import ru.practicum.exceptions.EwmServiceNotFound;
import ru.practicum.users.model.UserModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventServiceImpl eventServiceImpl;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public ReturnCommentDto createNewComment(Long eventId, Long userId, PostCommentDto commentDto) {
        EventModel eventModel = eventServiceImpl.checkIfEventExists(eventId);
        UserModel userModel = eventServiceImpl.checkIfUserExists(userId);
        CommentModel commentModel = commentMapper.mapPostCommentDtoToCommentModel(commentDto);
        commentModel.setText(commentDto.getText());
        commentModel.setEvent(eventModel);
        commentModel.setUser(userModel);
        commentModel.setCreated(LocalDateTime.now());
        return commentMapper.mapCommentModelToReturnCommentDto(commentRepository.save(commentModel));
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        CommentModel comment = checkIfCommentExists(commentId);
        checkIfCurrentUserCreatedComment(userId, comment);
        commentRepository.deleteById(commentId);
    }

    @Override
    public ReturnCommentDto updateComment(Long commentId, Long userId, PostCommentDto commentDto) {
        CommentModel commentModel = checkIfCommentExists(commentId);
        if (commentDto.getText() != null && !commentDto.getText().isBlank()) {
            commentModel.setText(commentDto.getText());
        }
        commentModel.setCreated(LocalDateTime.now());
        return commentMapper.mapCommentModelToReturnCommentDto(commentRepository.save(commentModel));
    }

    @Override
    public void deleteAllCommentForCurrentEvent(Long eventId) {
        commentRepository.deleteAllByEventId(eventId);
    }

    @Override
    public List<ReturnCommentDto> getAllCommentsForCurrentEvent(Long eventId, Long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return commentRepository.getAllByEventId(eventId, pageable).stream()
                .map(commentMapper::mapCommentModelToReturnCommentDto)
                .collect(Collectors.toList());
    }

    private CommentModel checkIfCommentExists(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EwmServiceNotFound(String
                        .format("Comment with id=%d was not found", commentId)));
    }

    private void checkIfCurrentUserCreatedComment(Long userId, CommentModel comment) {
        if (!comment.getUser().equals(userId)) {
            throw new EwmServiceConflictException(String
                    .format("User id: %s can't update current comment id: %s", userId, comment.getId()));
        }
    }
}
