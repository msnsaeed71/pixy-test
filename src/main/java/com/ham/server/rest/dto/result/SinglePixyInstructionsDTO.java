package com.ham.server.rest.dto.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*each uuid has server and client result that this json created for show them*/
@Data
public class SinglePixyInstructionsDTO {
    /*list of the number of repeat instructions of the session id (uuid type) from server */
    private List<SessionPixyInstructionRepeatDTO> pixyInstructionRepeatDTOSServer;
    /*list of the number of repeat instructions of the session id (uuid type) from client */
    private List<SessionPixyInstructionRepeatDTO> pixyInstructionRepeatDTOSClient;

    public SinglePixyInstructionsDTO() {
        pixyInstructionRepeatDTOSServer = new ArrayList<>();
        pixyInstructionRepeatDTOSClient = new ArrayList<>();
    }

    public SinglePixyInstructionsDTO(List<SessionPixyInstructionRepeatDTO> pixyInstructionRepeatDTOSServer, List<SessionPixyInstructionRepeatDTO> pixyInstructionRepeatDTOSClient) {
        this.pixyInstructionRepeatDTOSServer = pixyInstructionRepeatDTOSServer;
        this.pixyInstructionRepeatDTOSClient = pixyInstructionRepeatDTOSClient;
    }

    public void addToPixyInstructionRepeatDTOSServer(SessionPixyInstructionRepeatDTO sessionPixyInstructionRepeatDTO) {
        if (pixyInstructionRepeatDTOSServer == null)
            pixyInstructionRepeatDTOSServer = new ArrayList<>();
        pixyInstructionRepeatDTOSServer.add(sessionPixyInstructionRepeatDTO);
    }

    public void addToPixyInstructionRepeatDTOSClient(SessionPixyInstructionRepeatDTO sessionPixyInstructionRepeatDTO) {
        if (pixyInstructionRepeatDTOSServer == null)
            pixyInstructionRepeatDTOSClient = new ArrayList<>();
        pixyInstructionRepeatDTOSClient.add(sessionPixyInstructionRepeatDTO);
    }
}
