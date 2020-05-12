/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Checkout;

import FutbarPDV.Listener;
import View.CustomSwing.CustomLabel;
import View.PDV;
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
public class Card {
     
    private final Listener listener;
    
    public final JFrame frame;
    private final JPanel panel;
    
    public Map<Integer, JPanel> buttons = new HashMap<>();
    private final double totalValue;
    
    public Card(Listener listener, double totalValue) {
        frame = new JFrame();
        panel = new JPanel();
        this.listener = listener;
        this.totalValue = totalValue;
        preparePanel();
        prepareFrame();
    }
    
    private void preparePanel() {
        GridBagLayout layout = new GridBagLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension((int) (screenSize.getWidth()*0.3), (int) (screenSize.getHeight()*0.3)));
        addComponents();
    }
    
    private void addComponents() {
        JLabel total = new CustomLabel("Total: R$ " + String.format("%.2f", totalValue), 55, "#6D41F3");
        JLabel paid = new CustomLabel("Insira ou passe o cartão", 40, true);
        JPanel finalizar = addButton("Finalizar", 50, "#AA7AFD");
        
        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(10,10,10,10);
        cons.weightx = 1;
        cons.weighty = 1;
        cons.gridx = 0;
        cons.gridy = 0;
        panel.add(total, cons);
        cons.gridy = 1;
        panel.add(paid, cons);
        cons.gridy = 2;
        cons.fill = GridBagConstraints.BOTH;
        panel.add(finalizar, cons);
        
        buttons.put(0, finalizar);
        finalizar.addMouseListener(listener);
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
}