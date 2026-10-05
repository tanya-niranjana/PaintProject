/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Allows user to draw a diamond.
 * 
 * @author tanya
 */
public class DiamondTool {
    
    /**
     * Sets up the mouse controls used to draw a diamond.
     * The diamond is shown while the user drags the mouse.
     *
     * @param drawingArea the drawing area where the diamond is drawn
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {
         
        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create temporary diamond for the live preview
        Polygon previewDiamond =
                new Polygon();

        // diamond should only have an outline
        previewDiamond.setFill(
                Color.TRANSPARENT
        );

        // make sure preview does not block the mouse
        previewDiamond.setMouseTransparent(
                true
        );

        // hide preview until drawing begins
        previewDiamond.setVisible(
                false
        );

        // add preview above the canvas
        drawingArea.drawingPane.getChildren().add(
                previewDiamond
        );
        
        // save the starting point when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save the current image before drawing the new diamond
            UndoRedo.saveState(
                    drawingArea
            );         
                    
            // starting x
            drawingArea.startX =
                    event.getX();

            // starting y
            drawingArea.startY =
                    event.getY();

            // apply proper line width
            previewDiamond.setStrokeWidth(
                    drawingArea.lineWidth
            );

            // apply proper line color
            previewDiamond.setStroke(
                    drawingArea.lineColor
            );

            // copy dashed outline setting if turned on
            double[] dashes =
                    drawingArea.gc.getLineDashes();

            previewDiamond.getStrokeDashArray().clear();

            if (dashes != null) {

                for (double dash : dashes) {

                    previewDiamond.getStrokeDashArray().add(
                            dash
                    );
                }
            }

            // start with all points at the mouse location
            previewDiamond.getPoints().setAll(
                    drawingArea.startX,
                    drawingArea.startY,

                    drawingArea.startX,
                    drawingArea.startY,

                    drawingArea.startX,
                    drawingArea.startY,

                    drawingArea.startX,
                    drawingArea.startY
            );

            // show live preview
            previewDiamond.setVisible(
                    true
            );
        });

        // update the diamond while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // get current ending x and y
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find middle x and y
            double middleX =
                    (drawingArea.startX + endX) / 2;

            double middleY =
                    (drawingArea.startY + endY) / 2;

            // update the four points of the live diamond
            previewDiamond.getPoints().setAll(
                    middleX,
                    drawingArea.startY,

                    endX,
                    middleY,

                    middleX,
                    endY,

                    drawingArea.startX,
                    middleY
            );
        });

        // draw diamond when mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get ending x and y
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find middle x and y
            double middleX =
                    (drawingArea.startX + endX) / 2;

            double middleY =
                    (drawingArea.startY + endY) / 2;

            // create x points of diamond
            double[] xPoints = {
                middleX,
                endX,
                middleX,
                drawingArea.startX
            };

            // create y points of diamond
            double[] yPoints = {
                drawingArea.startY,
                middleY,
                endY,
                middleY
            };

            // hide temporary preview
            previewDiamond.setVisible(
                    false
            );
            
            // apply the proper line width
            drawingArea.gc.setLineWidth(
                    drawingArea.lineWidth
            );

            // apply the proper line color
            drawingArea.gc.setStroke(
                    drawingArea.lineColor
            );

            // draw diamond using 4 points
            drawingArea.gc.strokePolygon(
                    xPoints,
                    yPoints,
                    4
            );

            // drawing has been modified
            drawingArea.modified =
                    true;
        });
    }
}