package org.konge.taskmanagementapp.api.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.auth.AuthResponseDTO;
import org.konge.taskmanagementapp.api.dto.auth.LoginRequestDTO;
import org.konge.taskmanagementapp.api.dto.auth.UserRegistrationDTO;
import org.konge.taskmanagementapp.api.dto.user.UserResponseDTO;
import org.konge.taskmanagementapp.api.service.user.UserService;
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
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserResponseDTO registeredUserResponseDTO = userService.registerUser(userRegistrationDTO);

        return new ResponseEntity<>(registeredUserResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }

}
