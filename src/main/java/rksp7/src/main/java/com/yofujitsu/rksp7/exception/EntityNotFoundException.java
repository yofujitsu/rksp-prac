package com.yofujitsu.rksp7.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class EntityNotFoundException extends RuntimeException{

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException() {
        System.out.println("Entity not found");
    }
}
