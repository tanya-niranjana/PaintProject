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
 * Allows the user to draw a regular polygon
 * with a chosen number of sides.
 *
 * @author tanya
 */
public class RegularPolygonTool {

    /**
     * Asks the user for the number of sides and sets up
     * the mouse controls used to draw the polygon.
     * The polygon is shown while the user drags the mouse.
     *
     * @param drawingArea the drawing area where the polygon is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // ask the user how many sides the polygon should have
        TextInputDialog dialog = new TextInputDialog("5");

        dialog.setTitle("Regular Polygon"
        );

        dialog.setHeaderText("Enter the number of sides");

        dialog.setContentText("Sides:");

        // wait for the user to enter a value
        Optional<String> result = dialog.showAndWait();

        // stop if the user cancels
        if (result.isEmpty()) {
            return;
        }

        int sides;

        try {

            // turn the user's answer into a number
            sides =Integer.parseInt(result.get());

        } catch (NumberFormatException e) {

            // show an error if the user did not enter a number
            Alert alert =new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Invalid Number");

            alert.setHeaderText("Please enter a whole number.");

            alert.showAndWait();

            return;
        }

        // a polygon must have at least 3 sides
        if (sides < 3) {

            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Polygon");
            alert.setHeaderText("A polygon must have at least 3 sides.");
            alert.showAndWait();

            return;
        }

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create temporary polygon for the live preview
        Polygon previewPolygon = new Polygon();

        // polygon should only have an outline
        previewPolygon.setFill(Color.TRANSPARENT);

        // make sure preview does not block the mouse
        previewPolygon.setMouseTransparent(true);

        // hide preview until drawing begins
        previewPolygon.setVisible(false);

        // add preview above the canvas
        drawingArea.drawingPane.getChildren().add(previewPolygon);

        // save the center point when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(event -> {

            // save current image before drawing the polygon
            UndoRedo.saveState(drawingArea);

            // starting point becomes the center of the polygon
            drawingArea.startX = event.getX();

            drawingArea.startY = event.getY();

            // apply selected line width
            previewPolygon.setStrokeWidth(drawingArea.lineWidth);

            // apply selected line color
            previewPolygon.setStroke(drawingArea.lineColor);

            // copy dashed outline setting if turned on
            double[] dashes = drawingArea.gc.getLineDashes();

            previewPolygon.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewPolygon.getStrokeDashArray().add(dash );
                }
            }

            // remove any old preview points
            previewPolygon.getPoints().clear();

            // show the live preview
            previewPolygon.setVisible(true
            );
        });

        // update polygon while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(event -> {

            // get current mouse location
            double endX = event.getX();

            double endY = event.getY();

            // calculate distance from center
            double radius = Math.sqrt(Math.pow(endX - drawingArea.startX,2)
                            +
                            Math.pow(endY - drawingArea.startY,2)
                    );

            // remove old preview points
            previewPolygon.getPoints().clear();

            // calculate all polygon points
            for (int i = 0; i < sides; i++) {

                // evenly space each point around a circle
                double angle = -Math.PI / 2 + i * (2 * Math.PI / sides);

                // calculate x location
                double x = drawingArea.startX + radius * Math.cos(angle);

                // calculate y location
                double y = drawingArea.startY+ radius * Math.sin(angle);

                // add the point to the live preview
                previewPolygon.getPoints().addAll(x,y);
            }
        });

        // draw the polygon when the mouse is released
        drawingArea.canvas.setOnMouseReleased(event -> {

            // get ending location
            double endX = event.getX();

            double endY = event.getY();

            // calculate the distance from the center
            double radius = Math.sqrt(Math.pow(endX - drawingArea.startX,2)
                            +
                            Math.pow(endY - drawingArea.startY,2)
                    );

            // create arrays to hold each polygon point
            double[] xPoints = new double[sides];

            double[] yPoints = new double[sides];

            // calculate all points around the polygon
            for (int i = 0; i < sides; i++) {

                // evenly space each point around a circle
                double angle = -Math.PI / 2+ i * (2 * Math.PI / sides);

                // calculate x location
                xPoints[i] =
                        drawingArea.startX
                        + radius
                        * Math.cos(angle);

                // calculate y location
                yPoints[i] =
                        drawingArea.startY
                        + radius
                        * Math.sin(angle);
            }

            // hide temporary preview
            previewPolygon.setVisible(false);

            // apply selected line width
            drawingArea.gc.setLineWidth(drawingArea.lineWidth);

            // apply selected line color
            drawingArea.gc.setStroke(drawingArea.lineColor);

            // draw final polygon
            drawingArea.gc.strokePolygon(
                    xPoints,
                    yPoints,
                    sides
            );

            // mark drawing as changed
            drawingArea.modified = true;
        });
    }
}