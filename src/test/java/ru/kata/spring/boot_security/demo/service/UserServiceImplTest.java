package ru.kata.spring.boot_security.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepo;
import ru.kata.spring.boot_security.demo.repositories.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;

public class UserServiceImplTest {
    private List<User> users;
    private User user;
    @Mock
    private UserRepo userRepo;
    @Mock
    private RoleRepo roleRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        users = new ArrayList<>();
        users.add(new User("User1", "User1", "User1"));
        users.add(new User("User2", "User2", "User2"));
        users.add(new User("User3", "User3", "User3"));

        user = new User(1, "User-Name", "User-Surname", "User-Sex");
        user.setRoles(Set.of(new Role("ROLE_USER")));
    }


    @Test
    void registerTest() {
        Mockito.when(userRepo.save(user)).thenReturn(user);
        Mockito.when(roleRepo.findRoleByRole("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("password");

        userServiceImpl.register(user);

        Mockito.verify(userRepo).save(user);
        Mockito.verify(roleRepo).findRoleByRole("ROLE_USER");
    }

    @Test
    void saveTest() {
        Mockito.when(userRepo.save(user)).thenReturn(user);
        Mockito.when(roleRepo.findRoleByRole("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("password");

        userServiceImpl.save(user);

        Mockito.verify(userRepo).save(user);
        Mockito.verify(roleRepo).findRoleByRole("ROLE_USER");
    }


    @Test
    void deleteTest() {
        Mockito.doNothing().when(userRepo).deleteById(anyInt());

        Assertions.assertDoesNotThrow(() -> userServiceImpl.delete(1));
        Mockito.verify(userRepo).deleteById(1);
    }

    @Test
    void getAllTest() {
        Mockito.when(userRepo.findAll()).thenReturn(users);
        List<User> actualUsers = userServiceImpl.getAll();
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User("User1", "User1", "User1"));
        expectedUsers.add(new User("User2", "User2", "User2"));
        expectedUsers.add(new User("User3", "User3", "User3"));
        Mockito.verify(userRepo).findAll();
        assert actualUsers.equals(expectedUsers);
    }
}
