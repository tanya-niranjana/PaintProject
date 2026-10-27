/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


/**
 * Handles rotating selected parts of the image
 * 
 * @author tanya
 */
public class SelectionTransform {
    /** 
     * Rotates the selected area 90 degrees clockwise
     * 
     * @param drawingArea current drawing area
     */
    public static void rotate90(DrawingArea drawingArea){
        
        //make sure there is a selection
        if(!drawingArea.hasSelection){
            return;
        }
        
        // save current image for undo 
        UndoRedo.saveState(drawingArea);
        
        //get current image 
        WritableImage fullImage = drawingArea.getEditedImage();
        
        int x = (int) drawingArea.selectionX;
        int y = (int) drawingArea.selectionY;
        int width = (int) drawingArea.selectionWidth;
        int height = (int) drawingArea.selectionHeight;
        
        PixelReader reader = fullImage.getPixelReader();
        
        //copy selected area 
        WritableImage selected = new WritableImage(
            reader,
            x,
            y,
            width,
            height
        );
        
        // rotated selection swaps width and height
        WritableImage rotated = new WritableImage(height,width);
        
        PixelReader selectedReader = selected.getPixelReader();
        
        PixelWriter writer = rotated.getPixelWriter();
        
        // rotate selected pixels
        
        for (int row=0; row < height; row++){
            
            for (int col = 0; col < width; col++) {
                
                writer.setArgb(height - 1 - row,col,selectedReader.getArgb(col, row));
                
            }
        }
        // remove old selected area
        drawingArea.gc.setFill(javafx.scene.paint.Color.WHITE);
        
         drawingArea.gc.fillRect(
                x,
                y,
                width,
                height
        );

        // draw rotated selection
        drawingArea.gc.drawImage(
                rotated,
                x,
                y
        );
         // update selection size
        drawingArea.selectionWidth = height;
        drawingArea.selectionHeight = width;

        drawingArea.selectionBox.setWidth(height);
        drawingArea.selectionBox.setHeight(width);

        drawingArea.modified = true;

        PaintLogger.log(
                "Selection rotated 90 degrees"
        );
    }
    public static void rotate180(DrawingArea drawingArea){
        
        //make sure there is a selection
        if(!drawingArea.hasSelection){
            return;
        }
        
        // save current image for undo 
        UndoRedo.saveState(drawingArea);
        
        //get current image 
        WritableImage fullImage = drawingArea.getEditedImage();
        
        int x = (int) drawingArea.selectionX;
        int y = (int) drawingArea.selectionY;
        int width = (int) drawingArea.selectionWidth;
        int height = (int) drawingArea.selectionHeight;
        
        PixelReader reader = fullImage.getPixelReader();
        
        //copy selected area 
        WritableImage selected = new WritableImage(
            reader,
            x,
            y,
            width,
            height
        );
        
        // rotated selection swaps width and height
        WritableImage rotated = new WritableImage(width,height);
        
        PixelReader selectedReader = selected.getPixelReader();
        
        PixelWriter writer = rotated.getPixelWriter();
        
        // rotate selected pixels
        
        for (int row=0; row < height; row++){
            
            for (int col = 0; col < width; col++) {
                
                writer.setArgb(width - 1 - col,height - 1 - row,selectedReader.getArgb(col, row));
                
            }
        }
        // remove old selected area
        drawingArea.gc.setFill(javafx.scene.paint.Color.WHITE);
        
         drawingArea.gc.fillRect(
                x,
                y,
                width,
                height
        );

        // draw rotated selection
        drawingArea.gc.drawImage(
                rotated,
                x,
                y
        );
         // update selection size
        drawingArea.selectionWidth = width;
        drawingArea.selectionHeight = height;

        drawingArea.selectionBox.setWidth(width);
        drawingArea.selectionBox.setHeight(height);
        drawingArea.modified = true;

        PaintLogger.log(
                "Selection rotated 180 degrees"
        );
    }

public static void rotate270(DrawingArea drawingArea){
        
        //make sure there is a selection
        if(!drawingArea.hasSelection){
            return;
        }
        
        // save current image for undo 
        UndoRedo.saveState(drawingArea);
        
        //get current image 
        WritableImage fullImage = drawingArea.getEditedImage();
        
        int x = (int) drawingArea.selectionX;
        int y = (int) drawingArea.selectionY;
        int width = (int) drawingArea.selectionWidth;
        int height = (int) drawingArea.selectionHeight;
        
        PixelReader reader = fullImage.getPixelReader();
        
        //copy selected area 
        WritableImage selected = new WritableImage(
            reader,
            x,
            y,
            width,
            height
        );
        
        // rotated selection swaps width and height
        WritableImage rotated = new WritableImage(height,width);
        
        PixelReader selectedReader = selected.getPixelReader();
        
        PixelWriter writer = rotated.getPixelWriter();
        
        // rotate selected pixels
        
        for (int row=0; row < height; row++){
            
            for (int col = 0; col < width; col++) {
                
                writer.setArgb(row, width - 1 - col,selectedReader.getArgb(col, row));
                
            }
        }
        // remove old selected area
        drawingArea.gc.setFill(javafx.scene.paint.Color.WHITE);
        
         drawingArea.gc.fillRect(
                x,
                y,
                width,
                height
        );

        // draw rotated selection
        drawingArea.gc.drawImage(
                rotated,
                x,
                y
        );
         // update selection size
        drawingArea.selectionWidth = height;
        drawingArea.selectionHeight = width;

        drawingArea.selectionBox.setWidth(height);
        drawingArea.selectionBox.setHeight(width);
        drawingArea.modified = true;

        PaintLogger.log(
                "Selection rotated 270 degrees"
        );
    }

