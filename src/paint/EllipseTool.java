/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Allows the user to draw an ellipse.
 * 
 * @author tanya
 */
public class EllipseTool {

    /**
     * Sets up the mouse controls used to draw an ellipse.
     * The ellipse is shown while the user drags the mouse.
     *
     * @param drawingArea the drawing area where the ellipse is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create temporary ellipse for the live preview
        Ellipse previewEllipse =
                new Ellipse();

        // ellipse should only have an outline
        previewEllipse.setFill(
                Color.TRANSPARENT
        );

        // make sure the preview does not block the mouse
        previewEllipse.setMouseTransparent(
                true
        );

        // hide preview until drawing begins
        previewEllipse.setVisible(
                false
        );

        // add preview above the canvas
        drawingArea.drawingPane.getChildren().add(
                previewEllipse
        );

        // save the starting point when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save the current image before drawing the new ellipse
            UndoRedo.saveState(
                    drawingArea
            );

            // get the starting location of the ellipse
            drawingArea.startX =
                    event.getX();

            drawingArea.startY =
                    event.getY();

            // apply selected line width and color
            previewEllipse.setStrokeWidth(
                    drawingArea.lineWidth
            );

            previewEllipse.setStroke(
                    drawingArea.lineColor
            );

            // copy dashed outline setting if turned on
            double[] dashes =
                    drawingArea.gc.getLineDashes();

            previewEllipse.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewEllipse.getStrokeDashArray().add(
                            dash
                    );
                }
            }

            // start ellipse with no size
            previewEllipse.setCenterX(
                    drawingArea.startX
            );

            previewEllipse.setCenterY(
                    drawingArea.startY
            );

            previewEllipse.setRadiusX(
                    0
            );

            previewEllipse.setRadiusY(
                    0
            );

            // show live preview
            previewEllipse.setVisible(
                    true
            );
        });

        // update the ellipse while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // get current mouse location
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find left and top sides
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

            // update center of live ellipse
            previewEllipse.setCenterX(
                    x + width / 2
            );

            previewEllipse.setCenterY(
                    y + height / 2
            );

            // update ellipse size
            previewEllipse.setRadiusX(
                    width / 2
            );

            previewEllipse.setRadiusY(
                    height / 2
            );
        });

        // draw the final ellipse when mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get ending location
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find the left side of the ellipse
            double x =
                    Math.min(
                            drawingArea.startX,
                            endX
                    );

            // find the top side of the ellipse
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

            // hide temporary preview
            previewEllipse.setVisible(
                    false
            );

            // apply selected line width and color
            drawingArea.gc.setLineWidth(
                    drawingArea.lineWidth
            );

            drawingArea.gc.setStroke(
                    drawingArea.lineColor
            );

            // draw the final ellipse
            drawingArea.gc.strokeOval(
                    x,
                    y,
                    width,
                    height
            );

            // mark drawing as changed
            drawingArea.modified =
                    true;
        });
    }
}