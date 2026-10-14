/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

/**
 * Allows the user to erase drawings.
 * 
 * @author tanya
 */
public class EraserTool {
    
    /**
     * Sets up the mouse controls used for the eraser tool.
     *
     * @param drawingArea the drawing area where drawings are erased
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off the mouse controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseClicked(null);
        drawingArea.canvas.setOnMouseDragged(null);

        // save the current image before the user starts erasing
        drawingArea.canvas.setOnMousePressed(event -> {
            UndoRedo.saveState(drawingArea
            );
        });

        // erase while the user drags the mouse
        drawingArea.canvas.setOnMouseDragged(event -> {

            // make the eraser size based on the current line width
            double size =drawingArea.lineWidth * 3;

            // clear part of the canvas around the mouse
            drawingArea.gc.clearRect(
                    event.getX() - size / 2,
                    event.getY() - size / 2,
                    size,
                    size
            );
            
            // mark drawing as changed so it needs to be saved
            drawingArea.modified = true;
        });
    }
}