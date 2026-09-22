/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.File;

import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 *
 * @author tanya
 */
public class GuiImage extends Application {
    
    static File currentFile; //stores the image file currently being edit
    static byte[] imageData; // stores the original image file contents
    static DrawingArea drawingArea; // handles the image, canvas, and drawing tools
    
    
    // creates the main paint window and all of its control
    @Override
    public void start(Stage stage) {
        
        BorderPane root = new BorderPane(); // creates main layout
        
        drawingArea = new DrawingArea(); // creates the drawing area 
        
        Label colorLabel = new Label("Color: "); // put the drawing area in the center of the window 
        
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);  
        
        root.setCenter(drawingArea.getView());
        
        // creates menu bar 
        MenuBar menuBar = new MenuBar(); 
        
        Menu fileMenu = new Menu("File");

        
        // create the file menu options
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem exitItem = new MenuItem("Exit");
        
        
        // create the help menu and its options 
        Menu helpMenu = new Menu("Help");
        
        MenuItem helpItem = new MenuItem("Help");
        MenuItem aboutItem = new MenuItem("About");
       
        
        
        // adds all four menu items
        fileMenu.getItems().addAll(
                openItem,
                saveItem,
                saveAsItem,
                exitItem
        );
        
        // adds two help menu items
        helpMenu.getItems().addAll(
            helpItem,
            aboutItem
        );
        
        // create the Image Menu and resize option
        Menu imageMenu = new Menu("Image");
        
        MenuItem resizeItem = new MenuItem("Resize Drawing Area");
        
        imageMenu.getItems().add(resizeItem);
        
        // puts inside menu bar 
        menuBar.getMenus().addAll(
            fileMenu,
            imageMenu,
            helpMenu
            
        );

        // create the slide used to change line width
        Label widthLabel = new Label("Line Width"); 
        Slider lineWidthSlider = new Slider(1,20,3);
        
        // update the drawing line width whenever the slider changes 
        lineWidthSlider.setShowTickLabels(true);
        lineWidthSlider.setShowTickMarks(true);
        
        lineWidthSlider.valueProperty().addListener(
            (obserable, oldValue, newValue) -> {
                drawingArea.setLineWidth(newValue.doubleValue());
             }
        );
        
        // put the drawing controls together in one toolbar
        HBox tools = new HBox(
            10,
            widthLabel, 
            lineWidthSlider,
            colorLabel,
            colorPicker
        );
        
        // place the menu bar and drawing tools at the top of the window
        VBox topArea = new VBox(
            menuBar,
            tools
        );
        
        root.setTop(topArea);
        
        // update the drawing color when the user chooses a new color
        colorPicker.setOnAction(event -> {
            drawingArea.setLineColor(colorPicker.getValue());
        });
        
       // connect each menu item to its function file
      
      HelpFunction.setup(helpItem);

        AboutFunction.setup(aboutItem);

        OpenImage.setup(
                openItem,
                stage
        );

        ResizeFunction.setup(
                resizeItem
        );

        SaveFunction.setup(
                saveItem
        );

        SaveAsFunction.setup(
                saveAsItem,
                stage
        );

        ExitFunction.setup(
                exitItem,
                stage
        );

        CloseFunction.setup(
                stage,
                saveItem
        );

        
        
        // creates the scene 
        Scene scene = new Scene(root, 800, 600);
        
        // set window title and display the program
        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
    launch(args);
}

}