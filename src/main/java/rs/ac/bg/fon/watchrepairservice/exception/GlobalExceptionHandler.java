/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rs.ac.bg.fon.watchrepairservice.communication.ApiResponse;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Marko
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse> handleBusinessException(BusinessException ex){
        ApiResponse response = new ApiResponse(false, ex.getMessage(), null);
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }
    
    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<ApiResponse> handleIntengrityViolationEx(ResourceInUseException ex){
        ApiResponse response = new ApiResponse(false, ex.getMessage(), null);
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex){
        ApiResponse response = new ApiResponse(false, "An unexpected error occurred: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
