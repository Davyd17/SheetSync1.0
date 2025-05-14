package com.demo.sheetsync.exception;

public class GoogleSheetAccessException extends RuntimeException{

    public GoogleSheetAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
