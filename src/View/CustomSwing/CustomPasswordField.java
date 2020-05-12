/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.CustomSwing;

import java.awt.Font;
import javax.swing.JPasswordField;

/**
 *
 * @author davi
 */
public class CustomPasswordField extends JPasswordField{
    
    public CustomPasswordField(int textSize) {
        setFont(new Font("Arial", Font.PLAIN, textSize));
    }
}
