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

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javafx.stage.FileChooser;

import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
   
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.color;

import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Modality;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import static java.lang.String.format;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.ButtonType;
import java.util.Optional;


import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar;

/**
 *
 * @author tanya
 */
public class GuiImage extends Application {
    
    private File currentFile; //stores the image file currently being edit
    private byte[] imageData; // stores the original image file contents
    private DrawingArea drawingArea; // handles the image, canvas, and drawing tools
    
    
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
        

        // show instructions when the user selects Help
        helpItem.setOnAction(event -> {
            
            Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
            
            helpAlert.setTitle("help");
            helpAlert.setHeaderText("Paint Help");
            
            helpAlert.setContentText("""
                                     Open an image using File > Open. 
                                     Draw a line by clicking an dragging on the image. 
                                     Use the slider to change line width. 
                                     Use the color chooser to select a line color. 
                                     Use Image > Resize Drawing Area to make the canvas larger
                                     Use File > Save or Save as to save your image.""");
            
            helpAlert.showAndWait();
        });
        
        
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
        // OPEN 
        
        openItem.setOnAction(event -> { // when open gets selected run this code 

            //  open a file chooser 
            FileChooser fileChooser = new FileChooser(); 
            
            
            fileChooser.setTitle("Open Image");
            
            // only show supported image file types 
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Image Files",
                            "*.png",
                            "*.jpg",
                            "*.jpeg",
                            "*.bmp"
                    )
            );

            // store the file selected by the user 
            currentFile = fileChooser.showOpenDialog(stage);

            if (currentFile != null) { // if user selects something

                //load and display the selected image 
                Image image = new Image(currentFile.toURI().toString());

                drawingArea.setImage(image);// display it 

                try {
                       // reads all the data inside the image 
                    imageData = Files.readAllBytes(currentFile.toPath());

                } catch (IOException e) {
                    // if error occurs, run this instead of program crashing
                    System.out.println("Error opening image.");

                }
            }

            });
        
        // Ask the user for a new width and height for drawing area
        resizeItem.setOnAction((ActionEvent event) -> {
            
            // create the resize dialog button
            Dialog<ButtonType> dialog = new Dialog<>();
            
            dialog.setTitle("Resize Drawing Area");
            dialog.setHeaderText("Enter the new drawing size");
            
            
            // create text boxes for a new width and height
            TextField widthField = new TextField();
            TextField heightField = new TextField();
            
            // show what should be entered in each box
            widthField.setPromptText("Width");
            heightField.setPromptText("Height");
            
            
            // put the labels and text boxes into a vertical layout
            VBox resizeBox = new VBox(
                    10,
                    new Label("Width:"),
                    widthField,
                    new Label("Height:"),
                    heightField
            );
            
            // spacing around contents 
            resizeBox.setPadding(new Insets(10));
            
            // place the resize controls inside the dialog
            dialog.getDialogPane().setContent(resizeBox);
            
            // add OK and Cancel buttons
            dialog.getDialogPane().getButtonTypes().addAll(
                    ButtonType.OK,
                    ButtonType.CANCEL
            );
            
            // show dialog and wait for user to choose a button
            Optional<ButtonType> result =
                    dialog.showAndWait();
            
            // only continue if user clicks ok
            if (result.isPresent()
                    && result.get() == ButtonType.OK) {
                
                try {
                    
                    // convert the user's entries from text into numbers
                    double newWidth =
                            Double.parseDouble(
                                    widthField.getText()
                            );
                    double newHeight =
                            Double.parseDouble(
                                    heightField.getText()
                            );
                    
                    // resize the drawing area using the new dimensions
                    drawingArea.resizeDrawingArea(
                            newWidth,
                            newHeight
                    );
                    
                    // show an error if the user enters something that is not a number
                } catch (NumberFormatException e) {
                    // create an error message if user enters invalid number
                    Alert errorAlert =
                            new Alert(
                                    Alert.AlertType.ERROR
                            );
                    // tell user what went wrong
                    errorAlert.setContentText(
                            "Please enter valid numbers."
                    );
                    // show message
                    errorAlert.showAndWait();
                }
            }
        });
        // SAVE 
        // Save the edited image over the currently opened file 
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // when save is selected, run
                
                // checking to see if an image is actually selected
                if (currentFile != null) {
                    
                    // get a snapshot containing the original image and drawings
                    WritableImage editedImage =
                            drawingArea.getEditedImage();
                    
                    // convert the javafx image into a format ImageIO can save
                    BufferedImage bufferedImage =
                            SwingFXUtils.fromFXImage(
                                    editedImage,
                                    null
                            );
                    
                    String fileName =
                            currentFile.getName().toLowerCase();
                    
                    // determine which image format should be used 
                    String format = "png";
                    
                    if (fileName.endsWith(".jpg")
                            || fileName.endsWith(".jpeg")) {
                        
                        format = "jpg";
                    
                    }else if (fileName.endsWith(".bmp")) {
                        
                        format = "bmp";
                    }
                                
                    
                    try {
                        
                        // write the edited image to the file 
                        ImageIO.write(
                                bufferedImage,
                                format,
                                currentFile
                        );
                        
                        
                        // the lastest changes have now been saved 
                        drawingArea.markSaved();
                        
                    } catch (IOException e){
                        
                        System.out.println(
                                "Error saving Image"
                        );
                    }
                }
            }
        });
        
        
        // SAVE AS
        // Let the user save the edited image as a new file
        saveAsItem.setOnAction(event -> {

            if (imageData != null) {
                
                // show the different image formats that can be saved
                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("Save Image As");

                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter(
                                "PNG Image",
                                "*.png"
                        ),
                        new FileChooser.ExtensionFilter(
                                "JPEG Image",
                                "*.jpg",
                                "*.jpeg"
                        ),
                        new FileChooser.ExtensionFilter(
                                "BMP Image",
                                "*.bmp"
                        )
                );

                // asks the user where they want to save the new file
                File newFile = fileChooser.showSaveDialog(stage);

                if (newFile != null) {

                    // get the full edited image
                    WritableImage editedImage =
                            drawingArea.getEditedImage();

                    BufferedImage bufferedImage =
                            SwingFXUtils.fromFXImage(
                                    editedImage,
                                    null
                            );

                    String fileName =
                            newFile.getName().toLowerCase();
                    // determine the file type based on the filename
                    String format = "png";

                    if (fileName.endsWith(".jpg")
                            || fileName.endsWith(".jpeg")) {

                        format = "jpg";

                    } else if (fileName.endsWith(".bmp")) {

                        format = "bmp";

                    } else if (!fileName.endsWith(".png")) {

                        newFile = new File(
                                newFile.getAbsolutePath()
                                        + ".png"
                        );
                    }

                    try {

                        // Save the edited image to the new file
                        ImageIO.write(
                                bufferedImage,
                                format,
                                newFile
                        );

                        // make the new file the current file 
                        currentFile = newFile;

                        // mark all changes as saved 
                        drawingArea.markSaved();

                    } catch (IOException e) {

                        System.out.println(
                                "Error saving image"
                        );
                    }
                }
            }
        });
                    
        
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
        
        // creates the scene 
        Scene scene = new Scene(root, 800, 600);
        
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
             
        
        
        // set window title and display the program
        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
    launch(args);
}

}