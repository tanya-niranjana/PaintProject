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

import javafx.scene.control.CheckMenuItem;

import javafx.scene.control.TabPane;
/**
 *Creates the main user interface for program
 * Sets up menu, drawing tools, color controls, line width,
 * tabs, and connects each feature to its function class 
 * 
 * @author tanya
 */
public class GuiImage extends Application {
    
    static File currentFile; //stores the image file currently being edit
    static byte[] imageData; // stores the original image file contents
    static DrawingArea drawingArea; // handles the image, canvas, and drawing tools
    
    
    /**
     * Creates and displays the main Paint window and all of its controls.
     *
     * @param stage the main Paint application window
     */    @Override
    public void start(Stage stage) {
        
        BorderPane root = new BorderPane(); // creates main layout
        
        Label colorLabel = new Label("Color: "); // put the drawing area in the center of the window 
        
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);  
        
        Label colorText = new Label();
        
        // create the area that holds the image tabs
        TabPane tabPane = ImageTabs.createTabPane();

        // place the tabs in the center of the window
        root.setCenter(tabPane);

        // default black
        ColorTextLabel.update(
                Color.BLACK,
                colorText
        );

        
        // creates menu bar 
        MenuBar menuBar = new MenuBar(); 
        
        Menu fileMenu = new Menu("File");

        // create the Edit menu
        Menu editMenu = new Menu("Edit");

        // create View menu
        Menu viewMenu = new Menu("View");

        // option to show or hide autosave countdown
        CheckMenuItem showTimerItem = new CheckMenuItem("Show Autosave Countdown");

        showTimerItem.setSelected(true);

        viewMenu.getItems().add(showTimerItem);
        
        // create Undo and Redo options
        MenuItem undoItem = new MenuItem("Undo");
        MenuItem redoItem = new MenuItem("Redo");
        
        // create copy and paste options
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        MenuItem moveItem = new MenuItem("Move Selection");

        // add Undo and Redo to the Edit menu
        editMenu.getItems().addAll(
                undoItem,
                redoItem,
                copyItem,
                pasteItem,
                moveItem
        );
        
        // create the file menu options
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem exitItem = new MenuItem("Exit");
        
        KeyboardShortcuts.setup(
            openItem,
            saveItem,
            saveAsItem,
            exitItem
        );
        
        // create the Shapes menu
        Menu shapesMenu = new Menu("Shapes");

        // diff shapes
        MenuItem textItem = new MenuItem("Text");
        MenuItem lineItem = new MenuItem("Line");
        MenuItem squareItem = new MenuItem("Square");
        MenuItem rectangleItem = new MenuItem("Rectangle");
        MenuItem circleItem = new MenuItem("Circle");
        MenuItem ellipseItem = new MenuItem("Ellipse");
        MenuItem diamondItem = new MenuItem("Diamond");
        MenuItem starItem = new MenuItem("Star");
        MenuItem polygonItem = new MenuItem("Regular Polygon");
        
        // triangle submenu
        Menu triangleMenu = new Menu("Triangle");

        // create the triangle choices
        MenuItem triangleItem =
                new MenuItem("Regular Triangle");

        MenuItem rightTriangleItem =
                new MenuItem("Right Triangle");

        MenuItem nonRightTriangleItem =
                new MenuItem("Non-Right Triangle");

        // add the triangle choices inside the Triangle menu
        triangleMenu.getItems().addAll(
                triangleItem,
                rightTriangleItem,
                nonRightTriangleItem
        );
        
        
        // additional drawing options
        MenuItem pencilItem = new MenuItem("Pencil");
        MenuItem eraserItem = new MenuItem("Eraser");
        MenuItem colorGrabberItem = new MenuItem("Color Grabber");
        MenuItem selectItem = new MenuItem("Select");
        
        // dashed lines yes or no
        CheckMenuItem dashedItem = new CheckMenuItem("Dashed Outline");
        
        // add everything to shape menu
        shapesMenu.getItems().addAll(
            textItem,
            lineItem,
            pencilItem,
            eraserItem,
            colorGrabberItem,
            squareItem,
            rectangleItem,
            circleItem,
            ellipseItem,
            triangleMenu,
            diamondItem,
            starItem,
            polygonItem,
            selectItem, 
            dashedItem
        );
        
        // create the help menu and its options 
        Menu helpMenu = new Menu("Help");
        
