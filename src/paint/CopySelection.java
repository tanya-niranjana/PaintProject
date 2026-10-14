/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * Handles copying the currently selected part of the image.
 *
 * @author tanya
 */
public class CopySelection {

    /**
     * Sets up the Copy menu item.
     *
     * @param copyItem the menu item used to copy the selected area
     */
    public static void setup(
            MenuItem copyItem) {

        // copy the selected area when Copy is chosen
        copyItem.setOnAction(event -> {

            DrawingArea drawingArea =
                    GuiImage.drawingArea;

            // make sure a drawing area is open
            if (drawingArea == null) {
                return;
            }

            // make sure the user selected something first
            if (!drawingArea.hasSelection) {

                Alert alert =
                        new Alert(
                                Alert.AlertType.INFORMATION
                        );

                alert.setTitle("Copy Selection");

                alert.setHeaderText("No area is selected.");

                alert.setContentText("Use the Select tool before copying.");

                alert.showAndWait();

                return;
            }

            // get one image containing the original picture and drawings
            WritableImage fullImage = drawingArea.getEditedImage();

            // get the selected starting location
            int x = (int) drawingArea.selectionX;

            int y = (int) drawingArea.selectionY;

            // get the selected width and height
            int width = (int) drawingArea.selectionWidth;

            int height = (int) drawingArea.selectionHeight;

            // keep the selection inside the image
            width =Math.min(width,(int) fullImage.getWidth() - x);

            height =Math.min(height,(int) fullImage.getHeight() - y);

            // make sure the selected area is valid
            if (width <= 0 || height <= 0) {
                return;
            }

            // read the pixels from the entire image
            PixelReader pixelReader = fullImage.getPixelReader();

            // copy only the selected pixels
            drawingArea.copiedImage =
                    new WritableImage(
                            pixelReader,
                            x,
                            y,
                            width,
                            height
                    );
        });
    }
}
