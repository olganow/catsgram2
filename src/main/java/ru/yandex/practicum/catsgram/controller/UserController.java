package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Set<User> users = new HashSet<>();

    @GetMapping()
    public Set<User> findAll() {
        return users;
    }


    @PostMapping()
    public  User create(@RequestBody User user)  throws UserAlreadyExistException, InvalidEmailException {
        if (users.contains(user)) {
            throw new UserAlreadyExistException("User id not exists");
        } else {
            users.add(user);
        }

        if (user.getEmail().isBlank() || user.getEmail() == null) {
            throw new InvalidEmailException("Fill in email");
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws InvalidEmailException {
        if (user.getEmail().isBlank() || user.getEmail() == null) {
            throw new InvalidEmailException("Fill in email");
        }

        if (!users.contains(user)) {
            users.add(user);
        } else {

            for (User user1 : users) {
                if (user1.equals(user)) {
                    user1.setNickname(user.getNickname());
                    user1.setBirthdate(user.getBirthdate());
                    user1.setEmail(user.getEmail());
                }
            }

        }
        return user;
    }

    @DeleteMapping
    public void delete() {
        users.clear();

    }
}
