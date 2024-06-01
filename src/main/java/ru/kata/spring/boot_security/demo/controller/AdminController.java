package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.MyUserDetails;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {
    private final UserServiceImpl userService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserServiceImpl userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    @GetMapping("/admin")
    public String getAll(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("allUsers", users);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();

        model.addAttribute("user", user.getUser());

        return "admin";
    }

    @PostMapping("/admin/update")
    public String update(@RequestBody User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/save")
    public String save(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors())
            return "admin";
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/create")
    public String create(@RequestBody User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
