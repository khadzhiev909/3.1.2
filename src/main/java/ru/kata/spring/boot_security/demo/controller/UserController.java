package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.MyUserDetails;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;


@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String userName, @RequestParam String userEmail) {
        System.out.println(userEmail + " " + userName);
        // Здесь вы можете добавить логику для сохранения нового пользователя в базу данных или выполнить другие действия
        return "redirect:/admin"; // Перенаправляем пользователя обратно на страницу администратора
    }

    @GetMapping("/user")
    public String getInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();

        Set<Role> roles = user.getUser().getRoles();
        StringBuilder rolesString = new StringBuilder();
        for (Role role : roles) {
            rolesString.append(role.getRole().substring(5)).append(" ");
        }
        model.addAttribute("rolesString", rolesString.toString());
        model.addAttribute("user", user.getUser());
        return "user";
    }
}