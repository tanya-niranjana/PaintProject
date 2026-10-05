/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.shape.Line;

/**
 * Sets up the straight line drawing tool.
 * 
 * @author tanya
 */
public class SetUpDrawing {

    /**
     * Sets up the mouse controls used to draw a straight line.
     * The line is shown while the user drags the mouse.
     * 
     * @param drawingArea the drawing area where the line is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create the temporary line used for the live preview
        Line previewLine =
                new Line();

        // make sure the preview does not block mouse controls
        previewLine.setMouseTransparent(
                true
        );

        // hide the preview until the user starts drawing
        previewLine.setVisible(
                false
        );

        // add the preview line above the canvas
        drawingArea.drawingPane.getChildren().add(
                previewLine
        );

        // start the line when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save the current image before drawing the new line
            UndoRedo.saveState(
                    drawingArea
            );

            // get the location where the user started the line
            drawingArea.startX =
                    event.getX();

            drawingArea.startY =
                    event.getY();

            // set the preview starting point
            previewLine.setStartX(
                    drawingArea.startX
            );

            previewLine.setStartY(
                    drawingArea.startY
            );

            previewLine.setEndX(
                    drawingArea.startX
            );

            previewLine.setEndY(
                    drawingArea.startY
            );

            // use the selected line width and color
            previewLine.setStrokeWidth(
                    drawingArea.lineWidth
            );

            previewLine.setStroke(
                    drawingArea.lineColor
            );

            // copy the dashed line setting if it is turned on
            double[] dashes =
                    drawingArea.gc.getLineDashes();

            previewLine.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewLine.getStrokeDashArray().add(
                            dash
                    );
                }
            }

            // show the preview line
            previewLine.setVisible(
                    true
            );
        });

        // update the preview while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            previewLine.setEndX(
                    event.getX()
            );

            previewLine.setEndY(
                    event.getY()
            );
        });

        // finish the line when the mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get the ending point of the line
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // hide the temporary preview
            previewLine.setVisible(
                    false
            );

            // apply the selected line width and color
            drawingArea.gc.setLineWidth(
                    drawingArea.lineWidth
            );

            drawingArea.gc.setStroke(
                    drawingArea.lineColor
            );

            // draw the final line onto the canvas
            drawingArea.gc.strokeLine(
                    drawingArea.startX,
                    drawingArea.startY,
                    endX,
                    endY
            );

            // mark the drawing as changed
            drawingArea.modified =
                    true;
        });
    }
}