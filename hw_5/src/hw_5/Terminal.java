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
public class Terminal implements TerminalInterface {

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

    @Override
    public void transactions(Card card, String typeOperation, int sum) {
        if (typeOperation.equals("Снять")) {
            try {
                card.takeMoney(sum);
                System.out.println("Успешно!");
            } catch (InsufficientFunds ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                card.putMoney(sum);
            } catch (InsufficientFunds ex) {
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
                        throw new DuplicateClient();
                    }
                }
                clients.add(new Client(fio));
                System.out.println("Клиент создан.");
            } catch (DuplicateClient ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            for (Client client : clients) {
                if (client.getFIO().equals(fio)) {
                    for (Card card : cards) {
                        if(card.getOwner().equals(client.getFIO())){
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
                        throw new DuplicateCard();
                    }
                }
                Card card = new Card(number, pincode, owner);
                cards.add(card);
                owner.addCard(card);
                System.out.println("Карта создана");
            } catch (DuplicateCard ex) {
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
