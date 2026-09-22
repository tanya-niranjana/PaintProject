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
 *
 * @author tanya
 */
public class OpenImage {
     public static void setup(MenuItem openItem, Stage stage) {

        // OPEN 
        
        openItem.setOnAction(event -> { // when open gets selected run this code 

            //  open a file chooser 
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

            if (currentFile != null) { // if user selects something

                //load and display the selected image 
                Image image = new Image(currentFile.toURI().toString());

                drawingArea.setImage(image);// display it 

                try {
                       // reads all the data inside the image 
                    imageData = Files.readAllBytes(currentFile.toPath());

                } catch (IOException e) {
                    // if error occurs, run this instead of program crashing
                    System.out.println("Error opening image.");

                }
            }

        });
    }
}
