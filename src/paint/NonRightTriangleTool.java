/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Allows the user to draw a non-right triangle.
 *
 * @author tanya
 */
public class NonRightTriangleTool {

    /**
     * Sets up the mouse controls used to draw a non-right triangle.
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

        // make sure preview does not block the mouse
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

            // start with all points at the mouse location
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

        // update the triangle while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // get current ending x and y
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // calculate width of dragged area
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            // use width to calculate triangle height
            double height =
                    width * Math.sqrt(3) / 2;

            // find left and right sides
            double leftX =
                    Math.min(
                            drawingArea.startX,
                            endX
                    );

            double rightX =
                    Math.max(
                            drawingArea.startX,
                            endX
                    );

            // find middle x position
            double middleX =
                    (leftX + rightX) / 2;

            double topY =
                    drawingArea.startY;

            double bottomY;

            // decide whether the triangle goes up or down
            if (endY >= drawingArea.startY) {

                bottomY =
                        drawingArea.startY
                        + height;

            } else {

                bottomY =
                        drawingArea.startY
                        - height;
            }

            // update the live triangle
            previewTriangle.getPoints().setAll(
                    middleX,
                    topY,

                    leftX,
                    bottomY,

                    rightX,
                    bottomY
            );
        });

        // draw the final triangle when the mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get ending x and y
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // calculate width
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            // calculate triangle height
            double height =
                    width * Math.sqrt(3) / 2;

            // find left side
            double leftX =
                    Math.min(
                            drawingArea.startX,
                            endX
                    );

            // find right side
            double rightX =
                    Math.max(
                            drawingArea.startX,
                            endX
                    );

            // find middle x position
            double middleX =
                    (leftX + rightX) / 2;

            double topY =
                    drawingArea.startY;

            double bottomY;

            if (endY >= drawingArea.startY) {

                bottomY =
                        drawingArea.startY
                        + height;

            } else {

                bottomY =
                        drawingArea.startY
                        - height;
            }

            // create the three x points
            double[] xPoints = {
                middleX,
                leftX,
                rightX
            };

            // create the three y points
            double[] yPoints = {
                topY,
                bottomY,
                bottomY
            };

            // hide the temporary preview
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