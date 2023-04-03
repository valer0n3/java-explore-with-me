package ru.practicum.users.mapper;

import org.mapstruct.Mapper;
import ru.practicum.users.dto.GetUserDto;
import ru.practicum.users.dto.PostUserDto;
import ru.practicum.users.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
    GetUserDto mapUserModelToGetUserDto(UserModel userModel);

    UserModel mapPostUserDtoToUserModel(PostUserDto postUserDto);
}
