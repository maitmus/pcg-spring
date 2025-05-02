package com.github.maitmus.pcgspring.excpetion;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
