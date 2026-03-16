package com.example.demo.config;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, 
                                   RoleRepository roleRepository, 
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Initialize ADMIN Role
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(RoleName.ROLE_ADMIN);
                        return roleRepository.save(newRole);
                    });
            
            // 2. Initialize USER Role
            roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(RoleName.ROLE_USER);
                        return roleRepository.save(newRole);
                    });

            // 3. Create Master Admin
            String adminEmail = "adminforyou@gmail.com";
            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = new User();
                admin.setName("System Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("Admin@123")); // Permanent Password
                admin.setRoles(Collections.singleton(adminRole));
                
                userRepository.save(admin);
                System.out.println("✅ [System] Master Admin created: " + adminEmail);
            }
        };
    }
}