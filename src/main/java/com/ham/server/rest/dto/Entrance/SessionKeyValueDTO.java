package com.ham.server.rest.dto.Entrance;

import lombok.Data;

@Data
public class SessionKeyValueDTO {
    /*the name of instruction of session */
    private String key;
    /*the value of instruction of session */
    private String value;

    public SessionKeyValueDTO() {
    }

    public SessionKeyValueDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
