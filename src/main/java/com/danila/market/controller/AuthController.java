package com.danila.market.controller;

import com.danila.market.dto.JwtRequest;
import com.danila.market.dto.UserDTO;
import com.danila.market.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest authRequest) {
        return authService.createToken(authRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveNewUser(@RequestBody UserDTO userDTO) {
        return authService.saveNewUser(userDTO);
    }
}
