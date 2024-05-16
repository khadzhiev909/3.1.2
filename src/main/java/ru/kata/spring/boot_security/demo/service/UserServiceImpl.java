package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepo;
import ru.kata.spring.boot_security.demo.repositories.UserRepo;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findRoleByRole("ROLE_USER"));
        user.setRoles(roles);
        userRepo.save(user);
    }
    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findRoleByRole("ROLE_ADMIN"));
        user.setRoles(roles);
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User existingUser = userRepo.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + user.getId()));
        existingUser.setUsername(user.getUsername());
        existingUser.setSurname(user.getSurname());
        existingUser.setSex(user.getSex());
        existingUser.setRoles(user.getRoles());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(existingUser);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public User findById(int id) {
        return userRepo.getById(id);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return userRepo.findAll();
    }

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}