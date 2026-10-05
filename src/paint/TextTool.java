/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.util.Optional;
import javafx.scene.control.TextInputDialog;

/**
 * Allows the user to add text to the image.
 *
 * @author tanya
 */
public class TextTool {

    /**
     * Asks the user what text they want to add and then
     * allows them to click where the text should be placed.
     *
     * @param drawingArea the drawing area where the text is added
     */
    public static void setupDrawing(
            DrawingArea drawingArea) {

        // ask the user what text they want to add
        TextInputDialog dialog =
                new TextInputDialog();

        dialog.setTitle(
                "Add Text"
        );

        dialog.setHeaderText(
                "Enter the text you want to add"
        );

        dialog.setContentText(
                "Text:"
        );

        // wait for the user to enter text
        Optional<String> result =
                dialog.showAndWait();

        // stop if the user cancels
        if (result.isEmpty()) {
            return;
        }

        // save the text entered by the user
        String text =
                result.get();

        // stop if no text was entered
        if (text.isEmpty()) {
            return;
        }

        // turn off controls from the previous tool
        drawingArea.canvas.setOnMousePressed(null);
        drawingArea.canvas.setOnMouseReleased(null);
        drawingArea.canvas.setOnMouseDragged(null);
        drawingArea.canvas.setOnMouseClicked(null);

        // place the text where the user clicks
        drawingArea.canvas.setOnMouseClicked(
                event -> {

            // save current image before adding text
            UndoRedo.saveState(
                    drawingArea
            );

            // use the selected drawing color
            drawingArea.gc.setFill(
                    drawingArea.lineColor
            );

            // draw the text at the clicked location
            drawingArea.gc.fillText(
                    text,
                    event.getX(),
                    event.getY()
            );

            // mark drawing as changed
            drawingArea.modified = true;
        });
    }
}