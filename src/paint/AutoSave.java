/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;

/**
 * Handles the timer used for autosave.
 *
 * @author tanya
 */
public class AutoSave {

    // autosave every 60 seconds - 10 for demonstration purposes
    private static final int AUTOSAVE_TIME = 10;

    // current time left before autosave
    private static int secondsLeft = AUTOSAVE_TIME;

    private static Timeline timer;
    private static Label timerLabel;

    /**
     * Starts the autosave timer.
     *
     * @param saveItem the Save menu item
     * @param label label used to show the countdown
     */
    public static void setup(
            MenuItem saveItem,
            Label label) {

        timerLabel = label;

        // update timer every second
        timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {

                    secondsLeft--;

                    // update countdown shown to user
                    updateLabel();

                    // autosave when timer reaches zero
                    if (secondsLeft <= 0) {

                        // Only autosave if the image already has a file name.

                        if (GuiImage.currentFile != null
                                && GuiImage.drawingArea != null
                                && GuiImage.drawingArea.isModified()) {

                            saveItem.fire();
                        }

                        resetTimer();
                    }
                })
        );

        // keep timer running
        timer.setCycleCount(Timeline.INDEFINITE);

        timer.play();

        updateLabel();
    }

    /**
     * Resets the autosave timer.
     */
    public static void resetTimer() {

        secondsLeft = AUTOSAVE_TIME;

        updateLabel();
    }

    /**
     * Updates the countdown label.
     */
    private static void updateLabel() {

        if (timerLabel != null) {

            timerLabel.setText(
                    "Autosave: "
                    + secondsLeft
                    + " sec"
            );
        }
    }
}