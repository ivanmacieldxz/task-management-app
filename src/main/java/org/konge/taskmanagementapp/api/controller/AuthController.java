package org.konge.taskmanagementapp.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.UserRegistrationDTO;
import org.konge.taskmanagementapp.api.model.User;
import org.konge.taskmanagementapp.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
        User registeredNewUser = userService.registerUser(dto);

        return new ResponseEntity<>(registeredNewUser, HttpStatus.CREATED);
    }

}
