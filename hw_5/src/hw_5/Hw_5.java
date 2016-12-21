/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import hw_5.Exception.IncorrectlyPincodeException;
import hw_5.Exception.BlockedAccountException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author kate_
 */
public class Hw_5 {

    public static Terminal t;
    public static CardInterface card;
    public static Client client;
    public static ArrayList<String> operationMoney;
    public static ArrayList<String> operationClientCard;
    public static int n = 3;
    public static ReadWrite rw = new ReadWrite();

    public static void main(String[] args) throws InterruptedException, BlockedAccountException, UnsupportedEncodingException, IOException, FileNotFoundException, ClassNotFoundException {
        System.out.println("Задание 1");
        prepare(false);
        Decreaser dec = new Decreaser();
        Increaser inc = new Increaser();
        inc.setPriority(Thread.MIN_PRIORITY);
        dec.setPriority(Thread.MAX_PRIORITY);
        inc.start();
        dec.start();
        dec.join();
        inc.join();
        System.out.println("Задание 2");
        Thread seqInc;
        Thread seqDec;
        prepare(false);
        seqInc = new Thread(new SeqentialIncreasing());
        seqDec = new Thread(new SeqentialDecreasing());
        seqInc.setPriority(Thread.MIN_PRIORITY);
        seqDec.setPriority(Thread.MAX_PRIORITY);
        seqInc.start();
        seqDec.start();
        seqDec.join();
        seqInc.join();
        System.out.println("Задание 3");
        prepare(true);
        Decreaser decSync = new Decreaser();
        Increaser incSync = new Increaser();
        incSync.setPriority(Thread.MIN_PRIORITY);
        decSync.setPriority(Thread.MAX_PRIORITY);
        incSync.start();
        decSync.start();
        System.out.println("Главный поток завершён...");
    }

