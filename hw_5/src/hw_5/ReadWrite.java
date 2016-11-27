/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw_5;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author kate_
 */
public class ReadWrite {

    private ByteArrayOutputStream outputByte;
    private ByteArrayInputStream inputByte;
    private StringWriter outputSymbol;
    private StringReader inputSymbol;

    public void readFromSymbolToConsol() {
        StringBuilder strB = new StringBuilder();
        if (outputSymbol != null) {
            inputSymbol = new StringReader(outputSymbol.toString());
            StreamTokenizer tokenizer = new StreamTokenizer(inputSymbol);
            tokenizer.ordinaryChar(':');
            try {
                while (tokenizer.nextToken()
                        != StreamTokenizer.TT_EOF) {
                    String s;
                    switch (tokenizer.ttype) {
                        case StreamTokenizer.TT_EOL:
                            s = new String("EOL");
                            break;
                        case StreamTokenizer.TT_NUMBER:
                            s = (int)tokenizer.nval+"";
                            break;
                        case StreamTokenizer.TT_WORD:
                            s = tokenizer.sval;
                            break;
                        default:
                            s = String.valueOf((char) tokenizer.ttype);
                    }
                    strB.append(s);
                    strB.append(" ");

                }
                System.out.println(strB.toString());
            } catch (IOException e) {
                System.err.println(
                        "st.nextToken() unsuccessful");
            }
        } else {
            System.out.println("Сначала запишите что-то в поток");
        }
    }

    public void writeFromConsolSymbol(String str) {
        outputSymbol = new StringWriter();
        try {
            outputSymbol.write(str);
            System.out.println("Записано!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void writeFromConsolByte(String str) {
        outputByte = new ByteArrayOutputStream();
        try {
            outputByte.write(str.getBytes());
            System.out.println("Записано!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void readFromByteToConsol() {
        if (outputByte != null) {
            inputByte = new ByteArrayInputStream(outputByte.toByteArray());
            int b;
            while ((b = inputByte.read()) != -1) {
                System.out.print((char) b);
            }
        } else {
            System.out.println("Сначала запишите что-то в поток");
        }
    }

    public void readUsingFileInputStream(Terminal t) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        StringBuilder fio = new StringBuilder("Cp1251");
        InputStream inpS = new FileInputStream("fileStream.txt");
        BufferedReader bufRead = new BufferedReader(new InputStreamReader(inpS, "Cp1251"));
        int i = -1;
        while ((i = bufRead.read()) != -1) {
            if ((char) i == '_') {
                fio.setLength(0);
                i = bufRead.read();
                do {
                    fio.append((char) i);
                    i = bufRead.read();
                } while (i != '|');
                i = bufRead.read();
                t.operationsClient("Создать", fio.toString());
            }
            if ((char) i == '*') {
                int number = Integer.parseInt(readForFieldCard(bufRead).toString());
                int cash = Integer.parseInt(readForFieldCard(bufRead).toString());
                String pincode = readForFieldCard(bufRead).toString();
                t.operationsCard("Создать", number, pincode, t.getClients().get(t.getClients().size() - 1));
                t.transactions(t.getCards().get(t.getCards().size() - 1), "Пополнить", cash);
            }
        }
    }

    private StringBuilder readForFieldCard(BufferedReader bufRead) throws IOException {
        StringBuilder strB = new StringBuilder("Cp1251");
        strB.setLength(0);
        int i = bufRead.read();
        do {
            strB.append((char) i);
            i = bufRead.read();
        } while (i != '|');
        return strB;
    }

    public void writeUsingOutputStream(Terminal t) {
        OutputStream os = null;
        StringBuilder strB = new StringBuilder();
        for (Client client : t.getClients()) {
            strB.append(client.toString());
        }
        try {
            os = new FileOutputStream(new File("fileStream.txt"));
            os.write(strB.toString().getBytes("Cp1251"), 0, strB.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
