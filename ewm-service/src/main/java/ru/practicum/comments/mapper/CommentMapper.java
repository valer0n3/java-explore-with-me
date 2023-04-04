package ru.practicum.comments.mapper;

import org.mapstruct.Mapper;
import ru.practicum.comments.dto.PostCommentDto;
import ru.practicum.comments.dto.ReturnCommentDto;
import ru.practicum.comments.model.CommentModel;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentModel mapPostCommentDtoToCommentModel(PostCommentDto commentDto);

    ReturnCommentDto mapCommentModelToReturnCommentDto(CommentModel commentModel);
}
