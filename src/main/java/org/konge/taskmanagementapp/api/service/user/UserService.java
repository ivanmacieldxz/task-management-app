package org.konge.taskmanagementapp.api.service.user;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.auth.AuthResponseDTO;
import org.konge.taskmanagementapp.api.dto.auth.LoginRequestDTO;
import org.konge.taskmanagementapp.api.dto.auth.UserRegistrationDTO;
import org.konge.taskmanagementapp.api.dto.user.UserResponseDTO;
import org.konge.taskmanagementapp.api.model.user.User;
import org.konge.taskmanagementapp.api.repository.user.UserRepository;
import org.konge.taskmanagementapp.api.service.jwt.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResponseDTO registerUser(UserRegistrationDTO dto) {
        String registrationUsername = dto.username();
        String registrationEmail = dto.email();

        if (userRepository.existsByEmail(registrationEmail))
            throw new RuntimeException("Mail is already registered for other user.");

        if (userRepository.existsByUsername(registrationUsername))
            throw new RuntimeException("Username is already taken.");


        String encodedPassword = passwordEncoder.encode(
                dto.password()
        );

        User user = User.builder()
                .username(registrationUsername)
                .email(registrationEmail)
                .password(encodedPassword)
                .build();

        user = userRepository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository
                .findByEmail(loginRequestDTO.identifier())
                .or(() -> userRepository.findByUsername(loginRequestDTO.identifier()))
                .orElseThrow(() -> new RuntimeException("Login rejected: user not found."));

        boolean requestPasswordMatchesUserPassword = passwordEncoder
                .matches(
                        loginRequestDTO.password(),
                        user.getPassword()
                );

        if (!requestPasswordMatchesUserPassword)
            throw new RuntimeException("Login rejected: wrong password.");

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDTO(token);
    }
}
