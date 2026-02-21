package org.konge.taskmanagementapp.api.dto.boardlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardListRequestDTO(
        @NotBlank(message = "List name is required.")
        @Size(max = 50, message = "List name must not exceed 50 characters.")
        String name,

        @Size(max = 255, message = "Description must not exceed 255 characters.")
        String description
) { }