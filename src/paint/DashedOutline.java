/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

/**
 * Controls whether lines and shapes have
 * solid or dashed lines
 * 
 * @author tanya
 */
public class DashedOutline {
    
     /**
     * Turns on dashed lines for the drawing tools.
     *
     * @param drawingArea the drawing area where dashed lines are used
     */
     public static void turnOn(
            DrawingArea drawingArea) {
         
         // set the line pattern to dashed
        drawingArea.gc.setLineDashes(10);
    }

     /**
     * Turns off dashed lines and returns to solid lines.
     *
     * @param drawingArea the drawing area where solid lines are used
     */
     
    public static void turnOff(
            DrawingArea drawingArea) {

        // removes dash and returns to solid line
        drawingArea.gc.setLineDashes(null);
    }
}
