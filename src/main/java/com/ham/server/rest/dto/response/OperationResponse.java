/**
 * This is the common structure for all responses
 * if the response contains a list(array) then it will have 'items' field
 * if the response contains a single item then it will have 'item'  field
 */


package com.ham.server.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationResponse {
    /*make enum for HttpStatus codes and make custom entity*/
    public enum ResponseStatusEnum {
        SUCCESS(HttpStatus.OK), WARNING(HttpStatus.OK), ERROR(HttpStatus.INTERNAL_SERVER_ERROR), SUBSCRIPTION_NO_ACCESS(HttpStatus.FORBIDDEN),
        NO_ACCESS(HttpStatus.FORBIDDEN), BAD_REQUEST(HttpStatus.BAD_REQUEST),TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS), NOT_FOUND(HttpStatus.NOT_FOUND),INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
        METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED),ERR_ACCESS_DENIED(HttpStatus.FORBIDDEN),UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
        SERVICE_NOT_AVAILABLE(HttpStatus.SERVICE_UNAVAILABLE), REMOTE_SERVICE_ERROR(HttpStatus.FAILED_DEPENDENCY);

        ResponseStatusEnum(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }

        private HttpStatus httpStatus= HttpStatus.OK;

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }
    }

    public OperationResponse(ResponseStatusEnum status){
        this(status,status.name());
    }

    private ResponseStatusEnum status= ResponseStatusEnum.SUCCESS;
    private String message;

    public ResponseEntity<OperationResponse> makeResponseEntity() {
        return new ResponseEntity<OperationResponse>(this, status.getHttpStatus());
    }

}
