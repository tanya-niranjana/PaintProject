/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package paint;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static paint.GuiImage.currentFile;
import static paint.GuiImage.drawingArea;

/**
 * Handles the Save As feature.
 *
 * @author tanya
 */
public class SaveAsFunction {

    /**
     * Sets up the Save As menu item.
     *
     * @param saveAsItem the menu item used to save the image as a new file
     * @param stage the main Paint application window
     */
    public static void setup(MenuItem saveAsItem, Stage stage) {

        saveAsItem.setOnAction(event -> {

            if (drawingArea != null) {

                // show the different image formats that can be saved
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("Save Image As");

                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter(
                                "PNG Image",
                                "*.png"
                        ),
                        new FileChooser.ExtensionFilter(
                                "JPEG Image",
                                "*.jpg",
                                "*.jpeg"
                        ),
                        new FileChooser.ExtensionFilter(
                                "BMP Image",
                                "*.bmp"
                        )
                );

                // ask user where they want to save the file
                File newFile = fileChooser.showSaveDialog(stage);

                if (newFile != null) {

                    String fileName = newFile.getName().toLowerCase();

                    // png is the default format
                    String format = "png";

                    // find which format the user selected
                    if (fileName.endsWith(".jpg")
                            || fileName.endsWith(".jpeg")) {

                        format = "jpg";

                    } else if (fileName.endsWith(".bmp")) {

                        format = "bmp";

                    } else if (!fileName.endsWith(".png")) {

                        // add png if no file type was entered
                        newFile = new File(
                                newFile.getAbsolutePath() + ".png"
                        );
                    }

                    /*
                     * Check if the new format is different
                     * from the current image format.
                     */
                    if (currentFile != null) {

                        String oldFormat =
                                getFileFormat(currentFile);

                        // warn user if the file type is changing
                        if (!oldFormat.isEmpty()
                                && !oldFormat.equals(format)) {

                            Alert warning =
                                    new Alert(Alert.AlertType.WARNING);

                            warning.setTitle("File Format Warning");

                            warning.setHeaderText(
                                    "You are changing the image file format."
                            );

                            warning.setContentText(
                                    "Changing image formats may cause "
                                    + "quality, transparency, or other "
                                    + "image data to be lost."
                            );

                            ButtonType continueButton =
                                    new ButtonType("Continue");

                            ButtonType cancelButton =
                                    ButtonType.CANCEL;

                            warning.getButtonTypes().setAll(
                                    continueButton,
                                    cancelButton
                            );

                            Optional<ButtonType> result =
                                    warning.showAndWait();

                            // stop Save As if user does not continue
                            if (result.isEmpty()
                                    || result.get() != continueButton) {

                                return;
                            }
                        }
                    }

                    // get the full edited image
                    WritableImage editedImage =
                            drawingArea.getEditedImage();

                    // change JavaFX image into image that ImageIO can save
                    BufferedImage bufferedImage =
                            SwingFXUtils.fromFXImage(
                                    editedImage,
                                    null
                            );

                    try {

                        // save image using chosen format
                        ImageIO.write(
                                bufferedImage,
                                format,
                                newFile
                        );

                        // make new file the current file
                        currentFile = newFile;

                        // update file name shown on tab
                        ImageTabs.updateCurrentFile(newFile);

                        // mark changes as saved
                        drawingArea.markSaved();

                    } catch (IOException e) {

                        System.out.println(
                                "Error saving image"
                        );
                    }
                }
            }
        });
    }


    /**
     * Gets the file format from an image file.
     *
     * @param file the image file
     * @return the image format
     */
    private static String getFileFormat(File file) {

        String name = file.getName().toLowerCase();

        if (name.endsWith(".png")) {
            return "png";

        } else if (name.endsWith(".jpg")
                || name.endsWith(".jpeg")) {

            return "jpg";

        } else if (name.endsWith(".bmp")) {
            return "bmp";
        }

        return "";
    }
}