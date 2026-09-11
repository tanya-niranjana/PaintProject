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

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javafx.stage.FileChooser;
    
   
        
/**
 *
 * @author tanya
 */
public class GuiImage extends Application {
    
    private File currentFile; //stores the image file that the user has open 
    private byte[] imageData; // stores the actual image file contents
    
    @Override
    
    public void start(Stage stage) {
        
        BorderPane root = new BorderPane(); // creates main layout
        
        ImageView imageView = new ImageView(); // empty space for image to appear 

        
        // image view height, width, and no stretch 
        imageView.setFitWidth(750);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);

        root.setCenter(imageView); // centers image 
        
        // creates menu bar 
        MenuBar menuBar = new MenuBar(); 
        Menu fileMenu = new Menu("File");

        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem exitItem = new MenuItem("Exit");
        
        
        // adds all four menu items
        fileMenu.getItems().addAll(
                openItem,
                saveItem,
                saveAsItem,
                exitItem
        );
        
        // puts inside menu bar 
        menuBar.getMenus().add(fileMenu);
        
        
        // place at top 
        root.setTop(menuBar);
        
        // OPEN 
        
        openItem.setOnAction(event -> { // when open gets selected run this code 

            // creates the window file selection box
            FileChooser fileChooser = new FileChooser(); 
            
            
            fileChooser.setTitle("Open Image");
            
            // allowed extentions 
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Image Files",
                            "*.png",
                            "*.jpg",
                            "*.jpeg"
                    )
            );


            currentFile = fileChooser.showOpenDialog(stage);

            if (currentFile != null) { // if user selects something

                //load image 
                Image image = new Image(currentFile.toURI().toString());

                imageView.setImage(image); // display it 

                try {
                       // reads all the data inside the image 
                    imageData = Files.readAllBytes(currentFile.toPath());

                } catch (IOException e) {
                    // if error occurs, run this instead of program crashing
                    System.out.println("Error opening image.");

                }
            }

            });
        
        // SAVE 
        saveItem.setOnAction(event -> { // when save is selected, run 

            // checking to see if an image is actually selected
            if (currentFile != null && imageData != null) { 

                try {
                    // writes image data back to current file
                    Files.write(
                            currentFile.toPath(),
                            imageData
                    );

                } catch (IOException e) {

                    System.out.println("Error saving image.");

                }
            }

        });
        
        
       // SAVE AS
        saveAsItem.setOnAction(event -> { // when save as is clicked, run code

            if (imageData != null && currentFile != null) { // make sure file is opened

                FileChooser fileChooser = new FileChooser(); // create another file chooser

                fileChooser.setTitle("Save Image As"); // change window title 

                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter(
                                "Image Files",
                                "*.png",
                                "*.jpg",
                                "*.jpeg"
                        )
                );

                File newFile = fileChooser.showSaveDialog(stage); //  stores data in new file

                if (newFile != null) { // make sure they didn't cancel

                    // Get extension from original image
                    String originalName = currentFile.getName();
                    String extension = ""; // start with empty extension

                    // find the last dot 
                    int dotIndex = originalName.lastIndexOf(".");
                    
                    // grab the ".___" part of the file
                    if (dotIndex > 0) {
                        extension = originalName.substring(dotIndex); // store it
                    }

                    // Add extension if user did not type one
                    if (!newFile.getName().contains(".")) {
                        newFile = new File(
                                newFile.getAbsolutePath() + extension
                        );
                    }

                    try {
                        // write the image data into new file
                        Files.write(
                                newFile.toPath(),
                                imageData
                        );

                        currentFile = newFile; // update currentFile

                    } catch (IOException e) {

                        System.out.println("Error saving image.");

                    }
                }
            }

        });
        
        // EXIT 
        exitItem.setOnAction(event -> { // when exit clicked
            stage.close(); // close java window
        });
        
        // creates the scene 
        Scene scene = new Scene(root, 800, 600);
        
        // set winfow title
        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
