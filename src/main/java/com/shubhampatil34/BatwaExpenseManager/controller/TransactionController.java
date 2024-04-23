package com.shubhampatil34.BatwaExpenseManager.controller;

import com.shubhampatil34.BatwaExpenseManager.dto.TransactionDTO;
import com.shubhampatil34.BatwaExpenseManager.exception.BatwaException;
import com.shubhampatil34.BatwaExpenseManager.service.TransactionService;
import com.shubhampatil34.BatwaExpenseManager.service.ValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lenden")
@CrossOrigin
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ValidationErrorService validationErrorService;

    @GetMapping("/{batwaId}")
    public ResponseEntity<List<TransactionDTO>> getAll(@PathVariable Long batwaId){
        return new ResponseEntity<>(transactionService.getAll(batwaId), HttpStatus.OK);
    }

    @GetMapping("/{batwaId}/{transactionId}")
    public ResponseEntity<TransactionDTO> getById(@PathVariable Long batwaId, @PathVariable Long transactionId){
        return new ResponseEntity<>(transactionService.getById(batwaId, transactionId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TransactionDTO transactionDTO, BindingResult result){
        System.out.println(transactionDTO);
        ResponseEntity<?> errors = validationErrorService.validate(result);

        if (errors != null) {
            return errors;
        }
        TransactionDTO transactionSaved = transactionService.createOrUpdate(transactionDTO);
        return new ResponseEntity<>(transactionSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<?> update(@PathVariable Long transactionId, @Valid @RequestBody TransactionDTO transactionDTO, BindingResult result){
        ResponseEntity<?> errors = validationErrorService.validate(result);
        if (errors != null) {
            return errors;
        }
        if(transactionService.isExists(transactionId)) {
            transactionDTO.setId(transactionId);
            TransactionDTO transactionSaved = transactionService.createOrUpdate(transactionDTO);
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