        MenuItem helpItem = new MenuItem("Help");
        MenuItem aboutItem = new MenuItem("About");
       
        
        // adds all four menu items
        fileMenu.getItems().addAll(
                newItem,
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
        MenuItem clearItem = new MenuItem("Clear Canvas");
        
        // add the image options to the Image menu
        imageMenu.getItems().addAll(
            resizeItem,
            clearItem
        );
        
        imageMenu.getItems().add(resizeItem);

        // create web menu
        Menu webMenu = new Menu("Web");
        MenuItem selectWebImageItem = new MenuItem("Select Current Tab for Web");
        MenuItem startServerItem = new MenuItem("Start Web Server");
        MenuItem stopServerItem = new MenuItem("Stop Web Server");

        
        // put all menus inside the menu bar
        menuBar.getMenus().addAll(
            fileMenu,
            editMenu,
            imageMenu,
            shapesMenu,
            viewMenu,
            webMenu,
            helpMenu
        );

        
        webMenu.getItems().addAll(
        selectWebImageItem,
        startServerItem,
        stopServerItem
        );
        
        // move the selected part of the image
        moveItem.setOnAction(event -> {

            if (drawingArea != null) {

                MoveSelection.setupDrawing(
                        drawingArea
                );
            }
        });
        
        // choose the text tool
        textItem.setOnAction(event -> {
            TextTool.setupDrawing(drawingArea);
        });
        
        // choose the selection tool
        selectItem.setOnAction(event -> {
            SelectionTool.setupDrawing(drawingArea);
        });
        
        // choose straight line tool
        lineItem.setOnAction(event -> {
            SetUpDrawing.setupDrawing(drawingArea);
        });

        // choose square tool
        squareItem.setOnAction(event -> {
            SqaureTool.setupDrawing(drawingArea);
        });

        // choose rectangle tool
        rectangleItem.setOnAction(event -> {
            RectangleTool.setupDrawing(drawingArea);
        });

        // choose circle tool
        circleItem.setOnAction(event -> {
            CircleTool.setupDrawing(drawingArea);
        });

        // choose ellipse tool
        ellipseItem.setOnAction(event -> {
            EllipseTool.setupDrawing(drawingArea);
        });

        // choose triangle tool
        triangleItem.setOnAction(event -> {
            TriangleTool.setupDrawing(drawingArea);
        });
        
        // choose right triangle tool
        rightTriangleItem.setOnAction(event -> {
            RightTriangleTool.setupDrawing(drawingArea);
        });

        // choose non-right triangle tool
        nonRightTriangleItem.setOnAction(event -> {
            NonRightTriangleTool.setupDrawing(drawingArea);
        });
        
        // choose pencil drawing tool
        pencilItem.setOnAction(event -> {
            PencilTool.setupDrawing(drawingArea);
        });

        // choose eraser tool
        eraserItem.setOnAction(event -> {
            EraserTool.setupDrawing(drawingArea);
        });

        // choose color grabber tool
        colorGrabberItem.setOnAction(event -> {
            ColorGrabber.setup(
                    drawingArea,
                    colorPicker,
                    colorText
            );
        });
        
        // choose the diamond drawing tool
        diamondItem.setOnAction(event -> {
            DiamondTool.setupDrawing(drawingArea);
        });
        
        // choose the star drawing tool
        starItem.setOnAction(event -> {
            StarTool.setupDrawing(drawingArea);
        });
        
        // choose the polygon drawing tool
        polygonItem.setOnAction(event -> {
            RegularPolygonTool.setupDrawing(drawingArea);
        });
        
        // turn dash lines on or off 
        dashedItem.setOnAction(event -> {

            // if selected 
            if (dashedItem.isSelected()) {

                DashedOutline.turnOn(
                        drawingArea
                );

            } else {
                
                // return to normal if not 
                DashedOutline.turnOff(
                        drawingArea
                );
            }
        });
        
        // add current tab image to website
        selectWebImageItem.setOnAction(event -> {
            WebServer.selectCurrentImage();
        });

        // start web server
        startServerItem.setOnAction(event -> {
            WebServer.startServer();
        });

        // stop web server
        stopServerItem.setOnAction(event -> {
            WebServer.stopServer();
        });
        
        // create the slide used to change line width
        Label widthLabel = new Label("Line Width"); 
        Slider lineWidthSlider = new Slider(1,20,3);
        
        Label pixelLabel = new Label("3 px");

        // shows time left until autosave
        Label autosaveLabel = new Label("Autosave: 60 sec");
        
        // connect the slider to the line width controls
        WidthControls.setup(
                lineWidthSlider,
                pixelLabel,
                drawingArea
        );
        
        
        // show the numbers and tick marks on the line width slider
        lineWidthSlider.setShowTickLabels(true);
        lineWidthSlider.setShowTickMarks(true);
        
        
        // update the drawing line width whenever the slider changes 
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
            pixelLabel,
            colorLabel,
            colorPicker,
            colorText,
            autosaveLabel
                
        );
        
        // place the menu bar and drawing tools at the top of the window
        VBox topArea = new VBox(
            menuBar,
            tools
        );
        
        root.setTop(topArea);
        
        // update the drawing color when the user chooses a new color
        colorPicker.setOnAction(event -> {

        // update color used for drawing
        drawingArea.setLineColor(
                colorPicker.getValue()
        );
        
        // update the color info shown to user
        ColorTextLabel.update(
                colorPicker.getValue(),
                colorText
            );
        });
        
       // connect each menu item to its function file
      
       // connect Undo and Redo to their function file
        UndoRedo.setup(
                undoItem,
                redoItem
        );
        
        // connect Copy to its function file
        CopySelection.setup(
                copyItem
        );
        
        // paste the copied selection
        pasteItem.setOnAction(event -> {

            if (drawingArea != null) {

                PasteSelection.setupDrawing(
                        drawingArea
                );
            }
        });

        HelpFunction.setup(helpItem);

        AboutFunction.setup(aboutItem);

        OpenImage.setup(
                openItem,
                stage
        );

        ResizeFunction.setup(
                resizeItem
        );
        
        ClearCanvas.setup(
                clearItem
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
        
        // start autosave timer
        AutoSave.setup(
                saveItem,
                autosaveLabel
        );
        
        // show or hide autosave countdown
        showTimerItem.setOnAction(event -> {

            autosaveLabel.setVisible(
                    showTimerItem.isSelected()
            );
        });
        
        
        // connect New to the blank image function
        BlankImage.setup(newItem);

        
        
        // creates the scene 
        Scene scene = new Scene(root, 800, 600);
        
        // set window title and display the program
        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * Starts the Paint application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
    launch(args);
}

}
