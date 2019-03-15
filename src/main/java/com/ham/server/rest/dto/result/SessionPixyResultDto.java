package com.ham.server.rest.dto.result;

import com.ham.server.persistence.entity.SessionPixy;
import lombok.Data;

@Data
public class SessionPixyResultDto {

    private Long id;
    private String sessionId;
    /* isClient false means that session sent from server and true means it sent from client*/
    private Boolean isClient;

    public SessionPixyResultDto() {
    }

    public SessionPixyResultDto(Long id, String sessionId, Boolean isClient) {
        this.id = id;
        this.sessionId = sessionId;
        this.isClient = isClient;
    }

    public SessionPixyResultDto(SessionPixy sessionPixy) {
        this(sessionPixy.getId(), sessionPixy.getSessionId(), sessionPixy.getType().equals(SessionPixy.TYPE_CLIENT));
    }
}
