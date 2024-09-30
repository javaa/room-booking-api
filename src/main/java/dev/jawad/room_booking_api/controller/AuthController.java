package dev.jawad.room_booking_api.controller;

import dev.jawad.room_booking_api.model.Room;
import dev.jawad.room_booking_api.model.User;
import dev.jawad.room_booking_api.repository.UserRepository;
import dev.jawad.room_booking_api.security.JwtTokenProvider;
import dev.jawad.room_booking_api.service.TokenBlacklistService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import dev.jawad.room_booking_api.payload.JwtAuthenticationResponse;
import dev.jawad.room_booking_api.payload.LoginRequest;
import dev.jawad.room_booking_api.payload.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    TokenBlacklistService tokenBlacklistService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        // if(userRepository.existsByUsername(signUpRequest.getUsername())) {
        //     return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        // }

        // if(userRepository.existsByEmail(signUpRequest.getEmail())) {
        //     return new ResponseEntity<>("Email Address already in use!", HttpStatus.BAD_REQUEST);
        // }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getRole());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (tokenProvider.validateToken(token)) {
                tokenBlacklistService.blacklistToken(token);
                return ResponseEntity.ok("User signed out successfully");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
