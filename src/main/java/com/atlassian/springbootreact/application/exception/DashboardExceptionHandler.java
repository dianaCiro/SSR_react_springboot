package com.atlassian.springbootreact.application.exception;

import com.atlassian.springbootreact.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DashboardExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> manageNotFoundException(NotFoundException notFoundException){
        log.warn(notFoundException.getMessage());
        ResponseError response = new ResponseError(HttpStatus
                .NOT_FOUND.value(), notFoundException.getMessage());
        return new ResponseEntity<ResponseError>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> manageMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        log.warn(methodArgumentNotValidException.getMessage());
        ResponseError response = new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ResponseError> manageHttpMessageConversionException(HttpMessageConversionException httpMessageConversionException){
        log.warn(httpMessageConversionException.getMessage());

        String message = httpMessageConversionException.getMessage();
        if(message.contains("enum")) {
            message = message.substring(message.indexOf("from") + 4, message.indexOf(";"));
        } else {
            message = "The format date must be yyyy-MM-dd HH:mm. Example 2019-01-01 14:02";
        }
        ResponseError response = new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                message);

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseError> manageGeneralException(Throwable throwable) {
        log.error("Internal server error", throwable.getMessage());
        System.err.println(throwable.getMessage());
        ResponseError response = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
