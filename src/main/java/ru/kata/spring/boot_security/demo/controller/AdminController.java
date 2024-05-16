package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.MyUserDetails;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();

        Set<Role> roles = user.getUser().getRoles();
        StringBuilder rolesString = new StringBuilder();
        for (Role role : roles) {
            rolesString.append(role.getRole().substring(5)).append(" ");
        }
        model.addAttribute("rolesString", rolesString.toString());
        model.addAttribute("user", user.getUser());

        return "admin";
    }


//    @GetMapping("/admin/update")
//    public String updateNewForm(Model model, @RequestParam("id") int id) {
//        model.addAttribute("user", userService.findById(id));
//        return "update";
//    }
//    @GetMapping("/admin/update")
//    public String updateNewForm(Model model, @RequestParam("id") int id) {
//        User user = userService.findById(id);
//        List<Role> allRoles = userService.getAllRoles();
//        model.addAttribute("user", user);
//        model.addAttribute("allRoles", allRoles);
//        return "admin";
//    }

    @PostMapping("/admin/update")
    public ResponseEntity<String> update(@RequestBody User user) {
        // обновление пользователя на основе полученных данных
        userService.update(user);
        return ResponseEntity.ok("User updated successfully");
    }

//    @PostMapping("/admin/update/{id}")
//    public String update(@PathVariable("id") int id, @RequestBody User user) {
//        userService.update(id, user);
//        return "redirect:/admin";
//    }



    @GetMapping("/admin/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
