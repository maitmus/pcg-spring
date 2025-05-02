package com.github.maitmus.pcgspring.common.constant;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS("Authorization"),
    REFRESH("refreshToken");

    private final String value;

    TokenType(String value) {
        this.value = value;
    }
}
