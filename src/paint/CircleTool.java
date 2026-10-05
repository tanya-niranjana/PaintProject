/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Allows user to draw a circle.
 * 
 * @author tanya
 */
public class CircleTool {
    
    /**
     * Sets up the mouse controls used to draw a circle.
     * The circle is shown while the user drags the mouse.
     * 
     * @param drawingArea the drawing area where the circle is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create temporary circle for the live preview
        Circle previewCircle =
                new Circle();

        // circle should only have an outline
        previewCircle.setFill(
                Color.TRANSPARENT
        );

        // make sure preview does not block the mouse
        previewCircle.setMouseTransparent(
                true
        );

        // hide preview until drawing begins
        previewCircle.setVisible(
                false
        );

        // add preview above the canvas
        drawingArea.drawingPane.getChildren().add(
                previewCircle
        );

        // save the starting point when mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save the current image before drawing the new circle
            UndoRedo.saveState(
                    drawingArea
            );

            // get starting location
            drawingArea.startX =
                    event.getX();

            drawingArea.startY =
                    event.getY();

            // apply selected line width and color
            previewCircle.setStrokeWidth(
                    drawingArea.lineWidth
            );

            previewCircle.setStroke(
                    drawingArea.lineColor
            );

            // copy dashed outline setting if turned on
            double[] dashes =
                    drawingArea.gc.getLineDashes();

            previewCircle.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewCircle.getStrokeDashArray().add(
                            dash
                    );
                }
            }

            // start circle with radius zero
            previewCircle.setCenterX(
                    drawingArea.startX
            );

            previewCircle.setCenterY(
                    drawingArea.startY
            );

            previewCircle.setRadius(
                    0
            );

            // show live preview
            previewCircle.setVisible(
                    true
            );
        });

        // update circle while mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // get ending location
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // calculate width of dragged area
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            // calculate height of dragged area
            double height =
                    Math.abs(
                            endY - drawingArea.startY
                    );

            // use smaller value so circle stays a circle
            double size =
                    Math.min(
                            width,
                            height
                    );

            // start circle at original mouse position
            double x =
                    drawingArea.startX;

            double y =
                    drawingArea.startY;

            // adjust x if user drags left
            if (endX < drawingArea.startX) {

                x =
                        drawingArea.startX
                        - size;
            }

            // adjust y if user drags up
            if (endY < drawingArea.startY) {

                y =
                        drawingArea.startY
                        - size;
            }

            // update center of preview circle
            previewCircle.setCenterX(
                    x + size / 2
            );

            previewCircle.setCenterY(
                    y + size / 2
            );

            // update radius
            previewCircle.setRadius(
                    size / 2
            );
        });

        // draw final circle when mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get ending location
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // calculate width of dragged area
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            // calculate height of dragged area
            double height =
                    Math.abs(
                            endY - drawingArea.startY
                    );

            // use smaller value so circle stays a circle
            double size =
                    Math.min(
                            width,
                            height
                    );

            // start circle at original mouse position
            double x =
                    drawingArea.startX;

            double y =
                    drawingArea.startY;

            // adjust x if user drags left
            if (endX < drawingArea.startX) {

                x =
                        drawingArea.startX
                        - size;
            }

            // adjust y if user drags up
            if (endY < drawingArea.startY) {

                y =
                        drawingArea.startY
                        - size;
            }

            // hide temporary preview
            previewCircle.setVisible(
                    false
            );

            // apply selected line width
            drawingArea.gc.setLineWidth(
                    drawingArea.lineWidth
            );

            // apply selected line color
            drawingArea.gc.setStroke(
                    drawingArea.lineColor
            );

            // draw the final circle
            drawingArea.gc.strokeOval(
                    x,
                    y,
                    size,
                    size
            );

            // mark drawing as changed so it needs to be saved
            drawingArea.modified =
                    true;
        });
    }
}