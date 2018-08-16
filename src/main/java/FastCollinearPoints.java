import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;
    private final ArrayList<String> lineSegmentsKeys;

    public FastCollinearPoints(Point[] points) {
        double previousSlope = Double.NEGATIVE_INFINITY;
        Point sortPoint;
        Point comparePoint;

        validate(points);
        lineSegmentsKeys = new ArrayList<>();
        lineSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            sortPoint = points[i];

            Point[] spoints = Arrays.copyOf(points, points.length);
            // group the points by slope
            Arrays.sort(spoints, sortPoint.slopeOrder());
            int cpCount = 0;
            int fromPoint = 1;
            for (int j = 1; j < spoints.length; j++) {
                comparePoint = spoints[j];
                double slope = sortPoint.slopeTo(comparePoint);

                if (!Double.isInfinite(previousSlope) && !equals(slope, previousSlope)) {
                    if (cpCount >= 3) {
                        addIfNew(
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
                addIfNew(
                    getSegment(sortPoint, spoints, spoints.length - cpCount, spoints.length - 1)
                );
            }
        }
    }

    private void addIfNew(Point[] segmentPoints) {
        String key = getSlopeKey(segmentPoints[0], segmentPoints[1]);
        if (!lineSegmentsKeys.contains(key)) {
            lineSegments.add(new LineSegment(segmentPoints[0], segmentPoints[1]));
            lineSegmentsKeys.add(key);
        }
    }

    private String getSlopeKey(Point x, Point y) {
        return x.slopeTo(y) + ":" + x.toString()  + "-" + y.toString() + "";
    }

    private boolean equals(double x, double y) {
        return ((Double) x).equals(y);
    }

    private boolean less(Point x, Point y) {
        return x.compareTo(y) < 0;
    }

    private Point[] getSegment(Point sortPoint, Point[] arr, int from, int to) {
        Arrays.sort(arr, from, to + 1);

        Point fromPoint = less(sortPoint, arr[from]) ? 
            sortPoint : 
            arr[from];
        Point toPoint = less(arr[to], sortPoint) ? sortPoint : arr[to];
        Point[] arrP = new Point[]{fromPoint, toPoint};
        return arrP;
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
        LineSegment[] arr = new LineSegment[lineSegments.size()];
        lineSegments.toArray(arr);
        return arr;
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