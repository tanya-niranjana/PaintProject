/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;


import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


/**
 * Handles rotating and flipping images
 * 
 * 
 * @author tanya
 */
public class ImageTransform {
    /**
     * Rotates the whole image 90 degrees clockwise
     * 
     * @param drawingArea current drawing area
     */
    
    public static void rotate90(DrawingArea drawingArea){
        
        // save current image for undo
        UndoRedo.saveState(drawingArea);
        
        // get current edited image 
        WritableImage original = drawingArea.getEditedImage();
        
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        
        // width and height switch after 90 degree rotation
        WritableImage rotated = new WritableImage(height, width);
        
        PixelReader reader = original.getPixelReader();
        PixelWriter writer = rotated.getPixelWriter();
        
        //move each pixel to its new location 
        for (int y = 0; y < height ; y++) {
            
            for (int x = 0;  x < width; x++) {
                
                writer.setArgb(height - 1 - y, x, reader.getArgb(x,y));
            
            }
        }
        
        // replace current image with rotated image
        drawingArea.imageView.setImage(rotated);
        
        //resize canvas to match new image
        drawingArea.canvas.setWidth(height);
        drawingArea.canvas.setHeight(width);
        
        drawingArea.drawingPane.setPrefSize(height,width);
        
        //clear old canvas drawing
        drawingArea.gc.clearRect(0,0,drawingArea.canvas.getWidth(),drawingArea.canvas.getHeight());
        
        drawingArea.modified = true;
        
        PaintLogger.log("Image rotated 90 degrees");
        
   }
    public static void rotate180(DrawingArea drawingArea){
        
        // save current image for undo
        UndoRedo.saveState(drawingArea);
        
        // get current edited image 
        WritableImage original = drawingArea.getEditedImage();
        
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        
        // width and height switch after 180 degree rotation
        WritableImage rotated = new WritableImage(width, height);  
        
        
        PixelReader reader = original.getPixelReader();
        PixelWriter writer = rotated.getPixelWriter();
        
        //move each pixel to its new location 
        for (int y = 0; y < height ; y++) {
            
            for (int x = 0;  x < width; x++) {
                
                writer.setArgb(width - 1 - x,height - 1 - y,reader.getArgb(x, y));            
            }
        }
        
        // replace current image with rotated image
        drawingArea.imageView.setImage(rotated);
       
        
        drawingArea.drawingPane.setPrefSize(width, height);        
        //clear old canvas drawing
        drawingArea.gc.clearRect(0,0,drawingArea.canvas.getWidth(),drawingArea.canvas.getHeight());
        
        drawingArea.modified = true;
        
        PaintLogger.log("Image rotated 180 degrees");
        
   }
    
    public static void rotate270(DrawingArea drawingArea){
        
        // save current image for undo
        UndoRedo.saveState(drawingArea);
        
        // get current edited image 
        WritableImage original = drawingArea.getEditedImage();
        
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        
        // width and height switch after 90 degree rotation
        WritableImage rotated = new WritableImage(height, width);
        
        PixelReader reader = original.getPixelReader();
        PixelWriter writer = rotated.getPixelWriter();
        
        //move each pixel to its new location 
        for (int y = 0; y < height ; y++) {
            
            for (int x = 0;  x < width; x++) {
                
                writer.setArgb(y,width - 1 - x,reader.getArgb(x, y));            
            }
        }
        
        // replace current image with rotated image
        drawingArea.imageView.setImage(rotated);
        
        //resize canvas to match new image
        drawingArea.canvas.setWidth(height);
        drawingArea.canvas.setHeight(width);
        
        drawingArea.drawingPane.setPrefSize(height,width);
        
        //clear old canvas drawing
        drawingArea.gc.clearRect(0,0,drawingArea.canvas.getWidth(),drawingArea.canvas.getHeight());
        
        drawingArea.modified = true;
        
        PaintLogger.log("Image rotated 270 degrees");
        
   }
    
    public static void flipHorizontal(DrawingArea drawingArea){
        
        // save current image for undo
        UndoRedo.saveState(drawingArea);
        
        // get current edited image 
        WritableImage original = drawingArea.getEditedImage();
        
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        
        // width and height switch after 180 degree rotation
        WritableImage rotated = new WritableImage(width, height);
        
        PixelReader reader = original.getPixelReader();
        PixelWriter writer = rotated.getPixelWriter();
        
        //move each pixel to its new location 
        for (int y = 0; y < height ; y++) {
            
            for (int x = 0;  x < width; x++) {
                
                writer.setArgb(width - 1 - x,y,reader.getArgb(x, y));          
            }
        }
        
        // replace current image with rotated image
        drawingArea.imageView.setImage(rotated);
       
        
        drawingArea.drawingPane.setPrefSize(width, height);
        
        //clear old canvas drawing
        drawingArea.gc.clearRect(0,0,drawingArea.canvas.getWidth(),drawingArea.canvas.getHeight());
        
        drawingArea.modified = true;
        
        PaintLogger.log("Image rotated Horizontal");
        
   }
    public static void flipVertical(DrawingArea drawingArea){
        
        // save current image for undo
        UndoRedo.saveState(drawingArea);
        
        // get current edited image 
        WritableImage original = drawingArea.getEditedImage();
        
        int width = (int) original.getWidth();
        int height = (int) original.getHeight();
        
        // width and height switch after vertical note
        WritableImage rotated = new WritableImage(width, height);   
        
        PixelReader reader = original.getPixelReader();
        PixelWriter writer = rotated.getPixelWriter();
        
        //move each pixel to its new location 
        for (int y = 0; y < height ; y++) {
            
            for (int x = 0;  x < width; x++) {
                
                writer.setArgb(x,height - 1 - y,reader.getArgb(x, y));        
            }
        }
        
        // replace current image with rotated image
        drawingArea.imageView.setImage(rotated);
        
        drawingArea.drawingPane.setPrefSize(width, height);  
        
        //clear old canvas drawing
        drawingArea.gc.clearRect(0,0,drawingArea.canvas.getWidth(),drawingArea.canvas.getHeight());
        
        drawingArea.modified = true;
        
        PaintLogger.log("Image rotated Vertical");
        
   }
    
}
