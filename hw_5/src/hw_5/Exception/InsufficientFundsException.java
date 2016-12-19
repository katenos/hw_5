/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5.Exception;

/**
 *
 * @author kate_
 */
public class InsufficientFundsException extends Exception{
     public InsufficientFundsException(){     
        super("Некорректная сумма.");        
    }
}
