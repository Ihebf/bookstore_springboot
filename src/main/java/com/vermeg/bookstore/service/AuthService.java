package com.vermeg.bookstore.service;

import com.vermeg.bookstore.dto.LoginRequest;
import com.vermeg.bookstore.dto.RegisterRequest;
import com.vermeg.bookstore.entities.User;
import com.vermeg.bookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());
        user.setRoles("USER");
        user.setOrder(null);

        userRepository.save(user);
    }


    public ResponseEntity login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder
                .getContext()
                .setAuthentication(authenticate);
        System.err.println("login request: "+loginRequest);
        System.err.println("authenticate: "+authenticate);
        return new ResponseEntity(loginRequest.getUsername(), HttpStatus.OK);
    }
}
