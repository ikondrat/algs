import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private final ArrayList<String> lineSegmentsKeys;
    private final ArrayList<LineSegment> lineSegmentsValues;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("arguments is null");
        }

        Arrays.sort(points);
        lineSegmentsKeys = new ArrayList<>();
        lineSegmentsValues = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            ArrayList<Double> slopes = new ArrayList<>();
            ArrayList<ArrayList<Integer>> slopesPointsIndexes = new ArrayList<>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                double slopeKey = points[i].slopeTo(points[j]);
                if (slopes.contains(slopeKey)) {
                    int index = slopes.indexOf(slopeKey);
                    ArrayList<Integer> p = slopesPointsIndexes.get(index);
                    p.add(j);
                } else {
                    slopes.add(slopeKey);
                    int index = slopes.indexOf(slopeKey);
                    ArrayList<Integer> p = new ArrayList<>();
                    p.add(i);
                    p.add(j);
                    slopesPointsIndexes.add(index, p);
                }
            }

            slopesPointsIndexes.forEach(sp -> {
                if (sp.size() > 3) {
                    LineSegment ls = new LineSegment(
                        points[sp.get(0)], 
                        points[sp.get(sp.size() - 1)]
                    );

                    String k = ls.toString();

                    if (!lineSegmentsKeys.contains(k)) {
                        lineSegmentsKeys.add(k);
                        int index = lineSegmentsKeys.indexOf(k);
                        lineSegmentsValues.add(index, ls);
                    }
                }
            });
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentsValues.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] sgArr = new LineSegment[lineSegmentsValues.size()];
        return lineSegmentsValues.toArray(sgArr);
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