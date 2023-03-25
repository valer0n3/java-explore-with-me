package ru.practicum.users.conroller;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.exceptions.EwmServiceNotFound;
import ru.practicum.users.dto.GetUserDto;
import ru.practicum.users.dto.PostUserDto;
import ru.practicum.users.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("admin/users")
@AllArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<GetUserDto> getAllUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                        @RequestParam(name = "from", defaultValue = "0") @Min(0) int from,
                                        @RequestParam(name = "size", defaultValue = "10") @Min(1) int size) {
        return userService.getAllUsers(ids, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetUserDto createNewUser(@Valid @RequestBody PostUserDto postUserDto) {
        return userService.createNewUser(postUserDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") long userId) {
        try {
            userService.deleteUser(userId);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new EwmServiceNotFound(String.format("User with id=%d was not found", userId));
        }
    }
}
