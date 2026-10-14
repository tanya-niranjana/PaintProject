/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Allows the user to draw a star with different amounts of points.
 *
 * @author tanya
 */
public class StarTool {

    /**
     * Sets up the mouse controls used to draw a star.
     *
     * @param drawingArea the drawing area where the star is drawn
     */
    public static void setupDrawing(DrawingArea drawingArea) {

        // ask user how many points they want
        TextInputDialog dialog = new TextInputDialog("5");

        dialog.setTitle("Star");
        dialog.setHeaderText("Enter the number of points");
        dialog.setContentText("Points:");

        Optional<String> result = dialog.showAndWait();

        // stop if user cancels
        if (result.isEmpty()) {
            return;
        }

        int starPoints;

        try {
            // change user input into a number
            starPoints = Integer.parseInt(result.get());

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Number");
            alert.setHeaderText("Please enter a whole number.");
            alert.showAndWait();

            return;
        }

        // star must have at least 4 points
        if (starPoints < 4) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Star");
            alert.setHeaderText("A star must have at least 4 points.");
            alert.showAndWait();

            return;
        }

        final int points = starPoints;

        // turn off controls from previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // temporary star for live preview
        Polygon previewStar = new Polygon();
        previewStar.setFill(Color.TRANSPARENT);
        previewStar.setMouseTransparent(true);
        previewStar.setVisible(false);

        drawingArea.drawingPane.getChildren().add(previewStar);

        // save starting point when mouse is pressed
        drawingArea.canvas.setOnMousePressed(event -> {

            // save image before drawing
            UndoRedo.saveState(drawingArea);

            drawingArea.startX = event.getX();
            drawingArea.startY = event.getY();

            // use selected line settings
            previewStar.setStrokeWidth(drawingArea.lineWidth);
            previewStar.setStroke(drawingArea.lineColor);

            // copy dashed setting
            double[] dashes = drawingArea.gc.getLineDashes();

            previewStar.getStrokeDashArray().clear();

            if (dashes != null) {
                for (double dash : dashes) {
                    previewStar.getStrokeDashArray().add(dash);
                }
            }

            previewStar.setVisible(true);
        });

        // update star while mouse is dragged
        drawingArea.canvas.setOnMouseDragged(event -> {

            double endX = event.getX();
            double endY = event.getY();

            // find center of dragged area
            double centerX = (drawingArea.startX + endX) / 2;
            double centerY = (drawingArea.startY + endY) / 2;

            // get size of dragged area
            double width = Math.abs(endX - drawingArea.startX);
            double height = Math.abs(endY - drawingArea.startY);

            double outerRadius = Math.min(width, height) / 2;

            // remove old preview points
            previewStar.getPoints().clear();

            // 4 points makes a diamond
            if (points == 4) {

                previewStar.getPoints().addAll(
                        centerX, centerY - outerRadius,
                        centerX + outerRadius, centerY,
                        centerX, centerY + outerRadius,
                        centerX - outerRadius, centerY
                );

            } else {

                double innerRadius = outerRadius * 0.4;

                // create outside and inside points
                for (int i = 0; i < points * 2; i++) {

                    double radius;

                    if (i % 2 == 0) {
                        radius = outerRadius;
                    } else {
                        radius = innerRadius;
                    }

                    double angle = Math.toRadians(
                            -90 + i * (180.0 / points)
                    );

                    double x = centerX + radius * Math.cos(angle);
                    double y = centerY + radius * Math.sin(angle);

                    previewStar.getPoints().addAll(x, y);
                }
            }
        });

        // draw final star when mouse is released
        drawingArea.canvas.setOnMouseReleased(event -> {

            double endX = event.getX();
            double endY = event.getY();

            // find center of dragged area
            double centerX = (drawingArea.startX + endX) / 2;
            double centerY = (drawingArea.startY + endY) / 2;

            // get final size
            double width = Math.abs(endX - drawingArea.startX);
            double height = Math.abs(endY - drawingArea.startY);

            double outerRadius = Math.min(width, height) / 2;

            double[] xPoints;
            double[] yPoints;
            int totalPoints;

            // make diamond
            if (points == 4) {

                totalPoints = 4;

                xPoints = new double[] {
                    centerX,
                    centerX + outerRadius,
                    centerX,
                    centerX - outerRadius
                };

                yPoints = new double[] {
                    centerY - outerRadius,
                    centerY,
                    centerY + outerRadius,
                    centerY
                };

            } else {

                totalPoints = points * 2;

                xPoints = new double[totalPoints];
                yPoints = new double[totalPoints];

                double innerRadius = outerRadius * 0.4;

                // create all star points
                for (int i = 0; i < totalPoints; i++) {

                    double radius;

                    if (i % 2 == 0) {
                        radius = outerRadius;
                    } else {
                        radius = innerRadius;
                    }

                    double angle = Math.toRadians(
                            -90 + i * (180.0 / points)
                    );

                    xPoints[i] = centerX + radius * Math.cos(angle);
                    yPoints[i] = centerY + radius * Math.sin(angle);
                }
            }

            // hide preview
            previewStar.setVisible(false);

            // use selected line settings
            drawingArea.gc.setLineWidth(drawingArea.lineWidth);
            drawingArea.gc.setStroke(drawingArea.lineColor);

            // draw star
            drawingArea.gc.strokePolygon(
                    xPoints,
                    yPoints,
                    totalPoints
            );

            // mark image as changed
            drawingArea.modified = true;
        });
    }
}