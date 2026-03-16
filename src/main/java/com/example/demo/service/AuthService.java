package com.example.demo.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {

    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private RoleRepository roleRepository;
    
    @Autowired 
    private PasswordEncoder passwordEncoder;

    /**
     * ✅ Secure Web Registration
     * This method now ignores any role sent from the frontend.
     * Every person registering via the website is automatically assigned ROLE_USER.
     */
    @Transactional
    public User register(RegisterRequest request) {
<<<<<<< HEAD
        // 1. Check if email already exists
=======
       // System.out.println("ROLE FROM REQUEST: " + request.getRole());

>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        //String roleName = "ROLE_" + request.getRole().toUpperCase();

<<<<<<< HEAD
        // 2. Map DTO to User Entity
=======
        // Role role = roleRepository.findByName(request.getRole())
        //         .orElseThrow(() -> new RuntimeException("Role not found"));

        RoleName roleName = request.getRole();
        if(roleName == null){
            roleName = RoleName.ROLE_USER;
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 3. SECURE ROLE ASSIGNMENT
        // We fetch the ROLE_USER from the DB (which was created by DataInitializer)
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Critical Error: Default Role ROLE_USER not found."));

        // Set the role using a singleton set for efficiency
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }
}