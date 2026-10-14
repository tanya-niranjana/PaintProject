/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Allows the user to draw a triangle.
 * 
 * @author tanya
 */
public class TriangleTool {
    
    /**
     * Sets up the mouse controls used to draw a triangle.
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
        Polygon previewTriangle = new Polygon();

        // triangle should only have an outline
        previewTriangle.setFill(Color.TRANSPARENT);

        // make sure the preview does not block the mouse
        previewTriangle.setMouseTransparent(true);

        // hide preview until drawing begins
        previewTriangle.setVisible(false);

        // add preview above the canvas
        drawingArea.drawingPane.getChildren().add(previewTriangle);

        // save starting point when mouse is pressed
        drawingArea.canvas.setOnMousePressed(event -> {

            // save the current image before drawing the new triangle
            UndoRedo.saveState(drawingArea);

            // starting location of the triangle
            drawingArea.startX = event.getX();

            drawingArea.startY = event.getY();

            // apply selected line width and color
            previewTriangle.setStrokeWidth(drawingArea.lineWidth);

            previewTriangle.setStroke(drawingArea.lineColor);

            // copy dashed outline setting if turned on
            double[] dashes = drawingArea.gc.getLineDashes();

            previewTriangle.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewTriangle.getStrokeDashArray().add(dash);
                }
            }

            // start triangle with all points at the mouse location
            previewTriangle.getPoints().setAll(
                    drawingArea.startX,
                    drawingArea.startY,

                    drawingArea.startX,
                    drawingArea.startY,

                    drawingArea.startX,
                    drawingArea.startY
            );

            // show live preview
            previewTriangle.setVisible(
                    true
            );
        });

        // update the triangle while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(event -> {

            // current ending location
            double endX = event.getX();

            double endY = event.getY();

            // find middle position for the top point
            double middleX = (drawingArea.startX + endX) / 2;

            // update the three points of the live triangle
            previewTriangle.getPoints().setAll(
                    middleX,
                    drawingArea.startY,

                    drawingArea.startX,
                    endY,

                    endX,
                    endY
            );
        });

        // draw the final triangle when mouse is released
        drawingArea.canvas.setOnMouseReleased(event -> {

            // ending location
            double endX = event.getX();

            double endY = event.getY();

            // find middle position for the top point
            double middleX = (drawingArea.startX + endX) / 2;

            // create x points for triangle
            double[] xPoints = {
                middleX,
                drawingArea.startX,
                endX
            };

            // create y points for triangle
            double[] yPoints = {
                drawingArea.startY,
                endY,
                endY
            };

            // hide temporary preview
            previewTriangle.setVisible(false);

            // apply line width and color
            drawingArea.gc.setLineWidth(drawingArea.lineWidth);

            drawingArea.gc.setStroke(drawingArea.lineColor);

            // draw final triangle
            drawingArea.gc.strokePolygon(
                    xPoints,
                    yPoints,
                    3
            );

            // mark drawing as changed
            drawingArea.modified = true;
        });
    }
}