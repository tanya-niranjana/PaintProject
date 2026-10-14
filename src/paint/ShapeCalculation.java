/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

/**
 * Holds simple calculations used for unit testing shapes.
 *
 * @author tanya
 */
public class ShapeCalculation {

    /**
     * Gets the location and size of a rectangle.
     *
     * @param startX starting x location
     * @param startY starting y location
     * @param endX ending x location
     * @param endY ending y location
     * @return x, y, width, and height
     */
    public static double[] getRectangle(
            double startX,
            double startY,
            double endX,
            double endY) {

        // find top left corner
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);

        // get width and height
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        return new double[] {
            x,
            y,
            width,
            height
        };
    }

    /**
     * Gets the location and size of a square.
     *
     * @param startX starting x location
     * @param startY starting y location
     * @param endX ending x location
     * @param endY ending y location
     * @return x, y, width, and height
     */
    public static double[] getSquare(
            double startX,
            double startY,
            double endX,
            double endY) {

        // get width and height of dragged area
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        // use smaller amount so it stays a square
        double size = Math.min(width, height);

        double x = startX;
        double y = startY;

        // adjust if dragged left
        if (endX < startX) {
            x = startX - size;
        }

        // adjust if dragged up
        if (endY < startY) {
            y = startY - size;
        }

        return new double[] {
            x,
            y,
            size,
            size
        };
    }

    /**
     * Checks if the number of star points is valid.
     *
     * @param points number of star points
     * @return true if there are at least 4 points
     */
    public static boolean validStarPoints(int points) {
        return points >= 4;
    }
}