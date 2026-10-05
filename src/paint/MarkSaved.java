/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

/**
 * marks the current drawing as saved 
 * 
 * @author tanya
 */
public class MarkSaved {
    
    /**
     * changes the drawing area's modified status to false
     * after the image has been saved
     * 
     * @param drawingArea the drawing area being marked as saved
     */   
    public static void markSaved(DrawingArea drawingArea) {
        
        // mark the current image as saved
        drawingArea.modified = false;
    }
}
