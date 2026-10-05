/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Displays info about the selected color
 * shows hex and RGB values
 * 
 * @author tanya
 */
public class ColorTextLabel {
    
    /**
     * Updates the color text label using the selected color.
     *
     * @param color the currently selected color
     * @param colorText the label used to display the color information
     */
    
    public static void update(
            Color color,
            Label colorText) {

        // converts color into a number from 0 to 255
        
        int red =
                (int) (color.getRed() * 255);

        int green =
                (int) (color.getGreen() * 255);

        int blue =
                (int) (color.getBlue() * 255);
        
        // converts RBG value into hex value

        String hex = String.format(
                "#%02X%02X%02X",
                red,
                green,
                blue
        );

        colorText.setText(
                hex
                + "  RGB("
                + red + ", "
                + green + ", "
                + blue + ")"
        );
    }
}