    public static void flipHorizontal(DrawingArea drawingArea){

            //make sure there is a selection
            if(!drawingArea.hasSelection){
                return;
            }

            // save current image for undo 
            UndoRedo.saveState(drawingArea);

            //get current image 
            WritableImage fullImage = drawingArea.getEditedImage();

            int x = (int) drawingArea.selectionX;
            int y = (int) drawingArea.selectionY;
            int width = (int) drawingArea.selectionWidth;
            int height = (int) drawingArea.selectionHeight;

            PixelReader reader = fullImage.getPixelReader();

            //copy selected area 
            WritableImage selected = new WritableImage(
                reader,
                x,
                y,
                width,
                height
            );

            // rotated selection swaps width and height
            WritableImage rotated = new WritableImage(width,height);

            PixelReader selectedReader = selected.getPixelReader();

            PixelWriter writer = rotated.getPixelWriter();

            // rotate selected pixels

            for (int row=0; row < height; row++){

                for (int col = 0; col < width; col++) {

                    writer.setArgb(width - 1 - col,row,selectedReader.getArgb(col, row));

                }
            }
            // remove old selected area
            drawingArea.gc.setFill(javafx.scene.paint.Color.WHITE);

             drawingArea.gc.fillRect(
                    x,
                    y,
                    width,
                    height
            );

            // draw rotated selection
            drawingArea.gc.drawImage(
                    rotated,
                    x,
                    y
            );
             // update selection size
            drawingArea.selectionWidth = width;
            drawingArea.selectionHeight = height;

            drawingArea.selectionBox.setWidth(width);
            drawingArea.selectionBox.setHeight(height);
            drawingArea.modified = true;

            PaintLogger.log("Selection flipped horizontally");
        }
    public static void flipVertical(DrawingArea drawingArea){

            //make sure there is a selection
            if(!drawingArea.hasSelection){
                return;
            }

            // save current image for undo 
            UndoRedo.saveState(drawingArea);

            //get current image 
            WritableImage fullImage = drawingArea.getEditedImage();

            int x = (int) drawingArea.selectionX;
            int y = (int) drawingArea.selectionY;
            int width = (int) drawingArea.selectionWidth;
            int height = (int) drawingArea.selectionHeight;

            PixelReader reader = fullImage.getPixelReader();

            //copy selected area 
            WritableImage selected = new WritableImage(
                reader,
                x,
                y,
                width,
                height
            );

            // rotated selection swaps width and height
            WritableImage rotated = new WritableImage(width,height);

            PixelReader selectedReader = selected.getPixelReader();

            PixelWriter writer = rotated.getPixelWriter();

            // rotate selected pixels

            for (int row=0; row < height; row++){

                for (int col = 0; col < width; col++) {

                    writer.setArgb(col,height - 1 - row,selectedReader.getArgb(col, row));

                }
            }
            // remove old selected area
            drawingArea.gc.setFill(javafx.scene.paint.Color.WHITE);

             drawingArea.gc.fillRect(
                    x,
                    y,
                    width,
                    height
            );

            // draw rotated selection
            drawingArea.gc.drawImage(
                    rotated,
                    x,
                    y
            );
             // update selection size
            drawingArea.selectionWidth = width;
            drawingArea.selectionHeight = height;

            drawingArea.selectionBox.setWidth(width);
            drawingArea.selectionBox.setHeight(height);
            drawingArea.modified = true;

            PaintLogger.log(
                    "Selection flipped vertically"
            );
        }
}