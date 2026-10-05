/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

/**
 * Creates an edited image containing both the original image
 * and any drawings added by the user.
 * 
 * @author tanya
 */
public class GetEditedImage {
    
    /**
     * Takes a snapshot of the entire drawing area and returns it
     * as one image that can be saved.
     *
     * The selection box is temporarily hidden so it is not
     * included as part of the saved image.
     * 
     * @param drawingArea the drawing area containing the image and drawings
     * @return the edited image containing everything in the drawing area
     */
    public static WritableImage getEditedImage(
            DrawingArea drawingArea) {
        
        // get the current size of the drawing area
        int width =
                (int) drawingArea.drawingPane.getWidth();

        int height =
                (int) drawingArea.drawingPane.getHeight();
        
        // create an empty image that will hold the edited picture
        WritableImage editedImage =
                new WritableImage(
                        width,
                        height
                );

        // remember whether the selection box was showing
        boolean selectionWasVisible =
                drawingArea.selectionBox.isVisible();

        // hide the selection box before taking the snapshot
        drawingArea.selectionBox.setVisible(
                false
        );
        
        // take a snapshot of everything in the drawing area
        drawingArea.drawingPane.snapshot(
                new SnapshotParameters(),
                editedImage
        );

        // return the selection box to its previous visibility
        drawingArea.selectionBox.setVisible(
                selectionWasVisible
        );
        
        // return the image so it can be saved
        return editedImage;
    }
}