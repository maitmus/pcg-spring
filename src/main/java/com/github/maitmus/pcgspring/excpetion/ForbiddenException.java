package com.github.maitmus.pcgspring.excpetion;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
