/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Checkout;

import Builders.Client;
import FutbarPDV.Listener;
import View.CustomSwing.CustomLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author davi
 */
public class Mark {
     
    private final Listener listener;
    
    public final JFrame frame;
    private final JPanel panel;
    
    public Map<Integer, JPanel> buttons = new HashMap<>();
    private final double totalValue;
    private final Client client;

    
    public Mark(Listener listener, double totalValue, Client client) {
        frame = new JFrame();
        panel = new JPanel();
        this.listener = listener;
        this.totalValue = totalValue;
        this.client = client;
        preparePanel();
        prepareFrame();
    }
    
    private void preparePanel() {
        GridBagLayout layout = new GridBagLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension((int) (screenSize.getWidth()*0.4), (int) (screenSize.getHeight()*0.3)));
        addComponents();
    }
    
    private void addComponents() {
        JLabel total = new CustomLabel("Total: R$ " + String.format("%.2f", totalValue), 55, "#6D41F3");
        JPanel finalizar = addButton("Confirmar", 50, "#AA7AFD");
        
        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(10,10,10,10);
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 1;
        cons.weighty = 1;
        panel.add(total, cons);
        cons.gridy = 1;
        panel.add(textPanel(), cons);
        cons.gridy = 2;
        cons.fill = GridBagConstraints.BOTH;
        panel.add(finalizar, cons);
        
        buttons.put(0, finalizar);
        finalizar.addMouseListener(listener);
    }
    
    private JPanel textPanel() {
        GridBagConstraints cons = new GridBagConstraints();
        JPanel textPanel = new JPanel(new GridBagLayout());
        JLabel marcar = new CustomLabel("Marcar para: ", 45, true);
        JLabel cliente = new CustomLabel(client.getName(), 50, "#FF4444");
        
        cons.gridx = 0;
        cons.gridy = 0;
        cons.weightx = 0.6;
        cons.weighty = 1;
        textPanel.add(marcar, cons);
        cons.gridx = 1;
        cons.gridy = 0;
        cons.weightx = 0.4;
        cons.weighty = 1;
        textPanel.add(cliente, cons);
        
        return textPanel;
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
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(listener);
        frame.setVisible(true);
    }
    
    public Client getClient() {
        return client;
    }
}