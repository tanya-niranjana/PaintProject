/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.input.MouseEvent;

/**
 * Allows the user to draw freely with the mouse
 * 
 * @author tanya
 */
public class PencilTool {
    
    /**
    * sets up the mouse control used for the pencil tool
    * 
    * @param drawingArea the drawing area where freehand drawing is created
    */
    public static void setupDrawing(DrawingArea drawingArea) {

        // turn off mouse control from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseClicked(null);
        drawingArea.canvas.setOnMouseDragged(null);

        // start the pencil path when the mouse is pressed. 
        drawingArea.canvas.setOnMousePressed((MouseEvent event) -> {

            // save the current image before drawing with the pencil
            UndoRedo.saveState(drawingArea);        
                    
            // apply the selected line width
            drawingArea.gc.setLineWidth(drawingArea.lineWidth);

            // apply the selected line color
            drawingArea.gc.setStroke(drawingArea.lineColor);

            // begin a new drawing path
            drawingArea.gc.beginPath();

            // move the starting point to where the user clicked
            drawingArea.gc.moveTo(
                    event.getX(),
                    event.getY()
            );
        });

        // draw feeling while the user drags the mouse
        drawingArea.canvas.setOnMouseDragged((MouseEvent event) -> {

            // extend the line to the current mouse position
            drawingArea.gc.lineTo(
                    event.getX(),
                    event.getY()
            );

            // draw the current path
            drawingArea.gc.stroke();

            // mark the drawing as changed 
            drawingArea.modified = true;
        });

        // finish the pencil path when the mouse is released
        drawingArea.canvas.setOnMouseReleased(event -> {

            // close the current drawing path
            drawingArea.gc.closePath();
        });
    }
}
