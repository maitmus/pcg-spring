package com.github.maitmus.pcgspring.excpetion;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
