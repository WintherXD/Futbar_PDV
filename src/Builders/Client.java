/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import FutbarPDV.Util;

/**
 *
 * @author davi
 */
public class Client {
    
    String name;
    int id;
    int code;
    double balance;
    Util util;
    
    public Client(int id, String name, int code, double balance) {
        this.name = name;
        this.id = id;
        this.code = code;
        this.balance = balance;
        util = new Util();
    }
    
    public String getName() {
        return name;
    }
    
    public int getId(){
        return id;
    }
    
    public int getCode() {
        return code;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double newbalance) {
        util.setClientBalance(this, newbalance);
        balance = newbalance;
    }
}
