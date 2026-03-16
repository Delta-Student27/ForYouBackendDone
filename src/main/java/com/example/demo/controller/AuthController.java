package com.example.demo.controller;

import com.example.demo.dto.LoginRequest; // Or use Map if you don't have this DTO
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.util.Map;
=======
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import com.example.demo.service.AuthService;
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b

@RestController
@RequestMapping("/api/auth")
public class AuthController {

<<<<<<< HEAD
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
=======
    private final AuthService authService;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService,
                          UserService userService,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User savedUser = authService.register(request);
        return ResponseEntity.ok(savedUser);
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // 1. Find user by email
        User user = userRepository.findByEmail(email)
                .orElse(null);

<<<<<<< HEAD
        // 2. Validate user and password
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
=======
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.status(401)
                        .body(Map.of("error", "Invalid email or password"));
            }

            String role = user.getRoles()
                  .iterator()
                  .next()
                  .getName()
                  .name();

            String token = jwtUtil.generateToken( user.getEmail(),
        role);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid email or password"));
>>>>>>> 741898d3e74e4280ae07b80cadfddd417e0b471b
        }

        // 3. Extract the role name (e.g., ROLE_ADMIN) to put in the JWT
        // We get the first role from the Set of roles
        String role = user.getRoles().iterator().next().getName().name();

        // 4. Generate token with BOTH email and role
        String token = jwtUtil.generateToken(email, role);

        // 5. Return the token to the frontend
        return ResponseEntity.ok(Map.of(
            "token", token,
            "message", "Login successful"
        ));
    }
}