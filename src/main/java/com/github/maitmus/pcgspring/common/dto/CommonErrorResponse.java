package com.github.maitmus.pcgspring.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonErrorResponse {
    private HttpStatus status;
    private String message;
}

