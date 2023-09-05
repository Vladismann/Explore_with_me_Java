package ru.practicum.dto.Common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

    private String status;
    private String reason;
    private String message;
    private String timestamp;
}
