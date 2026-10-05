/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Creates and displays the "About" window for the 
 * Paint Program
 * 
 * @author tanya
 */
public class AboutFunction {
    /**
    * Connects the about menu item to the About window
    * 
    * @param aboutItem the menu item used to open the About window
    */
    
    public static void setup(MenuItem aboutItem) {

        // open a seperate about window
        aboutItem.setOnAction(event -> {
            
            Stage aboutStage = new Stage(); // create about window
            
            aboutStage.setTitle("About Paint"); 
            
            // prevent the user from interacting with Paint until About is closed
            aboutStage.initModality(Modality.APPLICATION_MODAL); 
            
            
            Label titleLabel = new Label ("Paint");
            Label infoLabel = new Label(
                """
                JavaFX Image Editor 
                 Created by Tanya Sharma 
                CS 250""");
            
            // create a button to close the about window
            Button closeButton = new Button("Close");
            
            
            // close window when button is pressed. 
            closeButton.setOnAction(e -> {
                aboutStage.close(); 
            });
            
            // put labels and buttons into vertical layout
            VBox layout = new VBox(
                10, 
                titleLabel, 
                infoLabel, 
                closeButton
            );
            // add spacing in center 
            layout.setPadding(new Insets(20));
            // create the scene for the about window
            Scene aboutScene = new Scene(
                layout,
                250,
                150
            );
            // add scene to window
            aboutStage.setScene(aboutScene);
            // show the about window and wait until its clsoed. 
            aboutStage.showAndWait();
        });
    }
}
