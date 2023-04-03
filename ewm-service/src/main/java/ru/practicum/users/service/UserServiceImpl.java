package ru.practicum.users.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.users.dto.GetUserDto;
import ru.practicum.users.dto.PostUserDto;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<GetUserDto> getAllUsers(List<Long> ids, int from, int size) {
        Pageable pageble = PageRequest.of(from / size, size);
        if (ids == null || ids.isEmpty()) {
            return userRepository.findAll(pageble).stream()
                    .map(userMapper::mapUserModelToGetUserDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAllByIdIn(ids, pageble).stream()
                    .map(userMapper::mapUserModelToGetUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public GetUserDto createNewUser(PostUserDto postUserDto) {
        return userMapper.mapUserModelToGetUserDto(userRepository
                .save(userMapper.mapPostUserDtoToUserModel(postUserDto)));
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
