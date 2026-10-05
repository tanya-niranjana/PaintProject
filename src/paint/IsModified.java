/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

/**
 * checks whether the current drawing has unsaved changes 
 * 
 * @author tanya
 */
public class IsModified {
    /**
     * Returns whether the drawing area has been modified.
     *
     * @param drawingArea the drawing area being checked
     * @return true if the drawing has unsaved changes
     */    public static boolean isModified(DrawingArea drawingArea) {
        return drawingArea.modified;
    }
}
