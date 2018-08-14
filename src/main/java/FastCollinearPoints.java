import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private static ArrayList<LineSegment> lineSegments;
    private static ArrayList<String> lineSegmentsKeys;

    public FastCollinearPoints(Point[] spoints) {
        validate(spoints);
        lineSegments = new ArrayList<>();
        lineSegmentsKeys = new ArrayList<>();
        double previousSlope = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < spoints.length; i++) {
            Point startPoint = spoints[i];
            ArrayList<Point> points = new ArrayList<>(Arrays.asList(spoints));
            ArrayList<Point> slopePoints = new ArrayList<>();
            points.sort(startPoint.slopeOrder());
            for (int j = 0; j < points.size(); j++) {
                Point comparePoint = points.get(j);
                double slope = startPoint.slopeTo(comparePoint);
                if (previousSlope != slope && previousSlope != Double.NEGATIVE_INFINITY) {
                    addSegment(slopePoints);
                    slopePoints.clear();
                    slopePoints.add(startPoint);
                }
                slopePoints.add(comparePoint);
                previousSlope = slope;
            }
            addSegment(slopePoints);
        }
    }

    private static void addSegment(ArrayList<Point> points) {
        if (points.size() >= 4) {
            Point[] ps = new Point[points.size()];

            points.toArray(ps);
            Arrays.sort(ps);
            LineSegment ls = new LineSegment(
                ps[0],
                ps[ps.length - 1]
            );
            String key = ls.toString();
            if (!lineSegmentsKeys.contains(key)) {
                lineSegments.add(ls);
                lineSegmentsKeys.add(key);
            }
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