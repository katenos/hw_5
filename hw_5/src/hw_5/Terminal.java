/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import hw_5.Exception.DuplicateCardException;
import hw_5.Exception.DuplicateClientException;
import hw_5.Exception.InsufficientFundsException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kate_
 */
public class Terminal implements TerminalInterface, Serializable {

    private ArrayList<Client> clients = new ArrayList();
    private ArrayList<Card> cards = new ArrayList();

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public int checkInvoice(Card card) {
        return card.getCash();
    }

     public synchronized void transactionsSync(Card card,boolean take) throws InsufficientFundsException, InterruptedException {
        if (take) {
            card.takeMoney(200);
            System.out.println("Сумма на карте(снятие): " + card.getCash());
        } else {
            card.putMoney(1000);
            System.out.println("Сумма на карте(пополнение): " + card.getCash());
        }
        this.notify();
        this.wait(1000);
    }    

    @Override
    public void transactions(Card card, String typeOperation, int sum) {
        if (typeOperation.equals("Снять")) {
            try {
                card.takeMoney(sum);
                System.out.println("Успешно!");
            } catch (InsufficientFundsException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                card.putMoney(sum);
            } catch (InsufficientFundsException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void operationsClient(String operationClient, String fio) {
        if (operationClient.equals("Создать")) {
            try {
                for (Client client : clients) {
                    if (client.getFIO().equals(fio)) {
                        throw new DuplicateClientException();
                    }
                }
                clients.add(new Client(fio));
                System.out.println("Клиент создан.");
            } catch (DuplicateClientException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            for (Client client : clients) {
                if (client.getFIO().equals(fio)) {
                    for (Card card : cards) {
                        if (card.getOwner().equals(client.getFIO())) {
                            cards.remove(card);
                        }
                    }
                    clients.remove(client);
                    System.out.println("Клиент удален.");
                    break;
                }
            }
        }
    }

    @Override
    public void operationsCard(String operationCard, int number, String pincode, Client owner) {
        if (operationCard.equals("Создать")) {
            try {
                for (Card card : cards) {
                    if (card.getNumber() == number) {
                        throw new DuplicateCardException();
                    }
                }
                Card card = new Card(number, pincode, owner);
                cards.add(card);
                owner.addCard(card);
                System.out.println("Карта создана");
            } catch (DuplicateCardException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            for (Card card : cards) {
                if (card.getNumber() == number) {
                    cards.remove(card);
                    card.getOwner().removeCard(number);
                    System.out.println("Карта удалена");
                    break;
                }
            }

        }

    }

}
