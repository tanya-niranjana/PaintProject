/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Allows the user to draw a right triangle.
 *
 * @author tanya
 */
public class RightTriangleTool {

    /**
     * Sets up the mouse controls used to draw a right triangle.
     * The triangle is shown while the user drags the mouse.
     *
     * @param drawingArea the drawing area where the triangle is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create temporary triangle for the live preview
        Polygon previewTriangle =
                new Polygon();

        // triangle should only have an outline
        previewTriangle.setFill(
                Color.TRANSPARENT
        );

        // make sure the preview does not block the mouse
        previewTriangle.setMouseTransparent(
                true
        );

        // hide preview until drawing begins
        previewTriangle.setVisible(
                false
        );

        // add preview above the canvas
        drawingArea.drawingPane.getChildren().add(
                previewTriangle
        );

        // save the starting point when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save current image before drawing the triangle
            UndoRedo.saveState(
                    drawingArea
            );

            // save starting x and y
            drawingArea.startX =
                    event.getX();

            drawingArea.startY =
                    event.getY();

            // apply selected line width
            previewTriangle.setStrokeWidth(
                    drawingArea.lineWidth
            );

            // apply selected line color
            previewTriangle.setStroke(
                    drawingArea.lineColor
            );

            // copy dashed outline setting if turned on
            double[] dashes =
                    drawingArea.gc.getLineDashes();

            previewTriangle.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewTriangle.getStrokeDashArray().add(
                            dash
                    );
                }
            }

            // start triangle with all points at starting location
            previewTriangle.getPoints().setAll(
                    drawingArea.startX,
                    drawingArea.startY,

                    drawingArea.startX,
                    drawingArea.startY,

                    drawingArea.startX,
                    drawingArea.startY
            );

            // show the live preview
            previewTriangle.setVisible(
                    true
            );
        });

        // update the right triangle while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // get current ending location
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // update the three triangle points
            previewTriangle.getPoints().setAll(
                    drawingArea.startX,
                    drawingArea.startY,

                    drawingArea.startX,
                    endY,

                    endX,
                    endY
            );
        });

        // draw the final right triangle when the mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get ending x and y
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // create the three x points
            double[] xPoints = {
                drawingArea.startX,
                drawingArea.startX,
                endX
            };

            // create the three y points
            double[] yPoints = {
                drawingArea.startY,
                endY,
                endY
            };

            // hide temporary preview
            previewTriangle.setVisible(
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

            // draw the final triangle
            drawingArea.gc.strokePolygon(
                    xPoints,
                    yPoints,
                    3
            );

            // mark drawing as changed
            drawingArea.modified =
                    true;
        });
    }
}