package com.shubhampatil34.BatwaExpenseManager.controller;

import com.shubhampatil34.BatwaExpenseManager.entity.Batwa;
import com.shubhampatil34.BatwaExpenseManager.entity.Transaction;
import com.shubhampatil34.BatwaExpenseManager.exception.BatwaException;
import com.shubhampatil34.BatwaExpenseManager.service.BatwaService;
import com.shubhampatil34.BatwaExpenseManager.service.TransactionService;
import com.shubhampatil34.BatwaExpenseManager.service.ValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lenden")
@CrossOrigin
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ValidationErrorService validationErrorService;

    @GetMapping("/{batwaId}")
    public ResponseEntity<?> getAll(@PathVariable Long batwaId){
        return new ResponseEntity<>(transactionService.getAll(batwaId), HttpStatus.OK);
    }

    @GetMapping("/{batwaId}/{transactionId}")
    public ResponseEntity<?> getById(@PathVariable Long batwaId, @PathVariable Long transactionId){
        return new ResponseEntity<>(transactionService.getById(batwaId, transactionId), HttpStatus.OK);
    }

    @PostMapping("/{batwaId}")
    public ResponseEntity<?> create(@Valid @RequestBody Transaction transaction, @PathVariable Long batwaId,  BindingResult result){
        ResponseEntity<?> errors = validationErrorService.validate(result);
        if (errors != null) {
            return errors;
        }
        Transaction transactionSaved = transactionService.createOrUpdate(batwaId, transaction);
        return new ResponseEntity<>(transactionSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{batwaId}/{transactionId}")
    public ResponseEntity<?> update(@PathVariable Long batwaId, @PathVariable Long transactionId, @Valid @RequestBody Transaction transaction, BindingResult result){
        ResponseEntity<?> errors = validationErrorService.validate(result);
        if (errors != null) {
            return errors;
        }
        if(transactionService.isExists(transactionId)) {
            transaction.setId(transactionId);
            Transaction transactionSaved = transactionService.createOrUpdate(batwaId, transaction);
            return new ResponseEntity<>(transactionSaved, HttpStatus.OK);
        }
        throw new BatwaException("Transaction doesn't exists for id: " + transactionId);
    }

    @DeleteMapping("/{batwaId}/{transactionId}")
    public ResponseEntity<?> delete(@PathVariable Long batwaId, @PathVariable Long transactionId){
        transactionService.delete(batwaId, transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
