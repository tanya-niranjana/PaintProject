/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

/**
 * Allows the user to select part of the image.
 * A dashed box shows the selected area.
 *
 * @author tanya
 */
public class SelectionTool {

    /**
     * Sets up the mouse controls used to select an area
     * of the current image.
     *
     * @param drawingArea the drawing area where the selection is made
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // turn off mouse controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseClicked(null);
        drawingArea.canvas.setOnMouseDragged(null);

        // start the selection when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save the starting location
            drawingArea.startX =
                    event.getX();

            drawingArea.startY =
                    event.getY();

            // no completed selection yet
            drawingArea.hasSelection =
                    false;

            // start the selection box at the mouse location
            drawingArea.selectionBox.setX(
                    drawingArea.startX
            );

            drawingArea.selectionBox.setY(
                    drawingArea.startY
            );

            drawingArea.selectionBox.setWidth(
                    0
            );

            drawingArea.selectionBox.setHeight(
                    0
            );

            // show the selection box
            drawingArea.selectionBox.setVisible(
                    true
            );
        });

        // update the selection box while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // get the current mouse location
            double endX =
                    event.getX();

            double endY =
                    event.getY();

            // find the top left corner of the selection
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

            // calculate the selection width and height
            double width =
                    Math.abs(
                            endX - drawingArea.startX
                    );

            double height =
                    Math.abs(
                            endY - drawingArea.startY
                    );

            // update the selection box while the user drags
            drawingArea.selectionBox.setX(
                    x
            );

            drawingArea.selectionBox.setY(
                    y
            );

            drawingArea.selectionBox.setWidth(
                    width
            );

            drawingArea.selectionBox.setHeight(
                    height
            );
        });

        // save the selected area when the mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // save the final selection location
            drawingArea.selectionX =
                    drawingArea.selectionBox.getX();

            drawingArea.selectionY =
                    drawingArea.selectionBox.getY();

            drawingArea.selectionWidth =
                    drawingArea.selectionBox.getWidth();

            drawingArea.selectionHeight =
                    drawingArea.selectionBox.getHeight();

            // only count it as a selection if it has a size
            if (drawingArea.selectionWidth > 0
                    && drawingArea.selectionHeight > 0) {

                drawingArea.hasSelection =
                        true;

            } else {

                drawingArea.hasSelection =
                        false;

                drawingArea.selectionBox.setVisible(
                        false
                );
            }
        });
    }
}
