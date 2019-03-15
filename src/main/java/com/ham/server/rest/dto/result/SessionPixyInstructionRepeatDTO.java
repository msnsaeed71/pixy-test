package com.ham.server.rest.dto.result;

import com.ham.server.persistence.entity.SessionPixyInstructions;
import lombok.Data;

/* result dto for single session's instruction*/
@Data
public class SessionPixyInstructionRepeatDTO {
    /*the name of key for each instruction of specified session*/
    private String instruction;
    /*number repeated of each instruction of specified session*/
    private Integer repeat;

    public SessionPixyInstructionRepeatDTO() {
    }

    public SessionPixyInstructionRepeatDTO(SessionPixyInstructions sessionPixyInstructions) {
        this.instruction = sessionPixyInstructions.getInstruction();
        this.repeat = sessionPixyInstructions.getNumRepeat();
    }
}
