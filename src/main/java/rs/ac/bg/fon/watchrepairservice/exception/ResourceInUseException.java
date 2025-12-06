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
public class ResourceInUseException extends BusinessException{
    
    public ResourceInUseException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
    
}
