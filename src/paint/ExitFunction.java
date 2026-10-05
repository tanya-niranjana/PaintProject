/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *  Exits out of program
 * 
 * @author tanya
 */
public class ExitFunction {
    
     /**
     * Sets up the Exit menu item so it sends a close request
     * to the main Paint window.
     *
     * @param exitItem the menu item used to exit the program
     * @param stage the main Paint window
     */
    public static void setup(MenuItem exitItem, Stage stage) {

        // EXIT 
        // send a close request when the user selects file > exit
        exitItem.setOnAction(event -> { // when exit clicked
            
            // fire the same close request as pressing the windows close button
            stage.fireEvent(
                new javafx.stage.WindowEvent(
                stage, 
                javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST
                )
            );
        });
    }
}
