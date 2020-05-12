/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.CustomSwing;

import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author davi
 */
public class CustomButton extends JButton {
    
    public CustomButton(String text, int textSize) {
        setFont(new Font("DecoType Naskh", Font.BOLD, textSize));
        setText(text);
    }
}
