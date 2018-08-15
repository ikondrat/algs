import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;
    private ArrayList<String> segmentsKeys;

    public FastCollinearPoints(Point[] spoints) {
        validate(spoints);
        double previousSlope = Double.NEGATIVE_INFINITY;
        segments = new ArrayList<>();
        segmentsKeys = new ArrayList<>();
        for (Point startPoint: spoints) {
            Point[] points = Arrays.copyOf(spoints, spoints.length);
            Arrays.sort(points, startPoint.slopeOrder());
            
            ArrayList<Double> slopes = new ArrayList<>();
            ArrayList<ArrayList<Point>> slopePoints = new ArrayList<>();
            for (int j = 1; j < points.length; j++) {
                Point comparePoint = points[j];
                double slope = startPoint.slopeTo(comparePoint);
                if (slopes.contains(slope)) {
                    int index = slopes.indexOf(slope);
                    slopePoints.get(index).add(comparePoint);
                } else {
                    slopes.add(slope);
                    int index = slopes.indexOf(slope);
                    ArrayList<Point> ps = new ArrayList<>();
                    ps.add(comparePoint);
                    slopePoints.add(index, ps);
                }
                previousSlope = slope;
            }
            
            for (double slope: slopes) {
                int index = slopes.indexOf(slope);
                int n = slopePoints.get(index).size();
                if (n >= 3) {
                    Point[] ps = new Point[n + 1];
                    slopePoints.get(index).toArray(ps);
                    ps[ps.length - 1] = startPoint;
                    Arrays.sort(ps);
                    String key = ps[0] + "," + ps[ps.length - 1];
                    if (!segmentsKeys.contains(key)) {
                        segments.add(
                            new LineSegment(ps[0], ps[ps.length - 1])
                        );
                        segmentsKeys.add(key);
                    }
                }
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
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] sgArr = new LineSegment[segments.size()];
        return segments.toArray(sgArr);
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