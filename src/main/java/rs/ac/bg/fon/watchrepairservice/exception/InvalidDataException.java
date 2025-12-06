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
public class InvalidDataException extends BusinessException{
    
    public InvalidDataException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
    
}
