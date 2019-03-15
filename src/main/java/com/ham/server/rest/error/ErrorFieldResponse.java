package com.ham.server.rest.error;

import lombok.Data;

import java.util.List;

/**
 * Created by Mahdi on 11/30/2017.
 */

/* unValid body fields*/
@Data
public class ErrorFieldResponse extends ErrorResponse {
    private List<FieldErrorDTO> fieldErrors;
}
