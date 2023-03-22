package ru.practicum.users.service;

import ru.practicum.users.dto.GetUserDto;
import ru.practicum.users.dto.PostUserDto;

import java.util.List;

public interface UserService {
    List<GetUserDto> getAllUsers(List<Integer> ids, int from, int size);


    GetUserDto createNewUser(PostUserDto postUserDto);

    void deleteUser(long userId);
}
