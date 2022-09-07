package com.stdio.esm.controller;

import com.stdio.esm.dto.response.EsmResponse;
import com.stdio.esm.exception.EsmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EsmExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsmExceptionController.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        LOGGER.info("MethodArgumentNotValidException is happened");
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Map<String, Map> responseData = new HashMap<>();
        responseData.put("errorFields", errors);
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setCode(HttpStatus.BAD_REQUEST.value());
        esmResponse.setStatus(EsmResponse.ERROR);
        esmResponse.setMessage("Invalid input");
        esmResponse.setResponseData(responseData);
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        LOGGER.error(exception.getMessage(), exception);
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setCode(HttpStatus.BAD_REQUEST.value());
        esmResponse.setStatus(EsmResponse.ERROR);
        esmResponse.setMessage("Account is not existed");
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }

    @ExceptionHandler(value = {EsmException.class})
    public ResponseEntity<?> handleEsmException(EsmException exception) {
        LOGGER.error(exception.getMessage(), exception);
        EsmResponse esmResponse = new EsmResponse();
        esmResponse.setCode(HttpStatus.BAD_REQUEST.value());
        esmResponse.setStatus(EsmResponse.ERROR);
        esmResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }
}
