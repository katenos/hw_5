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

/**
 *
 * @author kate_
 */
public class Terminal implements TerminalInterface, Serializable {

    private ArrayList<Client> clients = new ArrayList();
    private ArrayList<CardInterface> cards = new ArrayList();

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<CardInterface> getCards() {
        return cards;
    }

    @Override
    public int checkInvoice(CardInterface card) {
        return card.getCash();
    }

    public synchronized void transactionsSync(CardInterface card,  String typeOperation, int sum) throws InsufficientFundsException, InterruptedException {
        transactions(card, typeOperation, sum);
        this.notify();
        this.wait(1000);
    }

    @Override
    public void transactions(CardInterface card, String typeOperation, int sum) {
        if (typeOperation.equals("Снять")) {
            try {
                card.takeMoney(sum);               
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
                    for (CardInterface card : cards) {
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
    public void operationsCard(String operationCard, int number, String pincode, Client owner, boolean sync) {
        if (operationCard.equals("Создать")) {
            try {
                for (CardInterface card : cards) {
                    if (card.getNumber() == number) {
                        throw new DuplicateCardException();
                    }
                }
                CardInterface card;
                if (sync) {
                    card = new SynchronizedAccount(new Card(number, pincode, owner));
                } else {
                    card = new Card(number, pincode, owner);
                }
                cards.add(card);
                owner.addCard(card);
                System.out.println("Карта создана");
            } catch (DuplicateCardException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            for (CardInterface card : cards) {
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
