/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.CustomSwing;

import java.awt.Font;
import javax.swing.JTextField;

/**
 *
 * @author davi
 */
public class CustomTextField extends JTextField {
    
    public CustomTextField(String text, int textSize) {
        setText(text);
        setFont(new Font("Arial", Font.PLAIN, textSize));
    }
    
    public CustomTextField(int textSize) {
        setFont(new Font("Arial", Font.PLAIN, textSize));
    }
}
