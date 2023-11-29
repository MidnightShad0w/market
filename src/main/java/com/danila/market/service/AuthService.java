package com.danila.market.service;

import com.danila.market.dto.JwtRequest;
import com.danila.market.dto.JwtResponse;
import com.danila.market.dto.UserDTO;
import com.danila.market.exceptions.AppError;
import com.danila.market.jwt.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> saveNewUser(@RequestBody UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getMatchingPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords do not match"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(userDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "User already exists"), HttpStatus.BAD_REQUEST);
        }
        userService.save(userDTO);
        return ResponseEntity.ok(new UserDTO(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getMatchingPassword()));
    }
}
