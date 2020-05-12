/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FutbarPDV;
 
import Builders.Client;
import Builders.Product;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Davi
 */
public class Impressora {
    
    PrintWriter ps;
    String formatDate;
    Util util;
    
    public Impressora(Util util) {
        try {
            this.util = util;
            FileOutputStream fos = new FileOutputStream("COM4");
            ps = new PrintWriter(fos);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        Date date = new Date();  
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatDate = df.format(date);
    }
    
    public void printItem(Product p) {
        ps.print("\n"+"**************************************************");
        ps.print("\n"+"                   " + doubleHeightWidth(bold("FUTBAR")) + "         ");
        ps.print("\n"+"**************************************************");
        if(p.getName().equalsIgnoreCase("Valor")) {
            ps.print("\n"+doubleHeightWidth(bold("Valor:")));
            ps.print("\n"+doubleHeightWidth("R$ " + String.format("%.2f", p.getPrice())));
        } else {
            ps.print("\n"+doubleHeightWidth(p.getName()));
        }
        if(p.getDesc() != null) {
            ps.print("\n"+doubleHeightWidth(p.getDesc()));
        }
        ps.print("\n                               "+formatDate);
        ps.print("\n"+"**************************************************");
    }
    
    public void printItem(Product p, int senha) {
        ps.print("\n"+"**************************************************");
        ps.print("\n"+"                   " + doubleHeightWidth(bold("FUTBAR")) + "         ");
        ps.print("\n"+"**************************************************");
        if(p.getName().equalsIgnoreCase("Valor")) {
            ps.print("\n"+doubleHeightWidth(bold("Valor:")));
            ps.print("\n"+doubleHeightWidth("R$ " + String.format("%.2f", p.getPrice())));
        } else {
            ps.print("\n"+doubleHeightWidth(p.getName()));
        }
        if(p.getDesc() != null) {
            ps.print("\n"+doubleHeightWidth(p.getDesc()));
        }
        ps.print("\n" + doubleHeightWidth(bold("               SENHA: " + senha)));
        ps.print("\n                               "+formatDate);
        ps.print("\n"+"**************************************************");
    }
    
    public void printCupom(List<Product> products, double paidValue) {
        double totalValue = 0;
        
        ps.print("\n"+"**************************************************");
        ps.print("\n"+"                   " + doubleHeightWidth(bold("FUTBAR")) + "         ");
        ps.print("\n"+"                " + formatDate + "               ");
        ps.print("\n"+"**************************************************");
        ps.print(bold("ID       Item       Desc       Cliente       Valor"));
        ps.print("\n"+"**************************************************");
        
        for(Product p : products) {
            totalValue += p.getPrice();
            
            String pId = "";
            if(p.getRegId() != 0) {
                pId += p.getRegId();
            } else {
                pId += p.getProdId();
            }
            String pName = p.getName();
            String pDesc = "-";
            if(p.getDesc() != null) {
                pDesc = p.getDesc();
            }
            String pClient = "-";
            if(p.getClient() != null) {
                pClient = p.getClient().getName();
            }
            String pPrice = "R$ " + String.format("%.2f", p.getPrice());
            
            String line = pId + pName + pDesc + pClient + pPrice;
            
            int space = (50 - line.length())/4;
            String itemsSpace = "";
            for(int i=0;i<space;i++) {
                itemsSpace += " ";
            }
            
            int totalSpaces = (line.length() + space*4);
            String endSpace = itemsSpace;
            if(totalSpaces < 50) {
                for(int i=totalSpaces;i<50;i++) {
                    endSpace += " ";
                }
            }
            
            ps.print("\n" + pId);
            ps.print(itemsSpace);
            ps.print(pName);
            ps.print(itemsSpace);
            ps.print(pDesc);
            ps.print(itemsSpace);
            ps.print(pClient);
            ps.print(endSpace);
            ps.print(pPrice);
        }
        
        ps.print("\n"+"**************************************************");
        
        //TOTAL
        String totalFormat = "R$ " + String.format("%.2f", totalValue);
        int totalSpaces = 25 - (7 + totalFormat.length());
        
        ps.print("\n"+doubleHeightWidth(bold("TOTAL: ")));
        for(int i=0;i<totalSpaces;i++) {
                ps.print(doubleHeightWidth(bold(" ")));
            }
        ps.print(doubleHeightWidth(bold(totalFormat)));
        
        //PAGAMENTO
        if(paidValue != 0) {
            String paidFormat = "R$ " + String.format("%.2f", paidValue);
            int paidSpaces = 25 - (10 + paidFormat.length());
            
            ps.print("\n"+doubleHeightWidth(bold("DINHEIRO: ")));
            for(int i=0;i<paidSpaces;i++) {
                    ps.print(doubleHeightWidth(bold(" ")));
                }
            ps.print(doubleHeightWidth(bold(paidFormat)));
            
            double troco = paidValue - totalValue;
            String trocoFormat = "R$ " + String.format("%.2f", troco);
            int trocoSpaces = 25 - (7 + trocoFormat.length());
            
            ps.print("\n"+doubleHeightWidth(bold("TROCO: ")));
            for(int i=0;i<trocoSpaces;i++) {
                    ps.print(doubleHeightWidth(bold(" ")));
                }
            ps.print(doubleHeightWidth(bold(trocoFormat)));
        } else {
            int cardSpaces = 25 - (8 + totalFormat.length());

            ps.print("\n"+doubleHeightWidth(bold("CARTAO: ")));
            for(int i=0;i<cardSpaces;i++) {
                    ps.print(doubleHeightWidth(bold(" ")));
                }
            ps.print(doubleHeightWidth(bold(totalFormat)));
            ps.print("\n"+doubleHeightWidth(bold("TROCO:            R$ 0,00")));
        }
    }
    
    public void printCupom(List<Product> products, Client client) {
        double totalValue = 0;
        
        ps.print("\n"+"**************************************************");
        ps.print("\n"+"                   " + doubleHeightWidth(bold("FUTBAR")) + "         ");
        ps.print("\n"+"                " + formatDate + "               ");
        ps.print("\n"+"**************************************************");
        ps.print(bold("ID       Item       Desc       Cliente       Valor"));
        ps.print("\n"+"**************************************************");
        
        for(Product p : products) {
            totalValue += p.getPrice();
            
            String pId = "";
            if(p.getRegId() != 0) {
                pId += p.getRegId();
            } else {
                pId += p.getProdId();
            }
            String pName = p.getName();
            String pDesc = "-";
            if(p.getDesc() != null) {
                pDesc = p.getDesc();
            }
            String pClient = "-";
            if(p.getClient() != null) {
                pClient = p.getClient().getName();
            }
            String pPrice = "R$ " + String.format("%.2f", p.getPrice());
            
            String line = pId + pName + pDesc + pClient + pPrice;
            
            int space = (50 - line.length())/4;
            String itemsSpace = "";
            for(int i=0;i<space;i++) {
                itemsSpace += " ";
            }
            
            int totalSpaces = (line.length() + space*4);
            String endSpace = itemsSpace;
            if(totalSpaces < 50) {
                for(int i=totalSpaces;i<50;i++) {
                    endSpace += " ";
                }
            }
            
            ps.print("\n" + pId);
            ps.print(itemsSpace);
            ps.print(pName);
            ps.print(itemsSpace);
            ps.print(pDesc);
            ps.print(itemsSpace);
            ps.print(pClient);
            ps.print(endSpace);
            ps.print(pPrice);
        }
        
        ps.print("\n"+"**************************************************");
        
        //TOTAL
        String totalFormat = "R$ " + String.format("%.2f", totalValue);
        int totalSpaces = 25 - (7 + totalFormat.length());
        
        ps.print("\n"+doubleHeightWidth(bold("TOTAL: ")));
        for(int i=0;i<totalSpaces;i++) {
                ps.print(doubleHeightWidth(bold(" ")));
            }
        ps.print(doubleHeightWidth(bold(totalFormat)));
        
        //PAGAMENTO
        int paidSpaces = 25 - (9 + client.getName().length());

        ps.print("\n"+doubleHeightWidth(bold("CLIENTE: ")));
        for(int i=0;i<paidSpaces;i++) {
                ps.print(doubleHeightWidth(bold(" ")));
            }
        ps.print(doubleHeightWidth(bold(client.getName())));
        
        //DEVE
        String mustFormat = "R$ " + String.format("%.2f", util.getClientTotalMark(client) + totalValue);
        int mustSpaces = 25 - (6 + mustFormat.length());

        ps.print("\n"+doubleHeightWidth(bold("DEVE: ")));
        for(int i=0;i<mustSpaces;i++) {
                ps.print(doubleHeightWidth(bold(" ")));
            }
        ps.print(doubleHeightWidth(bold(mustFormat)));
    }
    
    public void partialCut() {
        ps.print(""+(char)27+(char)109);
    }
    
    public void totalCut() {
        ps.print(""+(char)27+(char)119);
    }
    
    public void closeConn() {
        ps.close();
    }
    
    private String bold(String text) {
        String formatText = "" + (char)27 + (char)69 + text + (char)27 + (char)70;
        return formatText;
    }
    
    private String doubleHeight(String text) {
        String formatText = "" + (char)27 + (char)86 + text + (char)27 + (char)86;
        return formatText;
    }
    
    private String doubleWidth(String text) {
        String formatText = "" + (char)27 + (char)14 + text + (char)27 + (char)14;
        return formatText;
    }
    
    private String doubleHeightWidth(String text) {
        String formatText = "" + (char)27 + (char)14 + (char)27 + (char)86 + text + (char)27 + (char)53;
        return formatText;
    }
    
    private void skipLine() {
        ps.print(""+(char)13+(char)10);
    }
}
