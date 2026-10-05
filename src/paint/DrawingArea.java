/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package paint;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Stack;


/**
 * Creates and manages the drawing area.
 * 
 * Holds the image, canvas, drawing settings, and modified status.
 * 
 * @author tanya
 */
public class DrawingArea {

    ImageView imageView; // displays the image that the user opens

    Canvas canvas; // canvas placed over the image so the user can draw

    Pane drawingPane; // holds the image and canvas together

    ScrollPane scrollPane; // allows the user to scroll around large images
   
    Stack<WritableImage> undoStack =
            new Stack<>(); // stores previous image states for Undo

    Stack<WritableImage> redoStack =
            new Stack<>(); // stores undone image states for Redo

    GraphicsContext gc; // used to draw lines on the canvas
   
    // stores where the user first clicks to start a drawing
    double startX; 
    double startY;
    
    double lineWidth = 3; // default line width
    
    Color lineColor = Color.BLACK; // default drawing color
    
    boolean modified = false; // keeps track of unsaved changes


    // stores the location and size of the selected area
    double selectionX;
    double selectionY;
    double selectionWidth;
    double selectionHeight;

    // keeps track of whether the user currently has an area selected
    boolean hasSelection = false;

    // stores the image copied from the selected area
    WritableImage copiedImage;

    // shows the selected area on the screen
    Rectangle selectionBox;


    /**
     * Creates and sets up the drawing area.
     */
    public DrawingArea() {
        
        // creates the image display
        imageView =
                new ImageView();
        
        // creates the transparent drawing canvas
        canvas =
                new Canvas();
        
        // gets the tool used to draw on the canvas
        gc =
                canvas.getGraphicsContext2D();
        
        // creates the area that holds the image and canvas
        drawingPane =
                new Pane();


        // create the box that will show the current selection
        selectionBox =
                new Rectangle();

        // selection box should only show an outline
        selectionBox.setFill(
                Color.TRANSPARENT
        );

        // make the selection outline black
        selectionBox.setStroke(
                Color.BLACK
        );

        // make the selection outline dashed
        selectionBox.getStrokeDashArray()
                .addAll(
                        5.0,
                        5.0
                );

        // selection box should not block mouse drawing
        selectionBox.setMouseTransparent(
                true
        );

        // hide the selection box until something is selected
        selectionBox.setVisible(
                false
        );
        

        // make any extra resized drawing space white
        drawingPane.setStyle(
                "-fx-background-color: white;"
        );
        
        // image goes first, drawing canvas goes second,
        // and the selection outline goes on top
        drawingPane.getChildren().addAll(
                imageView,
                canvas,
                selectionBox
        );
        
        // places the drawing area inside a scrollable window
        scrollPane =
                new ScrollPane(
                        drawingPane
                );
        
        // allows large images to be moved around inside the scroll pane
        scrollPane.setPannable(
                true
        );
    }
    

    /**
     * Displays an image in the drawing area.
     *
     * @param image the image that will be displayed
     */
    public void setImage(Image image) {
        SetImage.setImage(
                this,
                image
        );
    }
    
    
    /**
     * Returns the scrollable drawing area so it can be displayed in GuiImage.
     *
     * @return the ScrollPane containing the drawing area
     */
    public ScrollPane getView() {
        return scrollPane;
    }
    

    /**
     * Updates the width used for new lines and shapes.
     *
     * @param width the new line width
     */
    public void setLineWidth(double width) {
        SetLineWidth.setLineWidth(
                this,
                width
        );
    }
    

    /**
     * Updates the color used for new lines and shapes.
     *
     * @param color the new drawing color
     */
    public void setLineColor(Color color) {
        SetLineColor.setLineColor(
                this,
                color
        );
    }
    

    /**
     * Creates one image containing the original image and current drawings.
     *
     * @return the edited image
     */
    public WritableImage getEditedImage() {
        return GetEditedImage.getEditedImage(
                this
        );
    }
    

    /**
     * Checks whether the image has unsaved changes.
     *
     * @return true if the image has been modified
     */
    public boolean isModified() {
        return IsModified.isModified(
                this
        );
    }


    /**
     * Changes the width and height of the drawing area.
     *
     * @param width the new width of the drawing area
     * @param height the new height of the drawing area
     */
    public void resizeDrawingArea(
            double width,
            double height) {

        ResizedDrawingArea.resizeDrawingArea(
                this,
                width,
                height
        );
    }
    

    /**
     * Marks the current image as saved.
     */
    public void markSaved() {
        MarkSaved.markSaved(
                this
        );
    }
}