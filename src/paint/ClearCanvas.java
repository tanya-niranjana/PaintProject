/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;

/**
 * Handles clearing everything from the current drawing area.
 *
 * @author tanya
 */
public class ClearCanvas {

    /**
     * Sets up the Clear Canvas menu item.
     * Asks the user for confirmation before clearing the image.
     *
     * @param clearItem the menu item used to clear the canvas
     */
    public static void setup(
            MenuItem clearItem) {

        // clear the canvas when the menu item is selected
        clearItem.setOnAction(event -> {

            // only clear if a drawing area is open
            if (GuiImage.drawingArea != null) {

                // create a confirmation message
                Alert alert =
                        new Alert(
                                Alert.AlertType.CONFIRMATION
                        );

                alert.setTitle(
                        "Clear Canvas"
                );

                alert.setHeaderText(
                        "Are you sure you want to clear the canvas?"
                );

                alert.setContentText(
                        "This will remove everything from the current image."
                );

                // wait for the user to choose OK or Cancel
                alert.showAndWait().ifPresent(
                        response -> {

                    // only clear if the user chooses OK
                    if (response == ButtonType.OK) {

                        // save current image so Clear can be undone
                        UndoRedo.saveState(
                                GuiImage.drawingArea
                        );

                        // remove the original image
                        GuiImage.drawingArea.imageView
                                .setImage(null);

                        // clear everything drawn on the canvas
                        GuiImage.drawingArea.gc.clearRect(
                                0,
                                0,
                                GuiImage.drawingArea.canvas.getWidth(),
                                GuiImage.drawingArea.canvas.getHeight()
                        );

                        // mark the image as changed
                        GuiImage.drawingArea.modified = true;
                    }
                });
            }
        });
    }
}