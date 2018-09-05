/*
 * This Java source file was generated by the Gradle 'init' task.
 */
// import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static org.junit.Assert.*;

public class BruteCollinearPointsTest {
    @Test(expected = IllegalArgumentException.class) public void testIllegalArgumentException() {
        new BruteCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class) 
    public void testNullInCOnstructor() {
        Point[] points = new Point[2];
        points[0] = new Point(10, 20);
        points[1] = new Point(10, 20);
        new BruteCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class) public void testNullPoint() {
        Point[] points = {
            new Point(10, 20),
            new Point(10, 25)
        };
        points[0] = null;
        new BruteCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class) public void testDuplicatedPoint() {
        Point[] points = {
            new Point(10, 20),
            new Point(10, 20)
        };
        new BruteCollinearPoints(points);
    }

    @Test 
    public void testBasic() {
        Point[] points = {
            new Point(0, 0),
            new Point(5, 5),
            new Point(2, 2),
            new Point(4, 4)
        };

        BruteCollinearPoints bp = new BruteCollinearPoints(points);
        assertEquals(
            1,
            bp.segments().length
        );
    }
}
