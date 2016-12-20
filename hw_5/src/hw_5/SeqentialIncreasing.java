/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import hw_5.Exception.InsufficientFundsException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kate_
 */
public class SeqentialIncreasing implements Runnable {

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Hw_5.putOrTake(false);
            }
            
        } catch (InsufficientFundsException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(SeqentialIncreasing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
