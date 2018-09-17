import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Stack;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private final TreeSet<Point2D> points;
    // construct an empty set of points 
    public PointSET() {
        points = new TreeSet<>();
    }
    // is the set empty? 
    public boolean isEmpty() {
        return points.size() == 0;
    }
    // number of points in the set 
    public int size() {
        return points.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException(
                "The Point2D is expected as argument"
            );
        }
        points.add(p);
    }
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException(
                "The Point2D is expected as argument"
            );
        }
        return points.contains(p);
    }

    // draw all points to standard draw 
    public void draw() {
        StdDraw.clear();
        int n = size();
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setXscale(-0.05*n, 1.05*n);
        StdDraw.setYscale(-0.05*n, 1.05*n);   // leave a border to write text
        StdDraw.filledSquare(n/2.0, n/2.0, n/2.0);

        StdDraw.setPenColor(StdDraw.GREEN);
        for (Point2D point: points) {
            StdDraw.filledCircle(point.x(), point.y(), 0.01);
        }
        StdDraw.pause(1000);
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException(
                "The RectHV is expected as argument"
            );
        }
        Stack<Point2D> p = new Stack<>();

        for (Point2D point: points) {
            if (rect.contains(point)) p.add(point);
        }

        return p;
    }
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException(
                "The Point2D is expected as argument"
            );
        }
        if (points.size() == 0) return null;
        Point2D[] x = new Point2D[points.size()];
        points.toArray(x);

        Arrays.sort(x, new DistanceOrder(p));
        return x[0];
    }

    private class DistanceOrder implements Comparator<Point2D> {
        private final Point2D point;

        DistanceOrder(Point2D p) {
            this.point = p;
        }

        @Override
        public int compare(Point2D p1, Point2D p2) {
            double mh1 = getManhattanDistance(this.point, p1);
            double mh2 = getManhattanDistance(this.point, p2);
            return ((Double) mh1).equals(mh2) ? 0 : (mh1 > mh2 ? 1 : -1);
        }
    }

    private double getManhattanDistance(Point2D point1, Point2D  point2) {
        return (
            Math.abs(point1.x() - point2.x()) + 
            Math.abs(point1.y() - point2.y())
        );
    }
}