/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

/**
 * Handles changing the size of the canvas and drawing area. 
 * 
 * @author tanya
 */
public class ResizedDrawingArea {
     /**
     * Resizes the canvas and drawing area using the width
     * and height entered by the user.
     *
     * @param drawingArea the drawing area being resized
     * @param width the new width of the drawing area
     * @param height the new height of the drawing area
     */    
    public static void resizeDrawingArea(
            DrawingArea drawingArea,
            double width,
            double height) {
        
        // only resizes if the user entered valid positive numbers
        if (width > 0 && height > 0){
        
            // change width and height of the main canvas
            drawingArea.canvas.setWidth(width);
            drawingArea.canvas.setHeight(height);
            
          
            // change the size of the drawing area 
            drawingArea.drawingPane.setPrefSize(width, height);

            // resizing count as am unsaved change 
            drawingArea.modified = true; 
        }
    }

}
