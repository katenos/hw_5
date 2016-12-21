/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author kate_
 */
public class Client implements Serializable{

    private String FIO;
    private ArrayList<CardInterface> cards = new ArrayList();

    public Client(String FIO) {
        this.FIO = FIO;
    }

    public void addCard(CardInterface card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        //формат 
        //_новый клиент
        //|разделение полей 
        //*разделение карт
        StringBuilder strB = new StringBuilder();
        strB.append("_");
        strB.append(this.FIO);
        strB.append("|");
        for (CardInterface card : cards) {
            strB.append(card.toString());
        }
        return strB.toString();
    }

    public ArrayList<CardInterface> getCards() {
        return cards;
    }

    public String getFIO() {
        return FIO;
    }

    public void removeCard(int number) {
        for (CardInterface card : cards) {
            if (card.getNumber() == number) {
                cards.remove(card);
                break;
            }

        }
    }

}
