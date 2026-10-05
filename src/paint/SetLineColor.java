/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.paint.Color;
/**
 * handles changing the color used for new lines and shapes
 *
 * @author tanya
 */
public class SetLineColor {
    /**
     * updates the drawing color used by the drawing area
     * 
     * 
     * @param drawingArea the drawing area whose color is being changed
     * @param color the new drawing color
     */   
    public static void setLineColor(DrawingArea drawingArea, Color color) {
        
        // update the color used for new lines and shapes
        drawingArea.lineColor = color;
    }
}
