package com.shubhampatil34.BatwaExpenseManager.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationErrorService {
    public ResponseEntity<?> validate(BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errorsMap = new HashMap<>();
            for(FieldError fieldError : result.getFieldErrors()){
                errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
