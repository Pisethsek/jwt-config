package com.piseth.exception;

import com.piseth.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {

    //service handler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResponseStatusException.class)
    public BaseError<?> handlerServiceException(ResponseStatusException e){
        return BaseError.builder()
                .status(false)
                .code(e.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .message("Something Went Wrong Please Check...!")
                .errors(e.getReason())
                .build();
    }

    //validation handler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e){

        List<Map<String, String>> errors = new ArrayList<>();

        for(FieldError error : e.getFieldErrors()){
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("name", error.getField());
            errorDetail.put("message", error.getDefaultMessage());
            errors.add(errorDetail);
        }


        return BaseError.builder()
                .status(true)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message("Validation Is Error, Please Check Detail")
                .errors(errors)
                .build();
    }

}
