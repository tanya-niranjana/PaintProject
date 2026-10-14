/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Allows user to draw a square.
 * 
 * @author tanya
 */
public class SqaureTool {
    
    /**
     * Sets up the mouse controls used to draw a square.
     * The square is shown while the user drags the mouse.
     *
     * @param drawingArea the drawing area where the square is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create temporary square for the live preview
        Rectangle previewSquare =
                new Rectangle();

        // square should only have an outline
        previewSquare.setFill(Color.TRANSPARENT);

        // make sure preview does not block the mouse
        previewSquare.setMouseTransparent(true);

        // hide preview until drawing begins
        previewSquare.setVisible(false);

        // add preview above the canvas
        drawingArea.drawingPane.getChildren().add(previewSquare);

        // save starting point when mouse is pressed
        drawingArea.canvas.setOnMousePressed(event -> {

            // save current image before drawing new square
            UndoRedo.saveState(drawingArea);

            // get starting location
            drawingArea.startX = event.getX();

            drawingArea.startY = event.getY();

            // apply selected width and color
            previewSquare.setStrokeWidth(drawingArea.lineWidth);

            previewSquare.setStroke(drawingArea.lineColor);

            // copy dashed outline setting if turned on
            double[] dashes = drawingArea.gc.getLineDashes();

            previewSquare.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewSquare.getStrokeDashArray().add(
                            dash
                    );
                }
            }

            // start square with size zero
            previewSquare.setX(drawingArea.startX);

            previewSquare.setY(drawingArea.startY);

            previewSquare.setWidth(0);

            previewSquare.setHeight(0);

            // show the live preview
            previewSquare.setVisible(true);
        });

        // update the square while mouse is dragged
        drawingArea.canvas.setOnMouseDragged(event -> {

            // ending location
            double endX = event.getX();

            double endY = event.getY();

            // calculate width and height
            double width = Math.abs(endX - drawingArea.startX);

            double height = Math.abs(endY - drawingArea.startY);

            // use smaller value so shape stays a square
            double size = Math.min(width,height);

            // start square at original mouse position
            double x = drawingArea.startX;

            double y = drawingArea.startY;

            // adjust x if dragged left
            if (endX < drawingArea.startX) {

                x =
                        drawingArea.startX
                        - size;
            }

            // adjust y if dragged up
            if (endY < drawingArea.startY) {

                y =
                        drawingArea.startY
                        - size;
            }

            // update live preview
            previewSquare.setX(x);

            previewSquare.setY(y);

            previewSquare.setWidth(size);

            previewSquare.setHeight(size);
        });

        // draw the final square when mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // ending location
            double endX = event.getX();

            double endY = event.getY();

            // calculate width and height
            double width =
                    Math.abs(endX - drawingArea.startX);

            double height =
                    Math.abs(endY - drawingArea.startY);

            // use smaller value so shape stays a square
            double size =
                    Math.min(width,height);

            // start square at original mouse position
            double x = drawingArea.startX;

            double y = drawingArea.startY;

            // adjust x if dragged left
            if (endX < drawingArea.startX) {

                x =drawingArea.startX - size;
            }

            // adjust y if dragged up
            if (endY < drawingArea.startY) {

                y = drawingArea.startY - size;
            }

            // hide temporary preview
            previewSquare.setVisible(false);

            // set line width and color
            drawingArea.gc.setLineWidth(drawingArea.lineWidth);

            drawingArea.gc.setStroke(drawingArea.lineColor);

            // draw final square
            drawingArea.gc.strokeRect(
                    x,
                    y,
                    size,
                    size
            );

            // mark drawing as changed
            drawingArea.modified = true;
        });
    }
}