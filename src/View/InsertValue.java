/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.CustomSwing.CustomTextField;
import View.CustomSwing.CustomLabel;
import FutbarPDV.Listener;
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
import javax.swing.JTextField;

/**
 *
 * @author davi
 */
public class InsertValue { 
    
    private final Listener listener;
    
    public final JFrame frame;
    private final JPanel panel;
    public JTextField value;
    public JPanel enter;
    
    public Map<Integer, JPanel> buttons = new HashMap<>();

    /*
        Mode: 0 -> Adicionar a lista de itens
              1 -> Valor pago
    */
    public int mode;
    
    public InsertValue(Listener listener, int mode) {
        frame = new JFrame();
        panel = new JPanel();
        this.listener = listener;
        this.mode = mode;
        preparePanel();
        prepareFrame();
    }
    
    private void preparePanel() {
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(600, 600));
        addComponents();
    }
    
    private void addComponents() {
        value = new CustomTextField("R$ ", 60);
        enter = addButton("OK", 60, "#FF4444");
        enter.addMouseListener(listener);
        buttons.put(12, enter);

        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(5,5,5,5);
        cons.fill = GridBagConstraints.BOTH;
        cons.weightx = 1;
        cons.weighty = 0.25;
        cons.gridx = 0;
        cons.gridy = 0;
        panel.add(value, cons);
        cons.gridy = 1;
        cons.weighty = 0.50;
        panel.add(numbersPanel(), cons);
        cons.gridy = 2;
        cons.weighty = 0.25;
        panel.add(enter, cons);
        
        value.addKeyListener(listener);
    }
    
    private JPanel numbersPanel() {
        GridBagConstraints cons = new GridBagConstraints();
        JPanel np = new JPanel(new GridBagLayout());

        cons.fill = GridBagConstraints.BOTH;
        cons.insets = new Insets(2, 2, 2, 2);
        cons.weightx = 1;
        cons.weighty = 1;
        
        int n = 1;
        for(int y=0; y<3; y++) {
            for(int x=0; x<3; x++) {
                JPanel btn = addButton(String.valueOf(n), 80, "#6F85FF");
                btn.addMouseListener(listener);
                
                cons.gridx = x;
                cons.gridy = y;
                np.add(btn, cons);
                buttons.put(n, btn);
                
                n++;
            }
        }
        
        for(int i=0; i<3; i++) {
            cons.gridx = i;
            cons.gridy = 3;
            
            JPanel btn = null;
            switch(i) {
                case 0:
                    btn = addButton(",", 80, "#6F85FF");
                    btn.addMouseListener(listener);
                    buttons.put(10, btn);
                    break;
                case 1:
                    btn = addButton("0", 80, "#6F85FF");
                    btn.addMouseListener(listener);
                    buttons.put(0, btn);
                    break;
                case 2:
                    btn = addImageButton("apagar.png", "#6F85FF");
                    btn.addMouseListener(listener);
                    buttons.put(11, btn);
                    break;
            }
            np.add(btn, cons);
        }
        
        return np;
    }
    
    private JPanel addButton(String text, int textSize, String color) {
        JPanel btn = new JPanel(new GridBagLayout());
        JLabel btnName = new CustomLabel(text, textSize, true);

        btn.setBackground(Color.decode(color));
        btn.add(btnName);
        
        return btn;
    }
    
    private JPanel addImageButton(String img, String color) {
        JPanel btn = new JPanel(new GridBagLayout());
        JLabel btnName = new CustomLabel(img);

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
