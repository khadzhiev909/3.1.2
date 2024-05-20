package ru.kata.spring.boot_security.demo.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validator.UserValidator;


@RestController
@RequestMapping("api/registration")
public class RegistrationRestController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public RegistrationRestController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    @PostMapping()
    public ResponseEntity<Void> performRegistration(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok().build();
    }
}
