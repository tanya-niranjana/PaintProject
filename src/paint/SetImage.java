/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.image.Image;

/**
 * handles displaying an image in the drawing area.
 * 
 * @author tanya
 */
public class SetImage {

    /**
     * Displays a newly opened image and resizes the canvas
     * and drawing area to match the image dimensions.
     *
     * @param drawingArea the drawing area where the image will be displayed
     * @param image the image being displayed
     */    
    public static void setImage(DrawingArea drawingArea, Image image){
        
        // display the image
        drawingArea.imageView.setImage(image);
        
        // make the main drawing canvas the same size as the image
        drawingArea.canvas.setWidth(image.getWidth());

        drawingArea.canvas.setHeight(image.getHeight());

        // make the drawing area match the image size
        drawingArea.drawingPane.setPrefSize(
            image.getWidth(),
            image.getHeight()
        );
    }
}