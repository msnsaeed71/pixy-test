package com.ham.server.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/* Table for save all instructions of session_id */
@Data
@Entity
@Table(name = "session_pixy_instructions")
public class SessionPixyInstructions {


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_pixy_instructions_id_seq")
    @SequenceGenerator(name = "session_pixy_instructions_id_seq", sequenceName = "session_pixy_instructions_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "num_repeat")
    private Integer numRepeat;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "session_pixy_id")
    private SessionPixy sessionPixy;

    public SessionPixyInstructions() {
    }

    public SessionPixyInstructions(String instruction, Integer numRepeat, SessionPixy sessionPixy) {
        this.instruction = instruction;
        this.numRepeat = numRepeat;
        this.sessionPixy = sessionPixy;
    }
}
