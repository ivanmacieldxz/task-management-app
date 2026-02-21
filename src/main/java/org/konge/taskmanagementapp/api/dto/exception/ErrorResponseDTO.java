package org.konge.taskmanagementapp.api.dto.exception;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        LocalDateTime timestamp,
        String path,
        Map<String, String> validationErrors
) {}