/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomationsystem.util.exception;

/**
 *
 * @author Acer
 */
public class AttendanceException extends Exception{
    
    private ErrorType type;
    
    public enum ErrorType { 
        USER_NOT_FOUND, NO_DATA_ACCESS, NO_INTERNET_CONNECTION
    }
    
    public AttendanceException(ErrorType type, String message)
    {
        super(message);
        this.type = type;
    }
    
    public AttendanceException(ErrorType type, String message, Exception ex)
    {
        super(message, ex);
        this.type = type;
    }
    
    public ErrorType getErrorType()
    {
        return type;
    }
    
}
