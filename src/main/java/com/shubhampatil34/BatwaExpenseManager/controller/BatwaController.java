package com.shubhampatil34.BatwaExpenseManager.controller;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.service.BatwaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/batwa")
public class BatwaController {
    @Autowired
    private BatwaService batwaService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Batwa batwa, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errorsMap = new HashMap<>();
            for(FieldError fieldError : result.getFieldErrors()){
                errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        Batwa batwaSaved = batwaService.createOrUpdate(batwa);
        return new ResponseEntity<>(batwaSaved, HttpStatus.CREATED);
    }
}
