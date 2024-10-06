package com.easybanks.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(name = "ErrorResponse Schema",
        description = "Schema to hold error response information")
@AllArgsConstructor
@Data
public class ErrorDTO {
    @Schema(name = "Field to hold API path request in case of error")
    private String apiPath;

    @Schema(name = "Field to hold status code case of error")
    private HttpStatus errorCode;

    @Schema(name = "Field to hold error message in case of error")
    private String statusMessage;

    @Schema(name = "Field to hold error date and time in case of error")
    private LocalDateTime errorTime;
}
