package com.epam.amorozov.studycenter.exceptions;

import lombok.Data;

import java.util.*;

@Data
public class StudyCenterError {
    private int status;
    private List<String> messages;
    private Date timestamp;

    public StudyCenterError(int status, String messages) {
        this.status = status;
        this.messages = List.of(messages);
        this.timestamp = new Date();
    }

    public StudyCenterError(int status, String... messages) {
        this.status = status;
        this.messages = new ArrayList<>(Arrays.asList(messages));
        this.timestamp = new Date();
    }

    public StudyCenterError(int status, Collection<String> messages) {
        this.status = status;
        this.messages = new ArrayList<>(messages);
        this.timestamp = new Date();
    }
}