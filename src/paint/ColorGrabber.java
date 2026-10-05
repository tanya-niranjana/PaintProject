/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
/**
 * Allows user to select a color from the current image
 * 
 * @author tanya
 */
public class ColorGrabber {
    
    /**
    * Sets up the color grabber tool so user
    * can click on drawing and use color for drawing
    * 
    * 
    * @param drawingArea the drawing area where image is displayed
    * @param colorPicker the color picker that shows the selected color
    * @param colorText the label that displays the color info
    * 
    */
    
    
    public static void setup(
            DrawingArea drawingArea,
            ColorPicker colorPicker,
            Label colorText) {

        // get the color when user clicks on screen
        drawingArea.canvas.setOnMouseClicked(event -> {

            // get one image containing the picture and current drawing
            WritableImage image =
                    drawingArea.getEditedImage();

            // get the x and y position where the user clicked
            int x = (int) event.getX();
            int y = (int) event.getY();

            // make sure the clicked point is inisde the image
            if (x >= 0
                    && y >= 0
                    && x < image.getWidth()
                    && y < image.getHeight()) {

                // get the color of the pixel that was clicked
                Color selectedColor =
                        image.getPixelReader()
                                .getColor(x, y);
                
                // make the grabbed color the new drawing color
                drawingArea.lineColor =
                        selectedColor;

                // updated the color picker to show the new color
                colorPicker.setValue(
                        selectedColor
                );

                // update label
                ColorTextLabel.update(
                        selectedColor,
                        colorText
                );
            }
        });
    }
}
