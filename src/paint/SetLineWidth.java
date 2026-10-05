/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

/**
 * handles changing the width used for lines and shapes
 * 
 * @author tanya
 */
public class SetLineWidth {
    /**
     * updates the drawing width used by the drawing area
     * 
     * @param drawingArea the drawing area whose line width is being changed
     * @param width the new line width
     */    
    public static void setLineWidth(DrawingArea drawingArea, double width){
        
        // update the width used for the new lines and shapes
        drawingArea.lineWidth = width;
    }
}
