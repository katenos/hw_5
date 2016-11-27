/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import java.util.ArrayList;

/**
 *
 * @author kate_
 */
public class Client {

    private String FIO;
    private ArrayList<Card> cards = new ArrayList();

    public Client(String FIO) {
        this.FIO = FIO;
    }

    public void addCard(Card card) {
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
        for (Card card : cards) {
            strB.append(card.toString());
        }
        return strB.toString();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getFIO() {
        return FIO;
    }

    public void removeCard(int number) {
        for (Card card : cards) {
            if (card.getNumber() == number) {
                cards.remove(card);
                break;
            }

        }
    }

}