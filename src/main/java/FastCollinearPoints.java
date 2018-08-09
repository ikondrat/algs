import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class FastCollinearPoints {
    private HashMap<String, LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("arguments is null");
        }

        Arrays.sort(points);
        lineSegments = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            HashMap<Double, ArrayList<Integer>> hmap = new HashMap<>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                double slopeKey = points[i].slopeTo(points[j]);
                if (hmap.containsKey(slopeKey)) {
                    ArrayList<Integer> p = hmap.get(slopeKey);
                    p.add(j);
                } else {
                    ArrayList<Integer> p = new ArrayList<>();
                    p.add(i);
                    p.add(j);
                    hmap.put(slopeKey, p);
                }
            }

            for (Map.Entry<Double, ArrayList<Integer>> entry : hmap.entrySet()) {
                ArrayList<Integer> slopePoints = entry.getValue();
                
                if (slopePoints.size() > 3) {
                    Collections.sort(slopePoints);
                    LineSegment ls = new LineSegment(
                        points[slopePoints.get(0)], 
                        points[slopePoints.get(slopePoints.size() - 1)]
                    );
                    String k = ls.toString();
                    if (!lineSegments.containsKey(k)) {
                        lineSegments.put(k, ls);
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] sgArr = new LineSegment[lineSegments.size()];
        return lineSegments.values().toArray(sgArr);
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