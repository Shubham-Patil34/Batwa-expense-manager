package com.shubhampatil34.BatwaExpenseManager.controller;

import com.shubhampatil34.BatwaExpenseManager.dto.BatwaDTO;
import com.shubhampatil34.BatwaExpenseManager.exception.BatwaException;
import com.shubhampatil34.BatwaExpenseManager.service.BatwaService;
import com.shubhampatil34.BatwaExpenseManager.service.ValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/batwa")
@CrossOrigin
public class BatwaController {
    @Autowired
    private BatwaService batwaService;
    @Autowired
    private ValidationErrorService validationErrorService;

    @GetMapping
    public ResponseEntity<List<BatwaDTO>> getAll(){
        return new ResponseEntity<>(batwaService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatwaDTO> getById(@PathVariable Long id){
        return new ResponseEntity<>(batwaService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BatwaDTO batwaDTO, BindingResult result){
        ResponseEntity<?> errors = validationErrorService.validate(result);
        if (errors != null) {
            return errors;
        }
        BatwaDTO batwaSaved = batwaService.createOrUpdate(batwaDTO);
        return new ResponseEntity<>(batwaSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BatwaDTO batwaDTO, BindingResult result){
        ResponseEntity<?> errors = validationErrorService.validate(result);
        if (errors != null) {
            return errors;
        }
        if(batwaService.isExists(id)) {
            batwaDTO.setId(id);
            BatwaDTO batwaSaved = batwaService.createOrUpdate(batwaDTO);
            return new ResponseEntity<>(batwaSaved, HttpStatus.OK);
        }
        throw new BatwaException("Batwa doesn't exists for id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        batwaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
