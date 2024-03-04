package com.epam.learn.rest.jmpservicerest;

import com.epam.learn.rest.jmpdto.User;
import com.epam.learn.rest.jmpdto.UserMapper;
import com.epam.learn.rest.jmpdto.UserRequestDto;
import com.epam.learn.rest.jmpdto.UserResponseDto;
import com.epam.learn.rest.jmpserviceapi.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        var user = mapper.toUser(userRequestDto);
        var createdUser = userService.createUser(user);

        return mapper.toUserResponseDto(createdUser);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        var user = verifyUser(userId);

        user.setName(userRequestDto.getName());
        user.setSurname(userRequestDto.getSurname());
        user.setBirthday(LocalDate.parse(userRequestDto.getBirthday()));

        var updatedUser = userService.updateUser(user);

        return mapper.toUserResponseDto(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        var user = verifyUser(userId);

        return mapper.toUserResponseDto(user);
    }

    @GetMapping
    public List<UserResponseDto> getAllUser() {
        return StreamSupport.stream(userService.getAllUser().spliterator(), false)
                        .map(mapper::toUserResponseDto).toList();
    }

    private User verifyUser(Long userId) {
        return userService.getUser(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with id " + userId));
    }
}
