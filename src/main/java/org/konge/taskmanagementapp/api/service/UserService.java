package org.konge.taskmanagementapp.api.service;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.UserRegistrationDTO;
import org.konge.taskmanagementapp.api.model.User;
import org.konge.taskmanagementapp.api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public User registerUser(UserRegistrationDTO dto) {
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
        
        return userRepository.save(user);
    }
}
