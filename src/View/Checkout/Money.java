/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Checkout;

import FutbarPDV.Listener;
import View.CustomSwing.CustomLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author davi
 */
public class Money {
     
    private final Listener listener;
    private final double totalValue;
    public final double valuePaid;
    
    public final JFrame frame;
    private final JPanel panel;
    
    public Map<Integer, JPanel> buttons = new HashMap<>();

    
    public Money(Listener listener, double totalValue, double valuePaid) {
        frame = new JFrame();
        panel = new JPanel();
        this.listener = listener;
        this.totalValue = totalValue;
        this.valuePaid = valuePaid;
        preparePanel();
        prepareFrame();
    }
    
    private void preparePanel() {
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(650, 400));
        addComponents();
    }
    
    private void addComponents() {
        JLabel total = new CustomLabel("Total: R$ " + String.format("%.2f", totalValue), 55, "#6D41F3");
        JLabel paid = new CustomLabel("Pago: R$ " + String.format("%.2f", valuePaid), 55, "#6D41F3");
        JLabel troco = new CustomLabel("Troco: R$ " + String.format("%.2f", valuePaid-totalValue), 65, "#FF4444");        
        JPanel confirmar = addButton("Finalizar", 50, "#AA7AFD");
        
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
        panel.add(troco, cons);
        cons.gridy = 3;
        cons.fill = GridBagConstraints.BOTH;
        panel.add(confirmar, cons);
        
        buttons.put(0, confirmar);
        confirmar.addMouseListener(listener);
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
        frame.setVisible(true);
    }
}