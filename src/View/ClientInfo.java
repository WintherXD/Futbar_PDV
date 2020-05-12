/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Builders.Client;
import Builders.Product;
import View.CustomSwing.CustomLabel;
import FutbarPDV.Listener;
import FutbarPDV.Util;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author davi
 */
public class ClientInfo { 
    
    private final Listener listener;
    private final Util util;
    private final PDV pdv;
    
    public final JFrame frame;
    private final JPanel panel;
    
    public DefaultTableModel listItems;
    public JTable listItemsTable;
    
    public Map<Integer, JPanel> buttons = new HashMap<>();
    public Map<Integer, Product> markProducts;
    public final Client client;
    
    public ClientInfo(Listener listener, PDV pdv, Client client) {
        frame = new JFrame();
        panel = new JPanel();
        util = new Util();
        this.listener = listener;
        this.pdv = pdv;
        this.client = client;
        markProducts = util.getClientProductsMark(client);
        prepareProducts();
        preparePanel();
        prepareFrame();
    }
    
    private void prepareProducts() {
        for(Product prod : pdv.cartProducts) {
            if(markProducts.containsKey(prod.getRegId())) {
                markProducts.remove(prod.getRegId());
            }
        }
    }
    
    private void preparePanel() {
        GridBagLayout layout = new GridBagLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension((int) (screenSize.getWidth()*0.8), (int) (screenSize.getHeight()*0.8)));
        addComponents();
    }
    
    private void addComponents() {
        GridBagConstraints cons = new GridBagConstraints();
        JLabel name = new CustomLabel(client.getName(), 60, true);        
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(5, 5, 5, 5);
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 1;
        cons.weighty = 0.2;
        panel.add(name, cons);
        cons.gridx = 0;
        cons.gridy = 1;
        cons.weightx = 1;
        cons.weighty = 0.6;
        panel.add(listItemsPanel(), cons);
        cons.gridx = 0;
        cons.gridy = 2;
        cons.weightx = 1;
        cons.weighty = 0.2;
        panel.add(infoPanel(), cons);
    }
    
    private JPanel listItemsPanel() {
        GridBagConstraints cons = new GridBagConstraints();
        Font fontHeader = new Font("Tahoma", Font.BOLD, 28);
        JPanel listPanel = new JPanel(new GridBagLayout());

        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(0, 5, 0, 5);
        cons.weightx = 1;
        cons.weighty = 1;
        
        listItems = new DefaultTableModel();
        listItems.addColumn("Data:");
        listItems.addColumn("Produto:");
        listItems.addColumn("Preço:");

        for(Product prod : markProducts.values()) {
            String splitDate[] = prod.getDate().split(" ")[0].split("-");
            String splitHour[] = prod.getDate().split(" ")[1].split(":");
            String date = splitDate[2] + "/" + splitDate[1] + " - " + splitHour[0] + ":" + splitHour[1];
            listItems.addRow(new Object[]{date, prod.getName(), "R$ " + String.format("%.2f", prod.getPrice())});
        }
        
        listItemsTable = new JTable(listItems);
        listItemsTable.setFont(fontHeader);
        listItemsTable.setRowHeight(60);
        listItemsTable.setDefaultEditor(Object.class, null);
        listItemsTable.addMouseListener(listener);
        listItemsTable.addKeyListener(listener);
        
        JScrollPane jscroll = new JScrollPane(listItemsTable);
        listPanel.add(jscroll, cons);

        return listPanel;
    }
    
    private JPanel infoPanel() {
        double balance = client.getBalance();
        double totalMark = util.getClientTotalMark(client);
        double must = 0;
        if(balance < totalMark) {
            must = totalMark - balance;
        }
        
        GridBagConstraints cons = new GridBagConstraints();        
        JPanel info = new JPanel(new GridBagLayout());
        //JPanel saldo = addButton("<html><center>Saldo:<br>R$ " + balance + "</html>", 40, "#6FFF90");
        JPanel total = addButton("<html><center>Total:<br>R$ " + totalMark + "</html>", 50, "#FFA2A2");
        //JPanel pagartudo = addButton("<html><center>Selecionar<br> Tudo</html>", 40, "#FFA2A2");
        //JPanel deve = addButton("<html><center>Deve:<br>R$ " + must + "</html>", 40, "#FFA2A2");
        
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(15,15,15,15);
        cons.weightx = 1;
        cons.weighty = 1;   
        cons.gridx = 0;
        cons.gridy = 0;
        info.add(total, cons);
        //info.add(saldo, cons);
        cons.gridx = 1;
        //info.add(pagartudo, cons);
        //cons.gridx = 2;
        //info.add(deve, cons);

        buttons.put(0, total);
        total.addMouseListener(listener);
        
        return info;
    }
    
    private JPanel addButton(String text, int textSize, String color) {
        JPanel btn = new JPanel(new GridBagLayout());
        JLabel btnName = new CustomLabel(text, textSize, true);

        btn.setBackground(Color.decode(color));
        btn.add(btnName);
        
        return btn;
    }
    
    public void clearProductList() {
        for(int i = listItems.getRowCount() - 1; i >= 0; i--) {
            listItems.removeRow(i);
        }
        markProducts.clear();
    }
    
    private void prepareFrame() {
        frame.setUndecorated(true);
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
