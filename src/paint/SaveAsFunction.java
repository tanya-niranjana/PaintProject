/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static paint.GuiImage.currentFile;
import static paint.GuiImage.imageData;
import static paint.GuiImage.drawingArea;

/**
 *
 * @author tanya
 */
public class SaveAsFunction {
     public static void setup(MenuItem saveAsItem, Stage stage) {

        // SAVE AS
        // Let the user save the edited image as a new file
        saveAsItem.setOnAction(event -> {

            if (imageData != null) {
                
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

                // asks the user where they want to save the new file
                File newFile = fileChooser.showSaveDialog(stage);

                if (newFile != null) {

                    // get the full edited image
                    WritableImage editedImage =
                            drawingArea.getEditedImage();

                    BufferedImage bufferedImage =
                            SwingFXUtils.fromFXImage(
                                    editedImage,
                                    null
                            );

                    String fileName =
                            newFile.getName().toLowerCase();
                    // determine the file type based on the filename
                    String format = "png";

                    if (fileName.endsWith(".jpg")
                            || fileName.endsWith(".jpeg")) {

                        format = "jpg";

                    } else if (fileName.endsWith(".bmp")) {

                        format = "bmp";

                    } else if (!fileName.endsWith(".png")) {

                        newFile = new File(
                                newFile.getAbsolutePath()
                                        + ".png"
                        );
                    }

                    try {

                        // Save the edited image to the new file
                        ImageIO.write(
                                bufferedImage,
                                format,
                                newFile
                        );

                        // make the new file the current file 
                        currentFile = newFile;

                        // mark all changes as saved 
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
}
