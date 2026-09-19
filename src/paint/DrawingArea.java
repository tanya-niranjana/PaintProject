/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;


import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 *
 * @author tanya
 */
public class DrawingArea {
    private ImageView imageView; // displays the image that the user opens
    private Canvas canvas; // Canvas placed over the image so the user can draw
    private Pane drawingPane; // Holds the image and canvas together
    private ScrollPane scrollPane; // Allows the user to scroll around large images
    
    private GraphicsContext gc; // used to draw lines on the canvas
   
    // stores where the user first clicks to start a line
    private double startX; 
    private double startY;
    
    private double lineWidth =3;// default line width
    
    private Color lineColor = Color.BLACK; // default drawing color
    
    private boolean modified = false; // keeps track of whether the image has unsaved changes
    
    // save the starting point when the mouse is pressed 
    private void setupDrawing(){
        
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                // get the location where the user started the line
                startX = event.getX();
                startY = event.getY();
            }
        });
        // draw a line when the user releases the mouse
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                // get the ending point of a line
                double endX = event.getX();
                double endY = event.getY();
               
                
                // apply the selected line width and color
                gc.setLineWidth(lineWidth);
                gc.setStroke(lineColor);
                
                
                // draw the line from starting point to ending
                gc.strokeLine(
                    startX,
                    startY,
                    endX,
                    endY
                );
                
    // mark the drawing as changed so the program knows it needs to be saved
                modified = true;
            }
        });
    }
    
    // creates and sets up the drawing area
    public DrawingArea() {
        
        imageView = new ImageView(); // creates the image display
        
        canvas = new Canvas(); // creates the transparent drawing canvas
        
        gc = canvas.getGraphicsContext2D(); // gets the tool used to draw on canvas
        
        drawingPane = new Pane();// creates the area that holds both the image and canvas
        
        // make any extra resized drawing space white 
        drawingPane.setStyle("-fx-background-color: white;");
        
        //Image goes first, canvas goes on top
        drawingPane.getChildren().addAll(imageView, canvas);
        
        //places the drawing area inside a scrollable window
        scrollPane = new ScrollPane(drawingPane);
        
        //allows large images to be moved around inside the scorll pane
        scrollPane.setPannable(true);
        
        // tur s on the mouse drawing control
        setupDrawing();
    }
    // displays a newly opened image and makes the canvas match its size
    public void setImage(Image image){
        
        imageView.setImage(image); // displays the image
        
        //makes the drawing canvas the same size as the image
        canvas.setWidth(image.getWidth());
        canvas.setHeight(image.getHeight());
        
        // make the drawing area match the image size
        drawingPane.setPrefSize(
            image.getWidth(),
            image.getHeight()
        );
    }
    
    // returns the scrollable drawing area so GuiImage can display it 
    public ScrollPane getView() {
        return scrollPane;
    }
    
    // updates the width used for new lines
    public void setLineWidth(double width){
        lineWidth = width;
    }
    
    // updates the color used for new lines
    public void setLineColor(Color color) {
        lineColor = color;
    }
    
    //creates one image containing both the og pic and any drawings
    public WritableImage getEditedImage() {
        
        // gets the current size of drawing
        int width = (int) drawingPane.getWidth();
        int height = (int) drawingPane.getHeight();
        
        // create any empty image that will hold the edited pic
        WritableImage editedImage = 
            new WritableImage(width,height);
        
        // take a snapshot of everything in the drawing area. 
        drawingPane.snapshot(
            new SnapshotParameters(),
            editedImage
        );
        
        // return the image so it can be saved
        return editedImage;
    }
    
    
    // returns true if the user has made changes that are not saved
    public boolean isModified() {
        return modified;
    }

    // changes the sizes of the canvas and drawing area
    public void resizeDrawingArea(double width, double height) {
        
        // only resizes if the user entered valid positive numbers
        if (width > 0 && height > 0){
        
            canvas.setWidth(width);
            canvas.setHeight(height);

            drawingPane.setPrefSize(width, height);

            // resizing count as am unsaved change 
            modified = true; 
        }
    }
    
    // marks the current image as saved
    public void markSaved() {
        modified = false;
    }
}

    
    