    public static void start() throws InterruptedException, BlockedAccountException, IOException, FileNotFoundException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        card = null;
        boolean checkNum = false;
        while (!checkNum) {
            System.out.println("Введите номер вашей карты (номер карты, который есть в базе)");
            int number = sc.nextInt();
            for (CardInterface tmpCard : t.getCards()) {
                if (tmpCard.getNumber() == number) {
                    card = tmpCard;
                    checkNum = true;
                }
            }
        }
        //для входа нужно ввести номер карты 12345678 и пинкод 0000
        n = 3;
        inputePin(card);
        menu();
    }

    public static void menu() throws InterruptedException, BlockedAccountException, IOException, FileNotFoundException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Клиент: " + client.getFIO());
        System.out.println("Выберите операцию: ");
        System.out.println("1 - проверить средства на счете");
        System.out.println("2 - пополнить карту");
        System.out.println("3 - снять наличные");
        System.out.println("4 - создать клиента");
        System.out.println("5 - удалить клиента");
        System.out.println("6 - создать карту");
        System.out.println("7 - удалить карту");
        System.out.println("8 - вывести список клиентов");
        System.out.println("9 - вывести список карт");
        System.out.println("10 - вывести список карт клиента");
        System.out.println("11 - потоки ввода/вывода");
        System.out.println("12 - сериализация");
        System.out.println("13 - выбрать другую карту");
        System.out.println("");
        switch (sc.nextLine()) {
            case "1":
                System.out.println("Сумма: " + card.getCash());
                System.out.println("");
                menu();
                break;
            case "2":
                System.out.println("Введите сумму");
                int sum = sc.nextInt();

                t.transactions(card, operationMoney.get(1), sum);
                menu();
                break;
            case "3":
                System.out.println("Введите сумму");
                sum = sc.nextInt();

                t.transactions(card, operationMoney.get(0), sum);
                menu();
                break;
            case "4":
                System.out.println("Введите ФИО нового клиента(транслитом)");
                String fio = sc.nextLine();
                t.operationsClient(operationClientCard.get(0), fio);
                menu();
                break;
            case "5":
                System.out.println("Введите ФИО клиента, которого нужно удалить");
                fio = sc.nextLine();
                t.operationsClient(operationClientCard.get(1), fio);
                menu();
                break;
            case "6":
                System.out.println("Введите номер карты");
                int number = Integer.parseInt(sc.nextLine());
                System.out.println("Введите пинкод");
                String pin;
                pin = sc.nextLine();
                t.operationsCard(operationClientCard.get(0), number, pin, client, false);
                menu();
                break;
            case "7":
                System.out.println("Введите номер карты");
                number = sc.nextInt();
                System.out.println("Введите пинкод");
                pin = sc.nextLine();
                t.operationsCard(operationClientCard.get(1), number, pin, client, false);
                menu();
                break;
            case "8":
                System.out.println("Список клиентов: ");
                ArrayList<Client> arrayClients = t.getClients();
                for (int i = 0; i < arrayClients.size(); i++) {
                    System.out.println((i + 1) + ": " + arrayClients.get(i).getFIO() + " количество карт: " + arrayClients.get(i).getCards().size());
                }
                menu();
                break;
            case "9":
                System.out.println("Список карт: ");
                ArrayList<CardInterface> arrayCards = t.getCards();
                for (int i = 0; i < arrayCards.size(); i++) {
                    System.out.println((i + 1) + ": " + arrayCards.get(i).getNumber() + " владелец: " + arrayCards.get(i).getOwner().getFIO());
                }
                menu();
                break;
            case "10":
                System.out.println("Список карт текущего клиента: ");
                arrayCards = client.getCards();
                for (int i = 0; i < arrayCards.size(); i++) {
                    System.out.println((i + 1) + ": " + arrayCards.get(i).getNumber() + " сумма " + arrayCards.get(i).getCash());
                }
                menu();
                break;
            case "11":
                menuInputOutput();
                break;
            case "12":
                menuSerialize();
                break;
            case "13":
                start();
                break;
            default:
                menu();
        }
    }

    public static void menuSerialize() throws InterruptedException, BlockedAccountException, IOException, FileNotFoundException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите: ");
        System.out.println("1 - сериализовать объекты");
        System.out.println("2 - десериализовать объекты");
        switch (sc.nextLine()) {
            case "1":
                rw.serialData(t);
                menu();
                break;
            case "2":
                t = rw.getSerialData();
                menu();
                break;
        }
    }

    public static void menuInputOutput() throws InterruptedException, BlockedAccountException, UnsupportedEncodingException, IOException, FileNotFoundException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Чтение/запись списка клиентов и их карт");
        System.out.println("Выберите метод: ");
        System.out.println("1 - запись данных в байтовый поток");
        System.out.println("2 - чтение данных из байтового потока");
        System.out.println("3 - запись данных в символьный поток");
        System.out.println("4 - чтение данных из символьного потока");
        System.out.println("5 - запись в файл всех клиентов и карт");
        System.out.println("6 - чтение из файла");
        switch (sc.nextLine()) {
            case "1":
                menuWriteByteStream();
                break;
            case "2":
                rw.readFromByteToConsol();
                menu();
                break;
            case "3":
                menuWriteSymbol();
                break;
            case "4":
                rw.readFromSymbolToConsol();
                menu();
                break;
            case "5":
                rw.writeUsingOutputStream(t);
                menu();
                break;
            case "6":
                rw.readUsingFileInputStream(t);
                menu();
                break;
            default:
                menu();
        }
    }

    public static void menuWriteSymbol() throws InterruptedException, BlockedAccountException, IOException, FileNotFoundException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        StringBuilder strB = new StringBuilder();
        System.out.println("Выберите что будем записывать: ");
        System.out.println("1 - карту");
        System.out.println("2 - клиента");
        switch (sc.nextLine()) {
            case "1":
                rw.writeFromConsolSymbol(writeCard());
                menu();
                break;
            case "2":
                System.out.println("Введите ФИО");
                strB.append("FIO: ");
                strB.append(sc.nextLine());
                strB.append("\n");
                strB.append(writeCard());
                rw.writeFromConsolSymbol(strB.toString());
                menu();
                break;
            default:
                menu();
        }
    }

    public static void menuWriteByteStream() throws InterruptedException, BlockedAccountException, IOException, FileNotFoundException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        StringBuilder strB = new StringBuilder();
        System.out.println("Выберите что будем записывать: ");
        System.out.println("1 - карту");
        System.out.println("2 - клиента");
        switch (sc.nextLine()) {
            case "1":
                rw.writeFromConsolByte(writeCard());
                menu();
                break;
            case "2":
                System.out.println("Введите ФИО");
                strB.append("FIO: ");
                strB.append(sc.nextLine());
                strB.append("\n");
                strB.append(writeCard());
                rw.writeFromConsolByte(strB.toString());
                menu();
                break;
            default:
                menu();
        }
    }

    private static String writeCard() {
        Scanner sc = new Scanner(System.in);
        StringBuilder strB = new StringBuilder();
        System.out.println("Введите номер карты");
        strB.append("Number: ");
        strB.append(sc.nextLine());
        strB.append("\n");
        System.out.println("Введите пинкод");
        strB.append("Pin: ");
        strB.append(sc.nextLine());
        strB.append("\n");
        System.out.println("Введите владельца");
        strB.append("FIO: ");
        strB.append(sc.nextLine());
        return strB.toString();
    }

    public static void printInfo() {
        ArrayList<Client> arrayClients = t.getClients();
        ArrayList<CardInterface> arrayCards = t.getCards();
        for (int i = 0; i < arrayClients.size(); i++) {
            System.out.println(i + ": " + arrayClients.get(i).getFIO());
        }
        for (int i = 0; i < arrayCards.size(); i++) {
            System.out.println(i + ": " + arrayCards.get(i).getNumber());
        }
    }

