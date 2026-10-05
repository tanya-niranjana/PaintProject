/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * Controls the line width and displays the
 * selected width in pixels.
 *
 * @author tanya
 */
public class WidthControls {

    /**
     * Sets up the line width slider and updates the
     * pixel label when the slider value changes.
     *
     * @param lineWidthSlider the slider used to choose the line width
     * @param pixelLabel the label that displays the current width in pixels
     * @param drawingArea the drawing area used by the Paint program
     */
    public static void setup(
            Slider lineWidthSlider,
            Label pixelLabel,
            DrawingArea drawingArea) {

        // display the starting line width in pixels
        pixelLabel.setText(
                (int) lineWidthSlider.getValue()
                + " px"
        );

        // update the line width when the slider changes
        lineWidthSlider.valueProperty()
                .addListener(
                (observable, oldValue, newValue) -> {

            // only change the width if an image tab is open
            if (GuiImage.drawingArea != null) {

                GuiImage.drawingArea.setLineWidth(
                        newValue.doubleValue()
                );
            }

            // display the new width in pixels
            pixelLabel.setText(
                    newValue.intValue()
                    + " px"
            );
        });
    }
}
