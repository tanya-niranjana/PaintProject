/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;


/**
 * Handles multiple image tabs in the Paint program.
 * 
 * @author tanya
 */
public class ImageTabs {

    private static TabPane tabPane;

    // keeps track of the number used for blank image names
    private static int untitledNumber = 1;

    // stores the information that belongs to each tab
    private static class TabData {

        File file; // stores the image file for this tab
        byte[] imageData; // stores the og image file contents
        DrawingArea drawingArea; // stores the drawing area for this tab

        
        /**
         * Creates the data that belongs to one image tab.
         *
         * @param file the image file opened in the tab
         * @param imageData the original image file contents
         * @param drawingArea the drawing area used by the tab
         */
        TabData(
                File file,
                byte[] imageData,
                DrawingArea drawingArea) {

            this.file = file; // save file belonging to this tab

            this.imageData = imageData; // save the image data belonging to this tab

            this.drawingArea = drawingArea; // save the drawing area belonging to the tab
        }
    }


    /**
     * Creates the TabPane used to hold multiple images.
     * Also updates the current image whenever the user changes tabs.
     *
     * @return the TabPane used by the Paint program
     */
    public static TabPane createTabPane() {

        // create the area that will hold all image tabs
        tabPane = new TabPane();

        // update the current image when the user changes tabs
        tabPane.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                (observable, oldTab, newTab) -> {

            // only update the current image if a tab is selected
            if (newTab != null) {

                // get the information stored in the selected tab
                TabData data =
                        (TabData) newTab.getUserData();

                // make this tab's file the current file
                GuiImage.currentFile =
                        data.file;
                
                // make this tab's image data the current image data
                GuiImage.imageData =
                        data.imageData;

                // make this tab's drawing area the current drawing area
                GuiImage.drawingArea =
                        data.drawingArea;
            }
        });

        // start the program with a blank image
        openBlankImage(
                800,
                600
        );

        // return the completed tab area
        return tabPane;
    }


    /**
     * Opens a blank image inside a new tab.
     *
     * @param width the width of the blank drawing area
     * @param height the height of the blank drawing area
     */
    public static void openBlankImage(
            double width,
            double height) {

        // create a new drawing area
        DrawingArea newDrawingArea =
                new DrawingArea();

        // make the canvas the requested size
        newDrawingArea.canvas.setWidth(
                width
        );

        newDrawingArea.canvas.setHeight(
                height
        );

        // make the drawing area match the canvas size
        newDrawingArea.drawingPane.setPrefSize(
                width,
                height
        );

        // straight line is the default drawing tool
        SetUpDrawing.setupDrawing(
                newDrawingArea
        );

        // create a name for the blank tab
        String tabName =
                "Untitled "
                + untitledNumber;

        // increase the number for the next blank tab
        untitledNumber++;

        // create the new blank tab
        Tab newTab =
                new Tab(tabName);

        // put the blank drawing area inside the tab
        newTab.setContent(
                newDrawingArea.getView()
        );

        // blank images do not have a file or original image data yet
        newTab.setUserData(
                new TabData(
                        null,
                        null,
                        newDrawingArea
                )
        );

        // allow the tab to be closed
        newTab.setClosable(true);

        // add the new tab
        tabPane.getTabs().add(
                newTab
        );

        // automatically select the blank tab
        tabPane.getSelectionModel()
                .select(newTab);
    }


    /**
     * Opens an image and places it inside a new tab.
     *
     * @param file the image file that will be opened
     */
    public static void openImage(File file) {

        // only open the image if a file was selected
        if (file != null) {

            // create a new drawing area for this image
            DrawingArea newDrawingArea =
                    new DrawingArea();

            // straight line is the default drawing tool
            SetUpDrawing.setupDrawing(
                    newDrawingArea
            );

            // create an image from the selected file
            Image image =
                    new Image(
                            file.toURI().toString()
                    );

            // display the image inside the new drawing area
            newDrawingArea.setImage(image);

            try {

                // read and store the og image file contents
                byte[] newImageData =
                        Files.readAllBytes(
                                file.toPath()
                        );

                // create the new tab
                Tab newTab =
                        new Tab(file.getName());

                // put the drawing area inside the tab
                newTab.setContent(
                        newDrawingArea.getView()
                );

                // store this tab's image information
                newTab.setUserData(
                        new TabData(
                                file,
                                newImageData,
                                newDrawingArea
                        )
                );

                // allow the tab to be closed
                newTab.setClosable(true);

                // add the new tab
                tabPane.getTabs().add(
                        newTab
                );

                // automatically select the new tab
                tabPane.getSelectionModel()
                        .select(newTab);

            } catch (IOException e) {

                // display an error message if the file cannot be opened
                System.out.println(
                        "Error opening image."
                );
            }
        }
    }


    /**
     * Updates the current tab after the user uses Save As.
     * Also changes the tab title to the new file name.
     *
     * @param newFile the new file used for the current image
     */
    public static void updateCurrentFile(
            File newFile) {

        // get the currently selected tab
        Tab selectedTab =
                tabPane.getSelectionModel()
                        .getSelectedItem();

        // only update the file if a tab is selected
        if (selectedTab != null) {

            // get the info stored in the selected tab
            TabData data =
                    (TabData)
                    selectedTab.getUserData();

            // update the file stored by this tab
            data.file = newFile;

            // change the tab name to the new file name
            selectedTab.setText(
                    newFile.getName()
            );
            
            // make the new file the current file
            GuiImage.currentFile =
                    newFile;
        }
    }
}