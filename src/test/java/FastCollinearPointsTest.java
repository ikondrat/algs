/*
 * This Java source file was generated by the Gradle 'init' task.
 */
// import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static org.junit.Assert.*;

public class FastCollinearPointsTest {
    @Test(expected = IllegalArgumentException.class) public void testIllegalArgumentException() {
        FastCollinearPoints bp = new FastCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class) 
    public void testNullInCOnstructor() {
        Point[] points = new Point[2];
        points[0] = new Point(10, 20);
        points[1] = new Point(10, 20);
        FastCollinearPoints bp = new FastCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class) public void testNullPoint() {
        Point[] points = {
            new Point(10, 20),
            new Point(10, 25)
        };
        points[0] = null;
        FastCollinearPoints bp = new FastCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class) public void testDuplicatedPoint() {
        Point[] points = {
            new Point(10, 20),
            new Point(10, 20)
        };
        FastCollinearPoints bp = new FastCollinearPoints(points);
    }

    @Test 
    public void testBasic() {
        Point[] points = {
            new Point(0, 0),
            new Point(5, 5),
            new Point(2, 2),
            new Point(4, 4)
        };

        FastCollinearPoints bp = new FastCollinearPoints(points);
        assertEquals(
            1,
            bp.segments().length
        );
    }
}
