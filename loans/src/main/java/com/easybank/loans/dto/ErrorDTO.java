package com.easybank.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(name = "Error Response",
        description = "The instance of this class is send as response to the client in case of any error")
public class ErrorDTO {
    @Schema(description = "API path invoked by client")
    private String path;
    @Schema(description = "Error code representing the error happened")
    private HttpStatus errorCode;
    @Schema(description = "Error message representing the error happened")
    private String errorMessage;
    @Schema(description = "Time representing when the error happened")
    private LocalDateTime errorTime;
}
