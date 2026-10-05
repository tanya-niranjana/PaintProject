/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Allows the user to draw a five point star.
 *
 * @author tanya
 */
public class StarTool {

    /**
     * Sets up the mouse controls used to draw a star.
     * The star is shown while the user drags the mouse.
     *
     * @param drawingArea the drawing area where the star is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create temporary star for the live preview
        Polygon previewStar =
                new Polygon();

        // star should only have an outline
        previewStar.setFill(
                Color.TRANSPARENT
        );

        // make sure the preview does not block the mouse
        previewStar.setMouseTransparent(
                true
        );

        // hide preview until drawing begins
        previewStar.setVisible(
                false
        );

        // add preview above the canvas
        drawingArea.drawingPane.getChildren().add(
                previewStar
        );

        // save the starting point when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save current image before drawing the star
            UndoRedo.saveState(
                    drawingArea
            );

            // save starting x and y
            drawingArea.startX =
                    event.getX();

            drawingArea.startY =
                    event.getY();

            // apply selected line width
            previewStar.setStrokeWidth(
                    drawingArea.lineWidth
            );

            // apply selected line color
            previewStar.setStroke(
                    drawingArea.lineColor
            );

            // copy dashed outline setting if turned on
            double[] dashes =
                    drawingArea.gc.getLineDashes();

            previewStar.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewStar.getStrokeDashArray().add(
                            dash
                    );
                }
            }

            // show the live preview
            previewStar.setVisible(
                    true
            );
        });

        // update the star while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // get ending x and y
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find the center of the dragged area
            double centerX =
                    (drawingArea.startX + endX) / 2;

            double centerY =
                    (drawingArea.startY + endY) / 2;

            // calculate width and height
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            double height =
                    Math.abs(
                            endY - drawingArea.startY
                    );

            // use smaller size so star stays even
            double outerRadius =
                    Math.min(
                            width,
                            height
                    ) / 2;

            // inner points are smaller than outer points
            double innerRadius =
                    outerRadius * 0.4;

            // remove the old preview points
            previewStar.getPoints().clear();

            // create the 10 points of the star
            for (int i = 0; i < 10; i++) {

                double radius;

                // alternate between outside and inside points
                if (i % 2 == 0) {

                    radius =
                            outerRadius;

                } else {

                    radius =
                            innerRadius;
                }

                // calculate angle for this point
                double angle =
                        Math.toRadians(
                                -90 + i * 36
                        );

                // calculate x position
                double x =
                        centerX
                        + radius
                        * Math.cos(angle);

                // calculate y position
                double y =
                        centerY
                        + radius
                        * Math.sin(angle);

                // add point to live preview
                previewStar.getPoints().addAll(
                        x,
                        y
                );
            }
        });

        // draw the final star when the mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get ending x and y
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find center of dragged area
            double centerX =
                    (drawingArea.startX + endX) / 2;

            double centerY =
                    (drawingArea.startY + endY) / 2;

            // calculate width and height
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            double height =
                    Math.abs(
                            endY - drawingArea.startY
                    );

            // use smaller size so star stays even
            double outerRadius =
                    Math.min(
                            width,
                            height
                    ) / 2;

            // inner points are smaller than outer points
            double innerRadius =
                    outerRadius * 0.4;

            // star uses 10 points total
            double[] xPoints =
                    new double[10];

            double[] yPoints =
                    new double[10];

            // create the points around the star
            for (int i = 0; i < 10; i++) {

                double radius;

                if (i % 2 == 0) {

                    radius =
                            outerRadius;

                } else {

                    radius =
                            innerRadius;
                }

                // calculate angle for this point
                double angle =
                        Math.toRadians(
                                -90 + i * 36
                        );

                // calculate x position
                xPoints[i] =
                        centerX
                        + radius
                        * Math.cos(angle);

                // calculate y position
                yPoints[i] =
                        centerY
                        + radius
                        * Math.sin(angle);
            }

            // hide temporary preview
            previewStar.setVisible(
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

            // draw the final star
            drawingArea.gc.strokePolygon(
                    xPoints,
                    yPoints,
                    10
            );

            // mark drawing as changed
            drawingArea.modified =
                    true;
        });
    }
}