import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x0, int y0) {
        this.x = x0;
        this.y = y0;
    }

    // draws this point
    public void draw() {
        StdDraw.point(this.x, this.y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        String str = this.x + "," + this.y;
        return str;
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        else if (this.y == that.y && this.x < that.x) return -1;
        else if (this.y == that.y && this.x == that.x) return 0;
        else return 1;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            return this.y == that.y ?
                    Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        // avoid "-0.0".
        if (this.y == that.y) {
            return 0.0;
        }

        return (that.y - this.y) * 1.0/(that.x - this.x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder(this);
    }

    private static class SlopeOrder implements Comparator<Point> {
        private final Point point;

        SlopeOrder(Point point) {
            this.point = point;
        }

        @Override
        public int compare(Point p1, Point p2) {
            double slope1 = p1.slopeTo(this.point);
            double slope2 = p2.slopeTo(this.point);
            return ((Double) slope1).equals(slope2) ? 0 : (slope1 > slope2 ? 1 : -1);
        }
    }
}