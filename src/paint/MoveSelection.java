/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Allows the user to move a selected part of the image.
 *
 * @author tanya
 */
public class MoveSelection {

    /**
     * Sets up the mouse controls used to move
     * the currently selected area.
     *
     * @param drawingArea the drawing area containing the selection
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // make sure an area has been selected first
        if (!drawingArea.hasSelection) {

            Alert alert =
                    new Alert(
                            Alert.AlertType.INFORMATION
                    );

            alert.setTitle(
                    "Move Selection"
            );

            alert.setHeaderText(
                    "No area is selected."
            );

            alert.setContentText(
                    "Use the Select tool before moving an area."
            );

            alert.showAndWait();

            return;
        }

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseClicked(null);
        drawingArea.canvas.setOnMouseDragged(null);

        // temporary image used while the selection is being moved
        ImageView movePreview =
                new ImageView();

        // stores where inside the selection the user clicked
        double[] offsetX = new double[1];
        double[] offsetY = new double[1];

        // keeps track of whether the user is currently moving the selection
        boolean[] moving =
                new boolean[1];

        // start moving when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            double mouseX =
                    event.getX();

            double mouseY =
                    event.getY();

            // check if the user clicked inside the selected area
            boolean insideSelection =
                    mouseX >= drawingArea.selectionX
                    && mouseX <= drawingArea.selectionX
                            + drawingArea.selectionWidth
                    && mouseY >= drawingArea.selectionY
                    && mouseY <= drawingArea.selectionY
                            + drawingArea.selectionHeight;

            // only move if the user clicked inside the selection
            if (!insideSelection) {
                moving[0] = false;
                return;
            }

            moving[0] = true;

            // save the current image before moving anything
            UndoRedo.saveState(
                    drawingArea
            );

            // get the complete current image
            WritableImage fullImage =
                    drawingArea.getEditedImage();

            // get selection location
            int x =
                    (int) drawingArea.selectionX;

            int y =
                    (int) drawingArea.selectionY;

            int width =
                    (int) drawingArea.selectionWidth;

            int height =
                    (int) drawingArea.selectionHeight;

            // keep selection inside the image
            width =
                    Math.min(
                            width,
                            (int) fullImage.getWidth() - x
                    );

            height =
                    Math.min(
                            height,
                            (int) fullImage.getHeight() - y
                    );

            // stop if the selected area is invalid
            if (width <= 0 || height <= 0) {
                moving[0] = false;
                return;
            }

            // read pixels from the current image
            PixelReader reader =
                    fullImage.getPixelReader();

            // copy the selected part of the image
            WritableImage selectedImage =
                    new WritableImage(
                            reader,
                            x,
                            y,
                            width,
                            height
                    );

            // also store the selected image for copy and paste
            drawingArea.copiedImage =
                    selectedImage;

            // make a new background image
            WritableImage backgroundImage =
                    new WritableImage(
                            reader,
                            (int) fullImage.getWidth(),
                            (int) fullImage.getHeight()
                    );

            // get the tool used to change pixels
            PixelWriter writer =
                    backgroundImage.getPixelWriter();

            // erase the original selected area
            for (int row = y;
                    row < y + height;
                    row++) {

                for (int column = x;
                        column < x + width;
                        column++) {

                    writer.setColor(
                            column,
                            row,
                            Color.WHITE
                    );
                }
            }

            // put the new background into the image display
            drawingArea.imageView.setImage(
                    backgroundImage
            );

            // clear the canvas because its drawings
            // are now part of the background image
            drawingArea.gc.clearRect(
                    0,
                    0,
                    drawingArea.canvas.getWidth(),
                    drawingArea.canvas.getHeight()
            );

            // calculate where inside the selection the user clicked
            offsetX[0] =
                    mouseX
                    - drawingArea.selectionX;

            offsetY[0] =
                    mouseY
                    - drawingArea.selectionY;

            // show the selected image as a temporary preview
            movePreview.setImage(
                    selectedImage
            );

            movePreview.setLayoutX(
                    drawingArea.selectionX
            );

            movePreview.setLayoutY(
                    drawingArea.selectionY
            );

            // add the preview on top of the image
            drawingArea.drawingPane.getChildren()
                    .add(
                            movePreview
                    );
        });

        // move the selected image while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            if (!moving[0]) {
                return;
            }

            // calculate the new location
            double newX =
                    event.getX()
                    - offsetX[0];

            double newY =
                    event.getY()
                    - offsetY[0];

            // move the image preview
            movePreview.setLayoutX(
                    newX
            );

            movePreview.setLayoutY(
                    newY
            );

            // move the selection box with the image
            drawingArea.selectionBox.setX(
                    newX
            );

            drawingArea.selectionBox.setY(
                    newY
            );
        });

        // place the image when the mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            if (!moving[0]) {
                return;
            }

            // get the final location
            double newX =
                    movePreview.getLayoutX();

            double newY =
                    movePreview.getLayoutY();

            // draw the moved image onto the canvas
            drawingArea.gc.drawImage(
                    movePreview.getImage(),
                    newX,
                    newY
            );

            // remove the temporary moving image
            drawingArea.drawingPane.getChildren()
                    .remove(
                            movePreview
                    );

            // save the new selection location
            drawingArea.selectionX =
                    newX;

            drawingArea.selectionY =
                    newY;

            // keep the selection box around the moved image
            drawingArea.selectionBox.setX(
                    newX
            );

            drawingArea.selectionBox.setY(
                    newY
            );

            drawingArea.selectionBox.setVisible(
                    true
            );

            // mark the image as changed
            drawingArea.modified =
                    true;

            moving[0] =
                    false;
        });
    }
}
