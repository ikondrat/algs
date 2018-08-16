import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] spoints) {
        double previousSlope = Double.NEGATIVE_INFINITY;
        Point sortPoint;
        Point comparePoint;

        validate(spoints);
        ArrayList<LineSegment> segments = new ArrayList<>();

        // initial sort
        Arrays.sort(spoints);
        
        for (int i = 0; i < spoints.length; i++) {
            sortPoint = spoints[i];
            // group the points by slope
            Arrays.sort(spoints, i, spoints.length, sortPoint.slopeOrder());
            int cpCount = 0;
            int fromPoint = i + 1;
            for (int j = i + 1; j < spoints.length; j++) {
                comparePoint = spoints[j];
                double slope = sortPoint.slopeTo(comparePoint);

                if (!Double.isInfinite(previousSlope) && !equals(slope, previousSlope)) {
                    if (cpCount >= 3) {
                        segments.add(
                            getSegment(sortPoint, spoints, fromPoint, j - 1)
                        );
                    }
                    cpCount = 0;
                    fromPoint = j;
                }
                previousSlope = slope;
                cpCount++;
            }
            if (cpCount >= 3) {
                segments.add(
                    getSegment(sortPoint, spoints, spoints.length - cpCount, spoints.length - 1)
                );
            }
        }
        lineSegments = new LineSegment[segments.size()];
        segments.toArray(lineSegments);
    }

    private boolean equals(double x, double y) {
        return ((Double) x).equals(y);
    }

    private LineSegment getSegment(Point sortPoint, Point[] arr, int from, int to) {
        Arrays.sort(arr, from, to + 1);

        Point fromPoint = (sortPoint.compareTo(arr[from]) == -1) ? 
            sortPoint : 
            arr[from];
        Point toPoint = (sortPoint.compareTo(arr[to]) == 1) ? sortPoint : arr[to];
        LineSegment ls = new LineSegment(
            fromPoint,
            toPoint
        );
        return ls;
    }

    private void validate(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("arguments is null");
        }
        ArrayList<Point> pKeys = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("one of the Point is null");
            }
            for (int j = 0; j < pKeys.size(); j++) {
                if (pKeys.get(j).compareTo(points[i]) == 0) {
                    throw new java.lang.IllegalArgumentException("duplicated point");
                }
            }
            pKeys.add(points[i]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }