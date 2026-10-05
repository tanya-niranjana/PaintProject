/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

/**
 * Handles pasting a copied part of the image.
 *
 * @author tanya
 */
public class PasteSelection {

    /**
     * Sets up the mouse so the user can place and move
     * the copied image before it is pasted.
     *
     * @param drawingArea the drawing area where the copied image is pasted
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // make sure something has been copied first
        if (drawingArea.copiedImage == null) {

            Alert alert =
                    new Alert(
                            Alert.AlertType.INFORMATION
                    );

            alert.setTitle(
                    "Paste"
            );

            alert.setHeaderText(
                    "Nothing has been copied."
            );

            alert.setContentText(
                    "Select and copy part of the image first."
            );

            alert.showAndWait();

            return;
        }

        // turn off mouse controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // create a temporary image used for the live paste preview
        ImageView pastePreview =
                new ImageView(
                        drawingArea.copiedImage
                );

        // preview should not block mouse controls
        pastePreview.setMouseTransparent(
                true
        );

        // make preview slightly transparent while moving
        pastePreview.setOpacity(
                0.7
        );

        // hide old selection box
        drawingArea.selectionBox.setVisible(
                false
        );

        // start the paste when the mouse is pressed
        drawingArea.canvas.setOnMousePressed(
                event -> {

            // save current image before changing it
            UndoRedo.saveState(
                    drawingArea
            );

            // place the preview where the mouse was pressed
            pastePreview.setLayoutX(
                    event.getX()
            );

            pastePreview.setLayoutY(
                    event.getY()
            );

            // add live preview above the image
            drawingArea.drawingPane
                    .getChildren()
                    .add(
                            pastePreview
                    );
        });

        // move the copied image while the mouse is dragged
        drawingArea.canvas.setOnMouseDragged(
                event -> {

            // update preview location
            pastePreview.setLayoutX(
                    event.getX()
            );

            pastePreview.setLayoutY(
                    event.getY()
            );
        });

        // finish the paste when the mouse is released
        drawingArea.canvas.setOnMouseReleased(
                event -> {

            // get final paste location
            double pasteX =
                    pastePreview.getLayoutX();

            double pasteY =
                    pastePreview.getLayoutY();

            // remove temporary preview first
            drawingArea.drawingPane
                    .getChildren()
                    .remove(
                            pastePreview
                    );

            // draw copied image permanently onto the canvas
            drawingArea.gc.drawImage(
                    drawingArea.copiedImage,
                    pasteX,
                    pasteY
            );

            // mark image as changed
            drawingArea.modified =
                    true;

            // old selection is no longer active
            drawingArea.hasSelection =
                    false;
        });
    }
}