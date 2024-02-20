package com.epam.learn.rest.jmpserviceapi;

import java.util.Optional;

import com.epam.learn.rest.jmpdto.User;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    Optional<User> getUser(Long userId);

    Iterable<User> getAllUser();
}
