package ru.kata.spring.boot_security.demo.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.MyUserDetails;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


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

    @GetMapping("/current-user")
    public ResponseEntity<MyUserDetails> getCurrentUser(Authentication authentication) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }
}
