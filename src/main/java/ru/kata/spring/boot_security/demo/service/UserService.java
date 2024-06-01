package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


@Service
public interface UserService {


    User findById(int id);
    List<User> getAll();
    void update(int id, User user);
    void register(User user);
    void delete(int id);
}