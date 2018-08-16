import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validate(points);
        lineSegments = new ArrayList<>();
        

        for (int i = 0; i < points.length - 1; i++) {
            ArrayList<Double> slopes = new ArrayList<>();
            for (int j = i + 1; j < points.length; j++) {
                double key = points[i].slopeTo(points[j]);
                if (slopes.contains(key)) continue;
                slopes.add(key);
            }

            for (int j = 0; j < slopes.size(); j++) {
                double slope = slopes.get(j);
                int n = 1;
                Point[] spoints = new Point[4];
                spoints[0] = points[i];
                for (int y = i + 1; y < points.length && n < 5; y++) {
                    if (((Double) slope).equals(points[i].slopeTo(points[y]))) {
                        spoints[n++] = points[y];
                    }
                }
                if (((Integer) n).equals(4)) {
                    Arrays.sort(spoints);
                    lineSegments.add(new LineSegment(spoints[0], spoints[n - 1]));
                }
            }

        }
    }

    private void validate(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("arguments is null");
        }
        // ArrayList<Point> pKeys = new ArrayList<>();
        // for (int i = 0; i < points.length; i++) {
        //     if (points[i] == null) {
        //         throw new java.lang.IllegalArgumentException("one of the Point is null");
        //     }
        //     for (int j = 0; j < pKeys.size(); j++) {
        //         if (pKeys.get(j).compareTo(points[i]) == 0) {
        //             throw new java.lang.IllegalArgumentException("duplicated point");
        //         }
        //     }
        //     pKeys.add(points[i]);
        // }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }