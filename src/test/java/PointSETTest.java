import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Spliterator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSETTest {
    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullArgumentInInsert() {
        PointSET ps = new PointSET();
        ps.insert(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullArgumentInContains() {
        PointSET ps = new PointSET();
        ps.contains(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullArgumentInRange() {
        PointSET ps = new PointSET();
        ps.range(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullArgumentInNearest() {
        PointSET ps = new PointSET();
        ps.nearest(null);
    }

    @Test
    public void testIsEmpty() {
        PointSET ps = new PointSET();
        assertTrue(ps.isEmpty());
    }

    @Test
    public void testIsNotEmpty() {
        PointSET ps = new PointSET();
        Point2D x = new Point2D(0.2, 0.4);
        ps.insert(x);
        assertFalse(ps.isEmpty());
    }

    @Test
    public void testContainsFalse() {
        PointSET ps = new PointSET();
        Point2D x = new Point2D(0.2, 0.4);
        RectHV rect = new RectHV(0, 0, 0.7, 0.7);
        

        Spliterator<Point2D> split1 = ps.range(rect).spliterator(); 
        assertEquals(
            split1.estimateSize(),
            0
        );
    }

    @Test
    public void testContainsOne() {
        PointSET ps = new PointSET();
        Point2D x = new Point2D(0.2, 0.4);
        RectHV rect = new RectHV(0, 0, 0.7, 0.7);
        ps.insert(x);

        Spliterator<Point2D> split1 = ps.range(rect).spliterator(); 
        assertEquals(
            split1.estimateSize(),
            1
        );
    }

    @Test
    public void testContainsThree() {
        PointSET ps = new PointSET();
        Point2D x1 = new Point2D(0.2, 0.4);
        Point2D x2 = new Point2D(0.1, 0.1);
        Point2D x3 = new Point2D(0.5, 0.3);
        RectHV rect = new RectHV(0, 0, 0.7, 0.7);
        ps.insert(x1);
        ps.insert(x2);
        ps.insert(x3);

        Spliterator<Point2D> split1 = ps.range(rect).spliterator(); 
        assertEquals(
            split1.estimateSize(),
            3
        );
    }

    @Test
    public void testNearesPoint() {
        PointSET ps = new PointSET();
        Point2D x1 = new Point2D(0.2, 0.4);
        Point2D x2 = new Point2D(0.1, 0.1);
        Point2D x3 = new Point2D(0.5, 0.3);
        Point2D x4 = new Point2D(0.1, 0.2);
        ps.insert(x1);
        ps.insert(x2);
        ps.insert(x3);

        Point2D n = ps.nearest(x4);

        assertTrue(
            n.equals(x2)
        );
    }

    @Test
    public void testNearestOnEmptySet() {
        PointSET ps = new PointSET();
        Point2D x2 = new Point2D(0.1, 0.1);
        assertNull(
            ps.nearest(x2)
        );
    }

    @Test
    public void testDraw() {
        PointSET ps = new PointSET();
        Point2D x1 = new Point2D(0.2, 0.4);
        Point2D x2 = new Point2D(0.1, 0.1);
        Point2D x3 = new Point2D(0.5, 0.3);
        ps.insert(x1);
        ps.insert(x2);
        ps.insert(x3);
        ps.draw();
        assertTrue(true);
    }
}