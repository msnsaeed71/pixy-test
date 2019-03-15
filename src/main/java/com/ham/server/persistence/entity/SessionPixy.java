package com.ham.server.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/* Table for save all session id */
@Data
@Entity
@Table(name = "session_pixy")
public class SessionPixy {

    public static final String TYPE_CLIENT ="c";
    public static final String TYPE_SERVER ="s";

	@Id
	@NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="session_pixy_id_seq")
    @SequenceGenerator(name="session_pixy_id_seq", sequenceName="session_pixy_id_seq", allocationSize=1)
	@Column(name = "id")
	private Long id;

	@Column(name = "session_id")
	private String sessionId;

	@Column(name = "type")
	private String type;

	/*save create date for track*/
	@Column(name = "created_date")
	private Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now());

    public SessionPixy() {
    }

    public SessionPixy(String sessionId, String type) {
        this.sessionId = sessionId;
        this.type = type;
    }
}
