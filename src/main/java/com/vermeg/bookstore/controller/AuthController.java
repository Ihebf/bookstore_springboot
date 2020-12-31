package com.vermeg.bookstore.controller;

import com.vermeg.bookstore.dto.LoginRequest;
import com.vermeg.bookstore.dto.RegisterRequest;
import com.vermeg.bookstore.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>(
                "User Registration Successful",
                OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

}
