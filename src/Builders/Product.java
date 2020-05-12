/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

/**
 *
 * @author davi
 */
public class Product {
    
    String name;
    String desc;
    String icon;
    String date;
    Double price;
    Client client;
    int categorie;
    int prodId;
    int regId;
    int kitchen;
    
    public Product(String name, String desc, Double price, String icon, int categorie, int prodId, int kitchen) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.icon = icon;
        this.categorie = categorie;
        this.prodId = prodId;
        this.kitchen = kitchen;
    }
    
    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }
    
    public Product(String name, Double price, String date, int regId, Client client) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.regId = regId;
        this.client = client;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public int getCategorie() {
        return categorie;
    }
    
    public int getProdId(){
        return prodId;
    }
    
    public int getRegId() {
        return regId;
    }
    
    public int getKitchen() {
        return kitchen;
    }
    
    public String getDate() {
        return date;
    }
    
    public Client getClient() {
        return client;
    }
    
    public void setPrice(double newprice) {
        price = newprice;
    }
}
