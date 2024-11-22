package com.trree.rattattouille.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AuthGlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("CustomException: {}", e.getMessage());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage());
        return ErrorResponse.toResponseEntity(
                ErrorCode.INVALID_INPUT_VALUE,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException: {}", e.getMessage());
        return ErrorResponse.toResponseEntity(ErrorCode.INVALID_TYPE_VALUE);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage());
        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ErrorResponse.toResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }
} 