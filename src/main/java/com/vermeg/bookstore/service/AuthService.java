package com.vermeg.bookstore.service;

import com.vermeg.bookstore.dto.RegisterRequest;
import com.vermeg.bookstore.entities.User;
import com.vermeg.bookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());
        user.setRoles("USER");

        userRepository.save(user);
    }


}
