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
                                     
                                             Open an image using File > Open.
                                             Create a new blank image using File > New.
                                     
                                             Choose a drawing tool from the Shapes menu or the tool buttons.
                                             Use the slider to change line width.
                                             Use the color chooser to select a drawing color.
                                             Turn Dashed Outline on to draw dashed shapes.
                                     
                                             Use Edit > Undo or Ctrl+Z to undo changes.
                                             Use Edit > Redo or Ctrl+Y to redo changes.
                                     
                                             Use the Select tool to select part of an image.
                                             Copy, paste, or move the selected area using the Edit menu.
                                     
                                             Use Image > Resize Drawing Area to change the canvas size.
                                             Use Image > Clear Canvas to clear the current image.
                                             Use the Image menu to rotate or flip the whole image or a selected area.
                                     
                                             Use File > Save or Ctrl+S to save your image.
                                             Use File > Save As or Ctrl+Shift+S to save a new copy.
                                     
                                             Autosave runs automatically while you work.
                                             The autosave countdown can be shown or hidden from the View menu.
                                     
                                             Use the Web menu to select an image and start or stop the web server.
                                     
                                             Hover over drawing tool buttons to see their tooltips.
                                     
                                     
                                     
                                     """);
            
            helpAlert.showAndWait();
        });
    }
}
