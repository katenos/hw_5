/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import hw_5.Exception.IncorrectlyPincodeException;
import hw_5.Exception.InsufficientFundsException;

/**
 *
 * @author admin
 */
public class SynchronizedAccount implements CardInterface {

    private CardInterface card;

    public SynchronizedAccount(CardInterface card) {
        this.card = card;
    }

    @Override
    public synchronized boolean checkPincode(String inputPin, int n) throws IncorrectlyPincodeException {
        return card.checkPincode(inputPin, n);
    }

    @Override
    public synchronized int getCash() {
        return card.getCash();
    }

    @Override
    public synchronized int getNumber() {
        return card.getNumber();
    }

    @Override
    public synchronized Client getOwner() {
        return card.getOwner();
    }

    @Override
    public synchronized String getPincode() {
        return card.getPincode();
    }

    @Override
    public synchronized void putMoney(int sum) throws InsufficientFundsException {
        card.putMoney(sum);
    }

    @Override
    public synchronized void takeMoney(int sum) throws InsufficientFundsException {
        card.takeMoney(sum);
    }

    @Override
    public synchronized String toString() {
        return card.toString();
    }

}
