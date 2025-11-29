/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.watchrepairservice.communication;

/**
 *
 * @author Marko
 */
public class ServiceResult<T> {
    private boolean success;
    private String message;
    private T data;

    public ServiceResult() {
    }

    public ServiceResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    public static <T> ServiceResult<T> successMessage(String message){
        return new ServiceResult<>(true, message, null);
    }
    
    public static <T> ServiceResult<T> successMessageData(String message, T data){
        return new ServiceResult<>(true, message, data);
    }
    
    public static <T> ServiceResult<T> errorMessage(String message){
        return new ServiceResult<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    
    
}
