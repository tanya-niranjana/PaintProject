/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.io.IOException;
import java.nio.file.Files;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static paint.GuiImage.currentFile;
import static paint.GuiImage.imageData;
import static paint.GuiImage.drawingArea;

/**
 * handles opening image files in the paint program
 * 
 * 
 * @author tanya
 */
public class OpenImage {
    
    /**
     * Sets up the Open menu item so the user can select
     * and open an image file.
     *
     * @param openItem the menu item used to open an image
     * @param stage the main Paint application window
     */
    
     public static void setup(MenuItem openItem,Stage stage) {

        // OPEN
        openItem.setOnAction(event -> {

            // open a file chooser
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Open Image");

            // only show supported image file types
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Image Files",
                            "*.png",
                            "*.jpg",
                            "*.jpeg",
                            "*.bmp"
                    )
            );

            // store the file selected by the user
            currentFile = fileChooser.showOpenDialog(stage);

            if (currentFile != null) {

                // open the image in a new tab
                ImageTabs.openImage(currentFile);
            }
        });
    }
}
