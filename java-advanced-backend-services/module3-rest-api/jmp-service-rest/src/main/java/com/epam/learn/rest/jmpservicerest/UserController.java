package com.epam.learn.rest.jmpservicerest;

import com.epam.learn.rest.jmpdto.User;
import com.epam.learn.rest.jmpdto.UserRequestDto;
import com.epam.learn.rest.jmpdto.UserResponseDto;
import com.epam.learn.rest.jmpserviceapi.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        var user = userService.createUser(fromDto(userRequestDto));
        return toDto(user);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        var user = verifyUser(userId);

        user.setName(userRequestDto.getName());
        user.setSurname(userRequestDto.getSurname());
        user.setBirthday(LocalDate.parse(userRequestDto.getBirthday()));

        return toDto(userService.updateUser(user));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return toDto(verifyUser(userId));
    }

    @GetMapping
    public List<UserResponseDto> getAllUser() {
        return StreamSupport.stream(userService.getAllUser().spliterator(), false)
                        .map(this::toDto).toList();
    }

    private User verifyUser(Long userId) {
        return userService.getUser(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with id " + userId));
    }

    private UserResponseDto toDto(User user) {
        var dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setBirthday(String.valueOf(user.getBirthday()));

        return dto;
    }

    private User fromDto(UserRequestDto dto) {
        var user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setBirthday(LocalDate.parse(dto.getBirthday()));

        return user;
    }
}
