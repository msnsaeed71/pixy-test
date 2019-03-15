package com.ham.server.rest.error;

import com.ham.server.rest.dto.response.OperationResponse;
import com.ham.server.rest.dto.response.PixyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
/*error handler*/
public class ExceptionTranslator {

    /*if PixyException occurred it customized this error json response*/
    @ExceptionHandler(PixyException.class)
    public ResponseEntity processPixyException(PixyException ex) throws Exception {
        ex.printStackTrace();
        return new ErrorResponse(ex).makeResponseEntity();
    }

    /*if MethodArgumentNotValidException occurred it customized this error json response*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private ErrorResponse processFieldErrors(List<FieldError> fieldErrors) {
        ErrorFieldResponse errorFieldResponse = new ErrorFieldResponse();
        errorFieldResponse.setStatus(OperationResponse.ResponseStatusEnum.ERROR);
        errorFieldResponse.setFieldErrors(new ArrayList<>());
        errorFieldResponse.setMessage("حطای اعتبار سنجی فیلد ها");
        for (FieldError fieldError : fieldErrors) {
            errorFieldResponse.getFieldErrors().add(new FieldErrorDTO(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode()));
        }
        return errorFieldResponse;
    }

    /*if Exception occurred it customized this error json response*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity processRuntimeException(Exception ex) throws Exception {
        ex.printStackTrace();
        return new ErrorResponse(OperationResponse.ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage()).makeResponseEntity();
    }
}
