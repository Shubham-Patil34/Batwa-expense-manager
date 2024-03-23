package com.shubhampatil34.BatwaExpenseManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BatwaException extends RuntimeException{
    public BatwaException(String message){super(message);}
}
