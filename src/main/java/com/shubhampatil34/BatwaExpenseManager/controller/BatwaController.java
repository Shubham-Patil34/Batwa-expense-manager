package com.shubhampatil34.BatwaExpenseManager.controller;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.service.BatwaService;
import com.shubhampatil34.BatwaExpenseManager.service.ValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batwa")
public class BatwaController {
    @Autowired
    private BatwaService batwaService;
    @Autowired
    private ValidationErrorService validationErrorService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Batwa batwa, BindingResult result){
        ResponseEntity<?> errors = validationErrorService.validate(result);
        if (errors != null) {
            return errors;
        }
        Batwa batwaSaved = batwaService.createOrUpdate(batwa);
        return new ResponseEntity<>(batwaSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        batwaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
