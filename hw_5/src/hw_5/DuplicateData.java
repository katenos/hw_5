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
public class DuplicateData extends Exception{
    public DuplicateData(String message){
        super("Дублирование данных! "+message);
    }
}
