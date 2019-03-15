package com.ham.server.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PixyException extends Exception {
    /*custom exception json*/
    private OperationResponse.ResponseStatusEnum status;
    private String message;
}
