package com.github.maitmus.pcgspring.excpetion;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
