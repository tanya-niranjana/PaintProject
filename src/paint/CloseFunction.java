/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import static paint.GuiImage.drawingArea;

/**
 * Closes the function as well as 
 * warns user if they try exiting
 * without saving
 * 
 * @author tanya
 */
public class CloseFunction {
    public static void setup(Stage stage, MenuItem saveItem) {

        // warn the user before closing if there are unsaved changes 
        stage.setOnCloseRequest(event -> {
            if (drawingArea.isModified()) { // only show the warning if something has changed
                
                Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION
                );
                
                alert.setTitle("Unsaved Changes");
                alert.setHeaderText(
                        "You have unsaved changes."
                );
                
                alert.setContentText(
                    "Would you like ot save before closing?"
                );
                
                // create save, dont save, and cancel choices 
                ButtonType saveButton =
                        new ButtonType("Save");
                
                ButtonType dontSaveButton = 
                        new ButtonType("Don't Save");
                
                ButtonType cancelButton = 
                        ButtonType.CANCEL;
                
                alert.getButtonTypes().setAll(
                    saveButton,
                    dontSaveButton, 
                    cancelButton
                );
                
                // wait for the user to choose what they want to do
                Optional<ButtonType> result =
                        alert.showAndWait();
                
                if (result.isPresent()
                        && result.get() == saveButton) {
                    
                    // save the file before closing 
                    saveItem.fire();
                    
                    //stop the window from closing if the save did not finish                    
                    if (drawingArea.isModified()){
                        event.consume(); //stop the window from closing
                    }
                    
                } else if (result.isPresent()
                        && result.get()
                            == dontSaveButton) {
                    
                    // allow the window to close
                    
                } else {
                    
                    //cancel closing
                    event.consume();
                }
            }
        });
    }
}
