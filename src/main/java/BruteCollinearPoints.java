import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private final Point[] p;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        this.p = new Point[points.length];
        for (int i = 0; i > points.length; i++) {
            this.p[i] = points[i];
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return 1;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[this.p.length];

        return lines;
    }

    public static void main(String[] args) {
        // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    }
 }