/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.CustomSwing.CustomTextField;
import View.CustomSwing.CustomPasswordField;
import View.CustomSwing.CustomButton;
import View.CustomSwing.CustomLabel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author davi
 */
public class Login { 
    
    public JFrame frame;
    public JPanel panel;
    public JTextField userField;
    public JPasswordField passwordField;
    public JButton loginBtn;
    
    public Login() {
        frame = new JFrame();
        panel = new JPanel();
        preparePanel();
        prepareFrame();
    }
    
    private void preparePanel() {
        GridLayout layout = new GridLayout(1, 2);
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(400, 200));
        addComponents();
    }
    
    private void addComponents() {
        JLabel img = new CustomLabel("login.png");
        JLabel user = new CustomLabel("Usuário", 24, true);
        userField = new CustomTextField(16);
        JLabel password = new CustomLabel("Senha: ", 24, true);
        passwordField = new CustomPasswordField(16);
        loginBtn = new CustomButton("Entrar", 16);

        //Campos de Texto e Botão Login
        GridBagConstraints cons = new GridBagConstraints();
        GridBagLayout layoutBtn = new GridBagLayout();
        JPanel buttons = new JPanel(layoutBtn);
        
        cons.insets = new Insets(5,5,5,5);
        cons.fill = GridBagConstraints.BOTH;
        cons.weightx = 1;
        cons.weighty = 1;
        cons.gridx = 0;
        cons.gridy = 0;
        buttons.add(user, cons);
        cons.gridy = 1;
        buttons.add(userField, cons);
        cons.gridy = 2;
        buttons.add(password, cons);
        cons.gridy = 3;
        buttons.add(passwordField, cons);
        cons.gridy = 4;
        buttons.add(loginBtn, cons);
        
        panel.add(img);
        panel.add(buttons);
    }
    
    private void prepareFrame() {
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
