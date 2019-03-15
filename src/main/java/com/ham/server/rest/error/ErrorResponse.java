package com.ham.server.rest.error;

import com.ham.server.rest.dto.response.OperationResponse;
import com.ham.server.rest.dto.response.PixyException;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Mahdi on 11/30/2017.
 */
@Data
@NoArgsConstructor
/* ErrorResponse json extends from OperationResponse class that customized*/
public class ErrorResponse extends OperationResponse {
    public ErrorResponse(ResponseStatusEnum status, String message) {
        super(status,message);
    }
    public ErrorResponse(PixyException e) {
        super(e.getStatus(),e.getMessage());
    }
}
