/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Marko
 */
public class BusinessException extends RuntimeException{
    //This is one personal exception that extends RuntimeException,
    //uses message from RuntimeException and adds httpStatus!
    private final HttpStatus httpStatus;

    public BusinessException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

