package com.github.maitmus.pcgspring.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
    private HttpStatus status;
    private T data;

    public CommonResponse() {
        this.status = HttpStatus.OK;
        this.data = null;
    }

    public CommonResponse(T data) {
        this.status = HttpStatus.OK;
        this.data = data;
    }
}
