package com.ham.server.persistence.repository;

import com.ham.server.persistence.entity.SessionPixyInstructions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* Repository for SessionPixyInstructions entity */
public interface SessionPixyInstructionsRepository extends JpaRepository<SessionPixyInstructions, Long> {
    /* find all SessionPixyInstructions that SessionPixy  of it has this sessionID  */
    List<SessionPixyInstructions> findAllBySessionPixyNotNullAndSessionPixy_SessionId(String sessionId);
    /* find all SessionPixyInstructions that SessionPixy  of it equals with sessionID and type  */
    List<SessionPixyInstructions> findAllBySessionPixyNotNullAndSessionPixy_SessionIdAndSessionPixy_Type(String sessionId, String type);
}
