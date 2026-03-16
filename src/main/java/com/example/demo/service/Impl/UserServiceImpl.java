package com.example.demo.service.Impl;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User register(User user) {

        // ✅ Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // ✅ Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ✅ Assign ROLE_USER
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Collections.singleton(role));

        // ✅ Save user
        User savedUser = userRepository.save(user);

        // ✅ Send Welcome Email (Do not break registration if email fails)
        try {
            emailService.sendEmail(
                    savedUser.getEmail(),
                    "Welcome to Our Store 🎉",
                    "Hi " + savedUser.getName() + ",\n\n"
                            + "Your account has been created successfully.\n\n"
                            + "You can now login and start shopping.\n\n"
                            + "Thank you for joining us!"
            );
        } catch (Exception e) {
            System.out.println("Welcome email failed, but user registered successfully.");
        }

        return savedUser;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
