package ru.practicum.users.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.users.dto.GetUserDto;
import ru.practicum.users.dto.PostUserDto;
import ru.practicum.users.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<GetUserDto> getAllUsers(List<Integer> ids, int from, int size) {
        return null;
    }

    @Override
    public GetUserDto createNewUser(PostUserDto postUserDto) {
        return null;
    }

    @Override
    public void deleteUser(long userId) {
    }
}
