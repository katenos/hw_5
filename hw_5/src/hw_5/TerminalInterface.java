/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

/**
 *
 * @author kate_
 */
public interface TerminalInterface {

    int checkInvoice(CardInterface card);

    void transactions(CardInterface card, String typeOperation,int sum) ;

    void operationsClient(String operationClient, String fio);

    void operationsCard(String operationCard, int number, String pincode, Client owner, boolean sync);
}
