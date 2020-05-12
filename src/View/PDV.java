/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.CustomSwing.CustomLabel;
import Builders.Categorie;
import FutbarPDV.Listener;
import Builders.Product;
import FutbarPDV.Main;
import FutbarPDV.Util;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class PDV {

    private final Listener listener;
    private final Util util;
    
    public int selectedCategoryID = 1;
    private final int itemsPerLine = 4;
    
    public JFrame frame;
    public JPanel panel;
    public Map<Integer, JPanel> categoriesButtons = new HashMap<>();
    public Map<Integer, JPanel> itemsButtons = new HashMap<>();
    public Map<Integer, JPanel> menuButtons = new HashMap<>();
    public JTable listItemsTable;
    public DefaultTableModel listItems;
    
    public List<Product> cartProducts = new ArrayList<>();
    public JLabel total;
    public double totalValue;
    
    public PDV(Util util) {
        frame = new JFrame();
        panel = new JPanel();
        listener = new Listener(this, util);
        this.util = util;
        preparePanel();
        prepareFrame();
    }
    
    public PDV(Util util, Integer selectedCategoryID, List<Product> cartProducts) {
        this.selectedCategoryID = selectedCategoryID;
        this.cartProducts = cartProducts;
        frame = new JFrame();
        panel = new JPanel();
        this.util = util;
        listener = new Listener(this, util);
        preparePanel();
        prepareFrame();
    }
    
    private void preparePanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(screenSize);
        
        addComponents();
    }
    
    private void addComponents() {
        GridBagConstraints cons = new GridBagConstraints();
        
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(5,5,5,5);
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weighty = 0.15;
        cons.weightx = 0.70;
        panel.add(categoriesPanel(), cons);
        cons.gridy = 1;
        cons.weighty = 0.75;
        panel.add(itemsPanel(), cons);
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridheight = 2;
        cons.weightx = 0.30;
        panel.add(listItemsPanel(), cons);
        cons.gridx = 0;
        cons.gridy = 2;
        cons.gridheight = 1;
        cons.weightx = 0.70;
        cons.weighty = 0.10;
        panel.add(menuPanel(), cons);
        cons.gridx = 1;
        cons.gridy = 2;
        cons.weightx = 0.30;
        cons.weighty = 0.10;
        panel.add(finalizePanel(), cons);
    }
    
    private JPanel categoriesPanel() {
        JPanel buttons = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(15,15,15,15);
        cons.weightx = 1;
        cons.weighty = 1;        
        cons.gridy = 0;
        int i = 0;
        for(Categorie cat : util.getCategories()) {
            
            cons.gridx = i;
            JPanel item;
            if(cat.getId() == selectedCategoryID) {
                item = addButton(cat.getName(), 120/util.getCategories().size(), "#FF4444");
            } else {
                item = addButton(cat.getName(), 120/util.getCategories().size(), "#FFA2A2");
            }
            item.addMouseListener(listener);
            buttons.add(item, cons);
            categoriesButtons.put(cat.getId(), item);
            i++;
        }
        return buttons;
    }
    
    private JPanel itemsPanel() {
        JPanel items = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(10,10,10,10);
        cons.weightx = 1;
        cons.weighty = 1;

        int x = 0;
        int y = 0;
        for(int i=0; i<util.getProducts().size(); i++) {
            Product prod = util.getProducts().get(i);
            if(prod.getCategorie() == selectedCategoryID) {
                JPanel item = addItem(prod.getIcon(), prod.getName(), prod.getDesc(), "R$ " + String.format("%.2f", prod.getPrice()), "#6F85FF");
                item.addMouseListener(listener);
                cons.gridx = x;
                cons.gridy = (int) Math.ceil(y/itemsPerLine);
                itemsButtons.put(prod.getProdId(), item);
                items.add(item, cons);
                y++;
                if(x < itemsPerLine-1) {
                    x++;
                } else {
                    x = 0;
                }
            }
        }
        return items;
    }
    
    private JPanel listItemsPanel() {
        GridBagConstraints cons = new GridBagConstraints();
        Font fontHeader = new Font("Tahoma", Font.BOLD, 28);
        JPanel listPanel = new JPanel(new GridBagLayout());

        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(5,5,5,5);
        cons.weightx = 1;
        cons.weighty = 1;
        
        if(listItems == null) {
            listItems = new DefaultTableModel();
            listItems.addColumn("Produto:");
            listItems.addColumn("Preço:");
            
            cartProducts.forEach((prod) -> {
                listItems.addRow(new Object[]{prod.getName(), "R$ " + String.format("%.2f", prod.getPrice())});
            });
        }
        
        listItemsTable = new JTable(listItems);
        listItemsTable.setFont(fontHeader);
        listItemsTable.setRowHeight(60);
        listItemsTable.setDefaultEditor(Object.class, null);
        listItemsTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        listItemsTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        listItemsTable.addMouseListener(listener);
        
        JScrollPane jscroll = new JScrollPane(listItemsTable);
        jscroll.getVerticalScrollBar().addAdjustmentListener(listener);
        listPanel.add(jscroll, cons);

        return listPanel;
    }
    
    private JPanel menuPanel() {
        GridBagConstraints cons = new GridBagConstraints();        
        JPanel buttons = new JPanel(new GridBagLayout());
        JPanel insereValor = addButton("<html><center>Inserir<br>Valor</html>", 40, "#6FFF90");
        JPanel limpar = addButton("Limpar", 40, "#6FFF90");
        JPanel clientes = addButton("Clientes", 40, "#6FFF90");
        //JPanel cozinha = addButton("Cozinha", 40, "#6FFF90");
        
        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(15,15,15,15);
        cons.weightx = 1;
        cons.weighty = 1;   
        cons.gridx = 0;
        cons.gridy = 0;
        buttons.add(insereValor, cons);
        cons.gridx = 1;
        buttons.add(limpar, cons);
        cons.gridx = 2;
        buttons.add(clientes, cons);
        cons.gridx = 3;
        //buttons.add(cozinha, cons);

        menuButtons.put(0, insereValor);
        menuButtons.put(1, limpar);
        menuButtons.put(2, clientes);
        //menuButtons.put(3, cozinha);
        
        insereValor.addMouseListener(listener);
        clientes.addMouseListener(listener);
        limpar.addMouseListener(listener);
        
        return buttons;
    }
    
    private JPanel finalizePanel() {
        GridBagConstraints cons = new GridBagConstraints();
        JPanel finalize = new JPanel(new GridBagLayout());
        total = new CustomLabel("Total: R$ 0,00", 50, "#FF4444");
        JPanel checkout = addButton("Finalizar Compra", 40, "#AA7AFD");

        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(0,15,15,15);
        cons.weightx = 1;
        cons.weighty = 1;
        cons.gridx = 0;
        cons.gridy = 0;
        finalize.add(total, cons);
        cons.gridy = 1;
        finalize.add(checkout, cons);
        
        menuButtons.put(4, checkout);
        
        checkout.addMouseListener(listener);
        
        return finalize;
    }
    
    private JPanel addItem(String icon, String name, String description, String price, String color) {
        GridBagConstraints cons = new GridBagConstraints();
        JPanel item = new JPanel(new GridBagLayout());
        item.setBackground(Color.decode(color));
        JLabel icone = new CustomLabel(icon);
        JLabel nome = new CustomLabel(name, 30, true);
        JLabel desc = new CustomLabel(description, 24, false);
        JLabel preco = new CustomLabel(price, 32, true);

        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(0, 0, 0, 10);
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridheight = 2;
        item.add(icone, cons);
        cons.insets = new Insets(0, 0, 0, 0);
        cons.gridx = 1;
        cons.gridy = 0;
        cons.gridheight = 1;
        item.add(nome, cons);
        cons.gridy = 1;
        if(description != null) {
            item.add(desc, cons);
            cons.gridy = 2;
        }
        cons.insets = new Insets(5, 0, 0, 0);
        item.add(preco, cons);
        
        return item;
    }
    
    private JPanel addButton(String text, int textSize, String color) {
        JPanel btn = new JPanel(new GridBagLayout());
        JLabel btnName = new CustomLabel(text, textSize, true);

        btn.setBackground(Color.decode(color));
        btn.add(btnName);
        
        return btn;
    }
    
    private void prepareFrame() {
        frame.setUndecorated(true);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void reload() {
        PDV pdv = new PDV(util, selectedCategoryID, cartProducts);
        pdv.refreshTotal();
        frame.setVisible(false);
        frame.dispose();
    }
    
    public void refreshTotal() {
        totalValue = 0;
        cartProducts.forEach((prod) -> {
            totalValue += prod.getPrice();
        });
        total.setText("Total: R$ " + String.format("%.2f", totalValue));
    }
    
    //Carrinho
    public void addToCart(Product prod) {
        cartProducts.add(prod);
        listItems.addRow(new Object[]{prod.getName(), "R$ " + String.format("%.2f", prod.getPrice())});
        refreshTotal();
    }
    
    public void removeFromCart(int id) {
        cartProducts.remove(id);
        listItems.removeRow(id);
        refreshTotal();
    }
    
    public void clearCart() {
        for(int i = listItems.getRowCount() - 1; i >= 0; i--) {
            listItems.removeRow(i);
        }
        cartProducts.clear();
        refreshTotal();
    }  
}