//    public static void test() {
//        ArrayList<Client> arrayClients = new ArrayList<>();
//        ArrayList<CardInterface> arrayCards = new ArrayList<>();
//        //проверка счета
//        System.out.println("Сумма на счете: " + t.checkInvoice(card));
//        //снять
//        t.transactions(card, operationMoney.get(0), 600);
//        System.out.println("Сняли 600 рублей " + t.checkInvoice(card));
//        System.out.println("Попытались снять еще 600 денег не хватило");
//        t.transactions(card, operationMoney.get(0), 600);
//        System.out.println("Снимем не кратное 100");
//        t.transactions(card, operationMoney.get(0), 550);
//        //положить
//        t.transactions(card, operationMoney.get(1), 500);
//        System.out.println("Положили 500 " + t.checkInvoice(card));
//        //добавить клиента
//        t.operationsClient(operationClientCard.get(0), "Маркин Михаил Павлович");
//        System.out.println("Добавили клиента. Список клиентов:");
//        arrayClients = t.getClients();
//        for (int i = 0; i < arrayClients.size(); i++) {
//            System.out.println(i + ": " + arrayClients.get(i).getFIO());
//        }
//        System.out.println("Попробуем добавить такого же");
//        t.operationsClient(operationClientCard.get(0), "Маркин Михаил Павлович");
//        System.out.println("Удалим клиента");
//        t.operationsClient(operationClientCard.get(1), "Маркин Михаил Павлович");
//        arrayClients = t.getClients();
//        for (int i = 0; i < arrayClients.size(); i++) {
//            System.out.println(i + ": " + arrayClients.get(i).getFIO());
//        }
//        //добавим карту
//        t.operationsCard(operationClientCard.get(0), 11111111, "1234", client);
//        System.out.println("Добавили карту. Список карт:");
//        arrayCards = t.getCards();
//        for (int i = 0; i < arrayCards.size(); i++) {
//            System.out.println(i + ": " + arrayCards.get(i).getNumber());
//        }
//        System.out.println("Попробуем добавить такую же");
//        t.operationsCard(operationClientCard.get(0), 11111111, "1234", client);
//        System.out.println("Удалим карту");
//        t.operationsCard(operationClientCard.get(1), 11111111, "1234", client);
//        for (int i = 0; i < arrayCards.size(); i++) {
//            System.out.println(i + ": " + arrayCards.get(i).getNumber());
//        }
//    }
    public static void prepare(boolean sync) {
        operationMoney = new ArrayList<String>();
        operationMoney.add("Снять");
        operationMoney.add("Положить");
        operationClientCard = new ArrayList<>();
        operationClientCard.add("Создать");
        operationClientCard.add("Удалить");
        t = new Terminal();
        //создаем клиента и карту
        t.operationsClient(operationClientCard.get(0), "Nosov Pavel Petrovich");
        int newNumberCard = 12345678;
        client = t.getClients().get(0);
        t.operationsCard(operationClientCard.get(0), newNumberCard, "0000", client, sync);
        card = client.getCards().get(0);
        t.transactions(card, operationMoney.get(1), 1000);
    }

    public static boolean inputePin(CardInterface card) throws InterruptedException, BlockedAccountException {
        //пинкод
        n--;
        System.out.println("Введите пинкод");
        Scanner sc = new Scanner(System.in);
        try {
            return card.checkPincode(sc.nextLine(), n);
        } catch (IncorrectlyPincodeException e) {
            try {
                if (n > 0) {
                    System.out.println(e.getMessage());
                    inputePin(card);
                } else {
                    throw new BlockedAccountException();
                }

            } catch (BlockedAccountException ex) {
                System.out.println(ex.getMessage());
                Thread.sleep(3000);
                n = 3;
                inputePin(card);
            }
        }
        n = 3;
        return true;
    }

}
