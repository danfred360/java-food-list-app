/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psu.edu;

/**
 *
 * @author dpfrederick
 */
public class DataAccessException extends Exception {

   private Exception exception;
 
   // constructor with String argument
   public DataAccessException( String message )
   {
     super( message );
   }

   // constructor with Exception argument
   public DataAccessException( Exception exception )
   {
      exception = this.exception;
   }

   // printStackTrace of exception from constructor
   public void printStackTrace()
   {
      exception.printStackTrace();
   }
}