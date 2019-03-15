package com.ham.server.persistence.repository;

import com.ham.server.persistence.entity.SessionPixy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* Repository for SessionPixy entity */
public interface SessionPixyRepository extends JpaRepository<SessionPixy, Long> {
    /* find all SessionPixy where sessionId equals it  */
    List<SessionPixy> findAllBySessionId(String sessionId);
    SessionPixy findBySessionIdAndType(String sessionId, String type);
}
