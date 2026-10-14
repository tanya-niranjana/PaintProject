/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
/**
 * Displays instructions for using paint program
 * 
 * @author tanya
 */
public class HelpFunction {
    
     /**
     * Sets up the Help menu item so it displays instructions
     * for using the main features of the Paint program.
     *
     * @param helpItem the menu item used to open the Help window
     */
      public static void setup(MenuItem helpItem) {

        // show instructions when the user selects Help
        helpItem.setOnAction(event -> {
            
            Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
            
            helpAlert.setTitle("help");
            helpAlert.setHeaderText("Paint Help");
            
            helpAlert.setContentText("""
                                     
                                     ** needs to be updated
                                     Open an image using File > Open. 
                                     Draw a line by clicking an dragging on the image. 
                                     Use the slider to change line width. 
                                     Use the color chooser to select a line color. 
                                     Use Image > Resize Drawing Area to make the canvas larger
                                     Use File > Save or Save as to save your image.""");
            
            helpAlert.showAndWait();
        });
    }
}
