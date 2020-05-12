/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.CustomSwing;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author davi
 */
public class CustomLabel extends JLabel{
    
    public CustomLabel(String text, int textSize, boolean bold) {
        setText(text);
        if(bold) {
            setFont(new Font("DecoType Naskh", Font.BOLD, textSize));
        } else {
            setFont(new Font("DecoType Naskh", Font.PLAIN, textSize));            
        }
        setHorizontalTextPosition(JLabel.CENTER);
    }
    
    public CustomLabel(String text, int textSize, String color) {
        setText(text);
        setFont(new Font("DecoType Naskh", Font.BOLD, textSize));
        setHorizontalTextPosition(JLabel.CENTER);
        setForeground(Color.decode(color));
    }
    
    
    public CustomLabel(String imgResource) {
        ImageIcon img = new ImageIcon(getClass().getResource("/images/" + imgResource));
        setIcon(img);
        setHorizontalAlignment(JLabel.CENTER);
    }
}
