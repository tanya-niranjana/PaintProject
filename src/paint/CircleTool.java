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
    public static void setupDrawing(DrawingArea drawingArea) {

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // temporary circle used for live preview
        Circle previewCircle = new Circle();

        previewCircle.setFill(Color.TRANSPARENT);
        previewCircle.setMouseTransparent(true);
        previewCircle.setVisible(false);

        drawingArea.drawingPane.getChildren().add(previewCircle);

        // start drawing the circle
        drawingArea.canvas.setOnMousePressed(event -> {

            UndoRedo.saveState(drawingArea);

            drawingArea.startX = event.getX();
            drawingArea.startY = event.getY();

            previewCircle.setStrokeWidth(drawingArea.lineWidth);
            previewCircle.setStroke(drawingArea.lineColor);

            // copy dashed outline setting
            double[] dashes = drawingArea.gc.getLineDashes();

            previewCircle.getStrokeDashArray().clear();

            if (dashes != null) {
                for (double dash : dashes) {
                    previewCircle.getStrokeDashArray().add(dash);
                }
            }

            previewCircle.setCenterX(drawingArea.startX);
            previewCircle.setCenterY(drawingArea.startY);
            previewCircle.setRadius(0);
            previewCircle.setVisible(true);
        });

        // update the circle while dragging
        drawingArea.canvas.setOnMouseDragged(event -> {

            double endX = event.getX();
            double endY = event.getY();

            double width = Math.abs(endX - drawingArea.startX);
            double height = Math.abs(endY - drawingArea.startY);

            // use smaller value so it stays a circle
            double size = Math.min(width, height);

            double x = drawingArea.startX;
            double y = drawingArea.startY;

            if (endX < drawingArea.startX) {
                x = drawingArea.startX - size;
            }

            if (endY < drawingArea.startY) {
                y = drawingArea.startY - size;
            }

            previewCircle.setCenterX(x + size / 2);
            previewCircle.setCenterY(y + size / 2);
            previewCircle.setRadius(size / 2);
        });

        // draw the final circle
        drawingArea.canvas.setOnMouseReleased(event -> {

            double endX = event.getX();
            double endY = event.getY();

            double width = Math.abs(endX - drawingArea.startX);
            double height = Math.abs(endY - drawingArea.startY);

            double size = Math.min(width, height);

            double x = drawingArea.startX;
            double y = drawingArea.startY;

            if (endX < drawingArea.startX) {
                x = drawingArea.startX - size;
            }

            if (endY < drawingArea.startY) {
                y = drawingArea.startY - size;
            }

            previewCircle.setVisible(false);

            drawingArea.gc.setLineWidth(drawingArea.lineWidth);
            drawingArea.gc.setStroke(drawingArea.lineColor);

            drawingArea.gc.strokeOval(
                    x,
                    y,
                    size,
                    size
            );

            drawingArea.modified = true;
        });
    }
}