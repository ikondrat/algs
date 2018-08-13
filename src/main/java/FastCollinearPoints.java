import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;
    private final ArrayList<String> lineSegmentsKeys;

    public FastCollinearPoints(Point[] spoints) {
        validate(spoints);
        lineSegments = new ArrayList<>();
        lineSegmentsKeys = new ArrayList<>();
        double previousSlope = Double.NEGATIVE_INFINITY;

        Point[] points = Arrays.copyOf(spoints, spoints.length);
        for (Point startPoint : spoints) {
            ArrayList<Point> slopePoints = new ArrayList<>();
            Arrays.sort(points, startPoint.slopeOrder());
            for (Point comparePoint : points) {
                if (startPoint.equals(comparePoint)) continue;
                double slope = startPoint.slopeTo(comparePoint);
                if (previousSlope != slope && previousSlope != Double.NEGATIVE_INFINITY) {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(startPoint);
                        Collections.sort(slopePoints);
                        LineSegment ls = new LineSegment(
                            slopePoints.get(0),
                            slopePoints.get(slopePoints.size() - 1)
                        );
                        String key = ls.toString();
                        if (!lineSegmentsKeys.contains(key)) {
                            lineSegments.add(ls);
                            lineSegmentsKeys.add(key);
                        }
                    }
                    slopePoints.clear();
                }
                slopePoints.add(comparePoint);
                previousSlope = slope;
            }
            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                Collections.sort(slopePoints);
                LineSegment ls = new LineSegment(
                    slopePoints.get(0),
                    slopePoints.get(slopePoints.size() - 1)
                );
                String key = ls.toString();
                if (!lineSegmentsKeys.contains(key)) {
                    lineSegments.add(ls);
                    lineSegmentsKeys.add(key);
                }
            }
        }
        // for (int i = 0; i < spoints.length; i++) {
        //     Arrays.sort(spoints, i + 1, spoints.length - 1, spoints[i].slopeOrder());
        //     Point startPoint = spoints[i];

        //     int n = 1;
        //     for (int j = 0; j < spoints.length; j++) {
        //         if (i == j) continue;
        //         Point comparePoint = spoints[j];
        //         double slope = startPoint.slopeTo(comparePoint);

        //         if (!((Double) slope).equals(previousSlope)) {
        //             if (n >= 3) {
        //                 int index = j - n;
        //                 Arrays.sort(spoints, index, j);
        //                 lineSegments.add(new LineSegment(spoints[j - n], spoints[j]));
        //             }
        //             n = 0;
        //         }
        //         previousSlope = slope;
        //         n++;
        //     }
        // }
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