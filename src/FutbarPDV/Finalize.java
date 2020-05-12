/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FutbarPDV;

import Builders.Client;
import Builders.Product;
import View.PDV;
import java.util.Random;

/**
 *
 * @author davi
 */
public class Finalize {
    
    private final Util util;
    
    public Finalize(PDV pdv, Util util, double valuePaid) {
        this.util = util;
        
        Impressora printer = new Impressora(util);
        printer.printCupom(pdv.cartProducts, valuePaid);
        printer.partialCut();
        
        int count = 0;
        for(Product prod : pdv.cartProducts) {
            if(prod.getRegId() == 0) {
                if(valuePaid != 0) {
                    util.addItemRegistro(prod, "", "Dinheiro", 1);
                } else {
                    util.addItemRegistro(prod, "", "Cartão", 1);
                }
                if(prod.getKitchen() != 0) {
                    int senha = gerarSenha();
                    util.addItemCozinha(prod.getKitchen(), prod.getName(), 0, senha);
                    printer.printItem(prod, senha);
                } else {
                    printer.printItem(prod);
                }
                if(count < (pdv.cartProducts.size() - 1)) {
                    printer.partialCut();
                }
            } else {
                if(valuePaid != 0) {
                    util.setItemPaid(prod.getRegId(), "Dinheiro");
                } else {
                    util.setItemPaid(prod.getRegId(), "Cartão");
                }
            }
            count++;
        }
        printer.totalCut();
        printer.closeConn();
        pdv.clearCart();
    }
    
    public Finalize(PDV pdv, Util util, Client client) {
        this.util = util;
        
        Impressora printer = new Impressora(util);
        printer.printCupom(pdv.cartProducts, client);
        printer.partialCut();
        
        int count = 0;
        for(Product prod : pdv.cartProducts) {
            if(client.getBalance() >= prod.getPrice()) {
                client.setBalance(client.getBalance() - prod.getPrice());
                util.addItemRegistro(prod, client.getName(), "Saldo", 1);
            } else {
                util.addItemRegistro(prod, client.getName(), "", 0);
            }
            if(prod.getKitchen() != 0) {
                int senha = gerarSenha();
                util.addItemCozinha(prod.getKitchen(), prod.getName(), 0, senha);
                printer.printItem(prod, senha);
            } else {
                printer.printItem(prod);
            }
            if(count < (pdv.cartProducts.size() - 1)) {
                printer.partialCut();
            }
            count++;
        }
        printer.totalCut();
        printer.closeConn();
        pdv.clearCart();
    }
    
    public final int gerarSenha() {
        Random rand = new Random();
        int senha = rand.nextInt(999) + 1;
        
        while(util.cozinhaContainsSenha(senha)) {
            senha = rand.nextInt(999) + 1;
        }
        return senha;
    }
}
