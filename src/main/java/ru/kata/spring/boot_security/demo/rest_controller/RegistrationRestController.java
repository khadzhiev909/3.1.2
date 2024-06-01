package ru.kata.spring.boot_security.demo.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("api/register")
public class RegistrationRestController {
    private final UserService userService;

    @Autowired
    public RegistrationRestController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping()
    public ResponseEntity<Void> performRegistration(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok().build();
    }
}
