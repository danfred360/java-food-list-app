/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psu.edu;

/**
 *
 * @author Kevin
 */
public class DataAccessException extends Exception {
    
    private Exception exception;
    
    public DataAccessException(String message){
        super(message);
    }
    
    public DataAccessException(Exception exception){
        exception = this.exception;
    }
    
    public void printStackTrace(){
        exception.printStackTrace();
    }
}
