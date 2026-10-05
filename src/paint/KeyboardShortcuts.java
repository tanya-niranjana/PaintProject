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
 * adds keyboard shortcuts to the file menu options.
 * 
 * @author tanya
 */
public class KeyboardShortcuts {
     /**
     * Sets up keyboard shortcuts for opening, saving,
     * saving as, and exiting the Paint program.
     *
     * @param openItem the menu item used to open an image
     * @param saveItem the menu item used to save an image
     * @param saveAsItem the menu item used to save an image as a new file
     * @param exitItem the menu item used to exit the program
     */
    
     public static void setup(
            MenuItem openItem,
            MenuItem saveItem,
            MenuItem saveAsItem,
            MenuItem exitItem) {

         // ctrl + O opens an image
        openItem.setAccelerator(
                new KeyCodeCombination(
                        KeyCode.O,
                        KeyCombination.CONTROL_DOWN
                )
        );

        // ctrl + s saves the current image
        saveItem.setAccelerator(
                new KeyCodeCombination(
                        KeyCode.S,
                        KeyCombination.CONTROL_DOWN
                )
        );

        // ctrl + shift + s opens save as 
        saveAsItem.setAccelerator(
                new KeyCodeCombination(
                        KeyCode.S,
                        KeyCombination.CONTROL_DOWN,
                        KeyCombination.SHIFT_DOWN
                )
        );

        // ctrl + q exits the program
        exitItem.setAccelerator(
                new KeyCodeCombination(
                        KeyCode.Q,
                        KeyCombination.CONTROL_DOWN
                )
        );
    }
}
