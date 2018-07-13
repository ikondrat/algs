import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("arguments is null");
        }
        int lng = points.length,
            segmentsCount = 0;

        LineSegment[] lines = new LineSegment[lng];

        for (int i = 0; i < lng; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("point item is null");
            }
            String key = points[i].toString();
            for (int j = i+1; j < lng; j++) {
                double slope = points[i].slopeTo(points[j]);
            }
        }

        lineSegments = new LineSegment[segmentsCount];
        for (int i = 0; i < segmentsCount; i++) {
            lineSegments[i] = lines[i];
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
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