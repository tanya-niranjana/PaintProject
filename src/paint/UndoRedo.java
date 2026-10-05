/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Handles Undo and Redo.
 * Uses stacks to save previous and redone image states.
 *
 * @author tanya
 */
public class UndoRedo {

    /**
     * Sets up the Undo and Redo menu items and their
     * keyboard shortcuts.
     *
     * @param undoItem the menu item used to undo a change
     * @param redoItem the menu item used to redo a change
     */
    public static void setup(
            MenuItem undoItem,
            MenuItem redoItem) {

        // Ctrl + Z performs Undo
        undoItem.setAccelerator(
                new KeyCodeCombination(
                        KeyCode.Z,
                        KeyCombination.CONTROL_DOWN
                )
        );

        // Ctrl + Y performs Redo
        redoItem.setAccelerator(
                new KeyCodeCombination(
                        KeyCode.Y,
                        KeyCombination.CONTROL_DOWN
                )
        );

        // undo the most recent change
        undoItem.setOnAction(event -> {

            if (GuiImage.drawingArea != null) {

                undo(
                        GuiImage.drawingArea
                );
            }
        });

        // redo the most recently undone change
        redoItem.setOnAction(event -> {

            if (GuiImage.drawingArea != null) {

                redo(
                        GuiImage.drawingArea
                );
            }
        });
    }


    /**
     * Saves the current image before a change is made.
     *
     * @param drawingArea the drawing area whose current state is saved
     */
    public static void saveState(
            DrawingArea drawingArea) {

        if (drawingArea != null) {

            // save the current image on the Undo stack
            drawingArea.undoStack.push(
                    drawingArea.getEditedImage()
            );

            // a new change removes old Redo states
            drawingArea.redoStack.clear();
        }
    }


    /**
     * Restores the previous image state.
     *
     * @param drawingArea the drawing area being changed
     */
    public static void undo(
            DrawingArea drawingArea) {

        // only undo if a previous state exists
        if (!drawingArea.undoStack.empty()) {

            // save the current state so it can be redone
            drawingArea.redoStack.push(
                    drawingArea.getEditedImage()
            );

            // get the previous image from the Undo stack
            WritableImage previousImage =
                    drawingArea.undoStack.pop();

            // restore the previous image
            restoreImage(
                    drawingArea,
                    previousImage
            );
        }
    }


    /**
     * Restores an image state that was previously undone.
     *
     * @param drawingArea the drawing area being changed
     */
    public static void redo(
            DrawingArea drawingArea) {

        // only redo if a Redo state exists
        if (!drawingArea.redoStack.empty()) {

            // save the current state back onto the Undo stack
            drawingArea.undoStack.push(
                    drawingArea.getEditedImage()
            );

            // get the next image from the Redo stack
            WritableImage nextImage =
                    drawingArea.redoStack.pop();

            // restore the redone image
            restoreImage(
                    drawingArea,
                    nextImage
            );
        }
    }


    /**
     * Restores a saved image state to the drawing area.
     *
     * @param drawingArea the drawing area being restored
     * @param image the saved image state being restored
     */
    private static void restoreImage(
            DrawingArea drawingArea,
            WritableImage image) {

        // place the saved image into the image display
        drawingArea.imageView.setImage(image);

        // resize the canvas to match the restored image
        drawingArea.canvas.setWidth(
                image.getWidth()
        );

        drawingArea.canvas.setHeight(
                image.getHeight()
        );

        // clear the canvas because the restored image
        // already contains the previous drawings
        drawingArea.gc.clearRect(
                0,
                0,
                image.getWidth(),
                image.getHeight()
        );

        // make the drawing area match the restored image size
        drawingArea.drawingPane.setPrefSize(
                image.getWidth(),
                image.getHeight()
        );

        // mark the image as changed
        drawingArea.modified = true;
    }
}