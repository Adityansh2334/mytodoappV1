package com.note.indiatodo.dto;

import org.springframework.stereotype.Component;

@Component
public class ModalConfig {
    private String checkFlag;

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }
}
