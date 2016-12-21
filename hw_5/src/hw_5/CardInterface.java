/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import hw_5.Exception.IncorrectlyPincodeException;
import hw_5.Exception.InsufficientFundsException;
import java.io.Serializable;

/**
 *
 * @author admin
 */
public interface CardInterface extends Serializable {

    boolean checkPincode(String inputPin, int n) throws IncorrectlyPincodeException;

    int getCash();

    int getNumber();

    Client getOwner();

    String getPincode();

    void putMoney(int sum) throws InsufficientFundsException;

    void takeMoney(int sum) throws InsufficientFundsException;

    @Override
    String toString();
    
}
