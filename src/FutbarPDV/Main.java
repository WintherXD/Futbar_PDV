/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FutbarPDV;

import View.PDV;

/**
 *
 * @author davi
 */
public class Main {
    
    public static Util util;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        util = new Util();
        PDV pdv = new PDV(util);
    }
}
