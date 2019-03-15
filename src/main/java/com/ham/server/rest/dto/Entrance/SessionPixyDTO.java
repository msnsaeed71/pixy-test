package com.ham.server.rest.dto.Entrance;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SessionPixyDTO {

    /*is the uuid come from front that can build in back-end. but I supposed it come from front-end */
    @NotNull
    private String sessionId;

    /*if session pixy from client is true else if is come from server is false or null*/
    private Boolean isClient;

    /*list of instructions,values of session*/
    private List<SessionKeyValueDTO> sessionKeyValueList;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<SessionKeyValueDTO> getSessionKeyValueList() {
        return sessionKeyValueList;
    }

    public void setSessionKeyValueList(List<SessionKeyValueDTO> sessionKeyValueList) {
        this.sessionKeyValueList = sessionKeyValueList;
    }
}
