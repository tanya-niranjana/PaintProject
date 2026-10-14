/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 3 unit tests
 *
 * @author tanya
 */
public class ShapeCalculationTest {

    /**
     * Tests rectangle width and height.
     */
    @Test
    public void testRectangleSize() {

        double[] rectangle =
                ShapeCalculation.getRectangle(
                        10,
                        20,
                        110,
                        70
                );

        // rectangle should be 100 wide and 50 tall
        assertEquals(100, rectangle[2], 0.0001);
        assertEquals(50, rectangle[3], 0.0001);
    }

    /**
     * Tests that the square keeps equal sides.
     */
    @Test
    public void testSquareSize() {

        double[] square =
                ShapeCalculation.getSquare(
                        10,
                        10,
                        110,
                        70
                );

        // dragged area is 100 by 60
        // so square should be 60 by 60
        assertEquals(60, square[2], 0.0001);
        assertEquals(60, square[3], 0.0001);
    }

    /**
     * Tests the minimum number of points for a star.
     */
    @Test
    public void testStarMinimumPoints() {

        // 4 should work
        assertTrue(
                ShapeCalculation.validStarPoints(4)
        );

        // 3 should not work
        assertFalse(
                ShapeCalculation.validStarPoints(3)
        );
    }
}