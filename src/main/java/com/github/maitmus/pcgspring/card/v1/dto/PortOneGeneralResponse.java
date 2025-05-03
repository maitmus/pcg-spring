package com.github.maitmus.pcgspring.card.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortOneGeneralResponse<T> {
    private Integer code;
    private String message;
    private T response;
}
