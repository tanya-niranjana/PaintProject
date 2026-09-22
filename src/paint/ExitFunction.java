/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author tanya
 */
public class ExitFunction {
    public static void setup(MenuItem exitItem, Stage stage) {

        // EXIT 
        // send a close request when the user selects file > exit
        exitItem.setOnAction(event -> { // when exit clicked
            stage.fireEvent(
                new javafx.stage.WindowEvent(
                stage, 
                javafx.stage.WindowEvent.WINDOW_CLOSE_REQUEST
                )
            );
        });
    }
}
