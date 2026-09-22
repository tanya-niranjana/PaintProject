/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;

import static paint.GuiImage.currentFile;
import static paint.GuiImage.drawingArea;
/**
 *
 * @author tanya
 */
public class SaveFunction {
     public static void setup(MenuItem saveItem) {

        // SAVE 
        // Save the edited image over the currently opened file 
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // when save is selected, run
                
                // checking to see if an image is actually selected
                if (currentFile != null) {
                    
                    // get a snapshot containing the original image and drawings
                    WritableImage editedImage =
                            drawingArea.getEditedImage();
                    
                    // convert the javafx image into a format ImageIO can save
                    BufferedImage bufferedImage =
                            SwingFXUtils.fromFXImage(
                                    editedImage,
                                    null
                            );
                    
                    String fileName =
                            currentFile.getName().toLowerCase();
                    
                    // determine which image format should be used 
                    String format = "png";
                    
                    if (fileName.endsWith(".jpg")
                            || fileName.endsWith(".jpeg")) {
                        
                        format = "jpg";
                    
                    }else if (fileName.endsWith(".bmp")) {
                        
                        format = "bmp";
                    }
                                
                    
                    try {
                        
                        // write the edited image to the file 
                        ImageIO.write(
                                bufferedImage,
                                format,
                                currentFile
                        );
                        
                        
                        // the lastest changes have now been saved 
                        drawingArea.markSaved();
                        
                    } catch (IOException e){
                        
                        System.out.println(
                                "Error saving Image"
                        );
                    }
                }
            }
        });
    }
}
