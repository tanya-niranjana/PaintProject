/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Allows user to draw a rectangle.
 * 
 * @author tanya
 */
public class RectangleTool {
    
    /**
     * Sets up the mouse controls used to draw a rectangle.
     * The rectangle is shown while the user drags the mouse.
     * 
     * @param drawingArea the drawing area where the rectangle is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create the temporary rectangle used for the live preview
        Rectangle previewRectangle =
                new Rectangle();

        // rectangle should only have an outline
        previewRectangle.setFill(
                Color.TRANSPARENT
        );

        // make sure the preview does not block the mouse
        previewRectangle.setMouseTransparent(
                true
        );

        // hide the preview until drawing begins
        previewRectangle.setVisible(
                false
        );

        // add the preview rectangle above the canvas
        drawingArea.drawingPane.getChildren().add(
                previewRectangle
        );

        // save the starting point when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save the current image before drawing the new rectangle
            UndoRedo.saveState(
                    drawingArea
            );

            // get the starting location of the rectangle
            drawingArea.startX =
                    event.getX();

            drawingArea.startY =
                    event.getY();

            // apply the selected line width and color
            previewRectangle.setStrokeWidth(
                    drawingArea.lineWidth
            );

            previewRectangle.setStroke(
                    drawingArea.lineColor
            );

            // copy the dashed outline setting if turned on
            double[] dashes =
                    drawingArea.gc.getLineDashes();

            previewRectangle.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewRectangle.getStrokeDashArray().add(
                            dash
                    );
                }
            }

            // start with a rectangle size of zero
            previewRectangle.setX(
                    drawingArea.startX
            );

            previewRectangle.setY(
                    drawingArea.startY
            );

            previewRectangle.setWidth(
                    0
            );

            previewRectangle.setHeight(
                    0
            );

            // show the preview
            previewRectangle.setVisible(
                    true
            );
        });

        // update the rectangle while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // get current mouse location
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find the left and top sides
            double x =
                    Math.min(
                            drawingArea.startX,
                            endX
                    );

            double y =
                    Math.min(
                            drawingArea.startY,
                            endY
                    );

            // calculate width and height
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            double height =
                    Math.abs(
                            endY - drawingArea.startY
                    );

            // update the live preview
            previewRectangle.setX(
                    x
            );

            previewRectangle.setY(
                    y
            );

            previewRectangle.setWidth(
                    width
            );

            previewRectangle.setHeight(
                    height
            );
        });

        // draw the final rectangle when the mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get ending location
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find the left side of rectangle
            double x =
                    Math.min(
                            drawingArea.startX,
                            endX
                    );

            // find top side of rectangle
            double y =
                    Math.min(
                            drawingArea.startY,
                            endY
                    );

            // calculate the rectangle width and height
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            double height =
                    Math.abs(
                            endY - drawingArea.startY
                    );

            // hide the temporary preview
            previewRectangle.setVisible(
                    false
            );

            // apply line width and color
            drawingArea.gc.setLineWidth(
                    drawingArea.lineWidth
            );

            drawingArea.gc.setStroke(
                    drawingArea.lineColor
            );

            // draw the final rectangle
            drawingArea.gc.strokeRect(
                    x,
                    y,
                    width,
                    height
            );

            // mark the drawing as changed so it needs to be saved
            drawingArea.modified =
                    true;
        });
    }
}