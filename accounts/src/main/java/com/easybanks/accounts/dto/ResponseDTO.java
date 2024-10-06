package com.easybanks.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "Response Schema",
description = "Schema to hold successful response information")
@AllArgsConstructor
@Data
public class ResponseDTO {
    @Schema(name = "Status code in the response")
    private String statusCode;

    @Schema(name = "Status message in the response")
    private String statusMessage;
}
