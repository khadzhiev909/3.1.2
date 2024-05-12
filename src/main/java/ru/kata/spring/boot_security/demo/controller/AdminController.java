package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping("/admin")
    public String getAll(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("allUsers", users);
        return "admin";
    }


//    @GetMapping("/admin/update")
//    public String updateNewForm(Model model, @RequestParam("id") int id) {
//        model.addAttribute("user", userService.findById(id));
//        return "update";
//    }
    @GetMapping("/admin/update")
    public String updateNewForm(Model model, @RequestParam("id") int id) {
        User user = userService.findById(id);
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "update";
    }

    @PatchMapping("/admin/update")
    public String update(@RequestParam("id") int id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin";
    }


    @GetMapping("/admin/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
