package ru.practicum.users.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.users.dto.GetUserDto;
import ru.practicum.users.dto.PostUserDto;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<GetUserDto> getAllUsers(List<Integer> ids, int from, int size) {
        Pageable pageWithElements = PageRequest.of(from / size, size /*Sort.by("start").descending()*/);
        return null;
    }

    @Override
    public GetUserDto createNewUser(PostUserDto postUserDto) {
        return userMapper.mapUserModelToGetUserDto(userRepository
                .save(userMapper.mapPostUserDtoToUserModel(postUserDto)));
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
