package com.easybank.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
public class ErrorDTO {
    private String requestPath;
    private HttpStatus errorStatusCode;
    private String errorMessage;
    private LocalDateTime errorTime;
}
