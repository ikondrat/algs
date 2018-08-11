import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] spoints) {
        validate(spoints);
        lineSegments = new ArrayList<>();
        Point[] points;

        points = Arrays.copyOf(spoints, spoints.length);
        for (Point startPoint : spoints) {
            Arrays.sort(points, startPoint.slopeOrder());

            ArrayList<Point> slopePoints = new ArrayList<>();
            double previousSlope = Double.NEGATIVE_INFINITY;
            slopePoints.add(startPoint);

            for (int i = 1; i < points.length; i++) {
                Point comparePoint = points[i];
                double slope = startPoint.slopeTo(comparePoint);

                boolean isLast = i == points.length - 1;
                if (i > 1 && (!((Double) slope).equals(previousSlope) || isLast)) {
                    int n = slopePoints.size();
                    if (n >= 3) {
                        lineSegments.add(new LineSegment(slopePoints.get(0), slopePoints.get(n - 1)));
                    }
                    if (!isLast) {
                        slopePoints.clear();
                    }
                }
                slopePoints.add(comparePoint);
                previousSlope = slope;
            }
            points = Arrays.copyOfRange(spoints, 1, spoints.length - 1);
        }
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
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] sgArr = new LineSegment[lineSegments.size()];
        return lineSegments.toArray(sgArr);
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