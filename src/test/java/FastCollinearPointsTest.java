/*
 * This Java source file was generated by the Gradle 'init' task.
 */
// import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static org.junit.Assert.*;

public class FastCollinearPointsTest {
    @Test(expected = IllegalArgumentException.class) public void testIllegalArgumentException() {
       new FastCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class) 
    public void testNullInCOnstructor() {
        Point[] points = new Point[2];
        points[0] = new Point(10, 20);
        points[1] = new Point(10, 20);
        new FastCollinearPoints(null);
    }

    @Test(expected = IllegalArgumentException.class) public void testNullPoint() {
        Point[] points = {
            new Point(10, 20),
            new Point(10, 25)
        };
        points[0] = null;
        new FastCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class) public void testDuplicatedPoint() {
        Point[] points = {
            new Point(10, 20),
            new Point(10, 20)
        };
        new FastCollinearPoints(points);
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

    @Test
    public void testFor2Segments() {
        Point[] points = {
            new Point(0, 0),
            new Point(5, 5),
            new Point(2, 2),
            new Point(4, 4),
            new Point(1, 2),
            new Point(3, 2),
            new Point(4, 2)
        };

        FastCollinearPoints bp = new FastCollinearPoints(points);
        assertEquals(
            2,
            bp.segments().length
        );
    }

    @Test
    public void testSegmentCorectnes() {
        Point[] points = {
            new Point(2, 2),
            new Point(0, 0),
            new Point(5, 5),
            new Point(4, 4)
        };

        FastCollinearPoints bp = new FastCollinearPoints(points);
        assertEquals(
            "0,0 -> 5,5",
            bp.segments()[0].toString()
        );
    }

    @Test
    public void testForCorrectNumberOgSegments() {
        Point[] points = {
            new Point(10000, 0),
            new Point(0, 10000),
            new Point(3000, 7000),
            new Point(7000, 3000),
            new Point(20000, 21000),
            new Point(3000, 4000),
            new Point(14000, 15000),
            new Point(6000, 7000)
        };

        FastCollinearPoints bp = new FastCollinearPoints(points);
        assertEquals(
            2,
            bp.segments().length
        );
    }


    @Test
    public void testForCorrectSegmentsKeys() {
        Point[] points = {
            new Point(10000, 0),
            new Point(0, 10000),
            new Point(3000, 7000),
            new Point(7000, 3000),
            new Point(20000, 21000),
            new Point(3000, 4000),
            new Point(14000, 15000),
            new Point(6000, 7000)
        };
        FastCollinearPoints bp = new FastCollinearPoints(points);
        String res = bp.segments()[0].toString();
        assertEquals(
            "10000,0 -> 0,10000",
            res
        );
        assertEquals(
            "3000,4000 -> 20000,21000",
            bp.segments()[1].toString()
        );
    }
}
