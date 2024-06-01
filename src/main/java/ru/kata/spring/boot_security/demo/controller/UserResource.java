package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.validator.UserValidator;


@RestController
@RequestMapping("/api/v1/user")
public class UserResource {
    private final UserServiceImpl userService;

    @Autowired
    public UserResource(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/{userId}")
    public User findUserById(@PathVariable Integer userId) {
        return userService.findById(userId);
    }

}
