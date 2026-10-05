/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
/**
 *
 * Handles creating a new blank image
 * 
 * @author tanya
 */
public class BlankImage {
    /**
    *
    * Sets up a the New menu item so the user can
    * create a new blank image
    * 
    * @param newItem the menu item used to create a new image
    */

    public static void setup(
        MenuItem newItem){
        
        // Ctrl + N creates a new blank image
        newItem.setAccelerator(
                new KeyCodeCombination(
                        KeyCode.N,
                        KeyCombination.CONTROL_DOWN
                )
        );
        
        //create a new blanket image when New is selected
        newItem.setOnAction(event -> {
            
            ImageTabs.openBlankImage(800,600);
            
       
        });
    }
    
    
}
