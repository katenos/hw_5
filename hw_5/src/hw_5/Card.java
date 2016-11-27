/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import java.io.Serializable;

/**
 *
 * @author kate_
 */
public class Card implements Serializable{

    private int number;
    private int cash;
    private String pincode;
    private Client owner;

    public Card(int number, String pincode, Client owner) {
        this.number = number;
        this.pincode = pincode;
        this.cash = 0;
        this.owner = owner;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();
        strB.append("*");
        strB.append(this.number);
        strB.append("|");
        strB.append(this.cash);
        strB.append("|");
        strB.append(this.pincode);
        strB.append("|");
        strB.append(this.owner.getFIO());
        strB.append("|");
        return strB.toString();
    }

    public Client getOwner() {
        return this.owner;
    }

    public int getNumber() {
        return this.number;
    }

    public String getPincode() {
        return this.pincode;
    }

    public int getCash() {
        return this.cash;
    }

    public boolean checkPincode(String inputPin, int n) throws IncorrectlyPincode {
        if (this.pincode.equals(inputPin)) {
            return true;
        } else {
            throw new IncorrectlyPincode(n);
        }
    }

    public void takeMoney(int sum) throws InsufficientFunds {
        if (this.cash - sum >= 0 && sum % 100 == 0) {
            this.cash -= sum;
        } else {
            throw new InsufficientFunds();
        }
    }

    public void putMoney(int sum) throws InsufficientFunds {
        if (sum % 100 == 0) {
            this.cash += sum;
        } else {
            throw new InsufficientFunds();
        }
    }
}
