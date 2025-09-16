package com.demo.sheetsync.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class SpreadsheetWatchException extends RuntimeException{
    public SpreadsheetWatchException(String message, Throwable cause){
        super(message, cause);
    }
}
