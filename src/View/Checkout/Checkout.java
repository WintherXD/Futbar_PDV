/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Checkout;

import View.CustomSwing.CustomLabel;
import FutbarPDV.Listener;
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
public class Checkout { 
    
    private final Listener listener;
    private final double totalValue;
    private final boolean mark;
    
    public final JFrame frame;
    private final JPanel panel;
    
    public Map<Integer, JPanel> buttons = new HashMap<>();

    
    public Checkout(Listener listener, Double totalValue, boolean mark) {
        frame = new JFrame();
        panel = new JPanel();
        this.listener = listener;
        this.totalValue = totalValue;
        this.mark = mark;
        preparePanel();
        prepareFrame();
    }
    
    private void preparePanel() {
        GridBagLayout layout = new GridBagLayout();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension((int) (screenSize.getWidth()*0.25), (int) (screenSize.getHeight()*0.5)));
        addComponents();
    }
    
    private void addComponents() {
        JLabel total = new CustomLabel("R$ " + String.format("%.2f", totalValue), 65, "#FF4444");
        JPanel dinheiro = addButton("Dinheiro", 50, "#AA7AFD");
        JPanel cartao = addButton("Cartão", 50, "#AA7AFD");
        JPanel marcar = addButton("Marcar", 50, "#AA7AFD");

        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(10,10,10,10);
        cons.weightx = 1;
        cons.weighty = 1;
        cons.gridx = 0;
        cons.gridy = 0;
        panel.add(total, cons);
        cons.gridy = 1;
        cons.fill = GridBagConstraints.BOTH;
        panel.add(dinheiro, cons);
        cons.gridy = 2;
        panel.add(cartao, cons);
        
        if(mark) {
            cons.gridy = 3;
            panel.add(marcar, cons);
            marcar.addMouseListener(listener);
            buttons.put(2, marcar);
        }
        
        buttons.put(0, dinheiro);
        buttons.put(1, cartao);
        
        dinheiro.addMouseListener(listener);
        cartao.addMouseListener(listener);
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
