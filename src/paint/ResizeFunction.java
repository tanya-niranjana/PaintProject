/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import static paint.GuiImage.drawingArea;
/**
 *handles resizing the drawing area in the paint program
 * 
 * allows user to enter a new width and height
 * 
 * @author tanya
 */
public class ResizeFunction {
    
     /**
     * Sets up the Resize Drawing Area menu item.
     *
     * @param resizeItem the menu item used to open the resize dialog
     */
     public static void setup(MenuItem resizeItem) {

        // Ask the user for a new width and height for drawing area
        resizeItem.setOnAction((ActionEvent event) -> {
            
            // create the resize dialog button
            Dialog<ButtonType> dialog = new Dialog<>();
            
            dialog.setTitle("Resize Drawing Area");
            dialog.setHeaderText("Enter the new drawing size");
            
            
            // create text boxes for a new width and height
            TextField widthField = new TextField();
            TextField heightField = new TextField();
            
            // show what should be entered in each box
            widthField.setPromptText("Width");
            heightField.setPromptText("Height");
            
            
            // put the labels and text boxes into a vertical layout
            VBox resizeBox = new VBox(
                    10,
                    new Label("Width:"),
                    widthField,
                    new Label("Height:"),
                    heightField
            );
            
            // spacing around contents 
            resizeBox.setPadding(new Insets(10));
            
            // place the resize controls inside the dialog
            dialog.getDialogPane().setContent(resizeBox);
            
            // add OK and Cancel buttons
            dialog.getDialogPane().getButtonTypes().addAll(
                    ButtonType.OK,
                    ButtonType.CANCEL
            );
            
            // show dialog and wait for user to choose a button
            Optional<ButtonType> result = dialog.showAndWait();
            
            // only continue if user clicks ok
            if (result.isPresent() && result.get() == ButtonType.OK) {
                
                try {
                    
                    // convert the user's entries from text into numbers
                    double newWidth =Double.parseDouble(widthField.getText());
                    double newHeight = Double.parseDouble(heightField.getText());
                    
                    // resize the drawing area using the new dimensions
                    drawingArea.resizeDrawingArea(newWidth,newHeight);
                    
                    // show an error if the user enters something that is not a number
                } catch (NumberFormatException e) {
                    // create an error message if user enters invalid number
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    // tell user what went wrong
                    errorAlert.setContentText("Please enter valid numbers.");
                    // show message
                    errorAlert.showAndWait();
                }
            }
        });
    }
}
