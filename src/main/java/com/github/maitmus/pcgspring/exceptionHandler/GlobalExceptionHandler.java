package com.github.maitmus.pcgspring.exceptionHandler;

import com.github.maitmus.pcgspring.common.dto.CommonErrorResponse;
import com.github.maitmus.pcgspring.excpetion.BadRequestException;
import com.github.maitmus.pcgspring.excpetion.ForbiddenException;
import com.github.maitmus.pcgspring.excpetion.NotFoundException;
import com.github.maitmus.pcgspring.excpetion.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({BadRequestException.class})
    public CommonErrorResponse handleBadRequestException(BadRequestException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    public CommonErrorResponse handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request");
    }

    @ExceptionHandler({ForbiddenException.class})
    public CommonErrorResponse handleForbiddenException(ForbiddenException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public CommonErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Request");
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public CommonErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public CommonErrorResponse handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({UnauthorizedException.class})
    public CommonErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    public CommonErrorResponse handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new CommonErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
