package nbo.springframework.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    ResponseEntity handleJPAViolationException(TransactionSystemException transactionSystemException){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){
        List errorsList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errorDetailsMap = new HashMap();
                    errorDetailsMap.put(fieldError.getField() ,fieldError.getDefaultMessage());
                    return errorDetailsMap;
                }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorsList);
    }
}
