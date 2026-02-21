package org.konge.taskmanagementapp.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.model.user.UserDetailsDTO;
import org.konge.taskmanagementapp.api.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDetailsDTO> getUserDetails() {
        return ResponseEntity.ok(userService.getCurrentUserDetails());
    }

}
