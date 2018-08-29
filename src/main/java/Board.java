import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final String stringKey;
    private Board[] neighbors;
    private final int manhattanSum;
    private final int hammingCount;
    private final int dimension;
    private int zeroIndex;
    private Board twinBoard;
    private int[] blocks;
    // construct a board from an n-by-n array of blocks
    public Board(int[][] arr) {
        dimension = arr.length;
        blocks = new int[dimension*dimension];

        String strOut = "";
        strOut += dimension + "\n";
        short k = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (j > 0) {
                    strOut += " ";
                }
                strOut += arr[i][j];
                if (arr[i][j] == 0) {
                    zeroIndex = k;
                }
                blocks[k++] = arr[i][j];
            }
            strOut += "\n";
        }

        int[] copy = Arrays.copyOf(blocks, blocks.length);
        exch(copy, zeroIndex, copy.length - 1);
        Arrays.sort(copy, 0, blocks.length - 1);

        int sum = 0;
        int hc = 0;
        for (int i = 0; i < copy.length; i++) {
            int currentValue = blocks[i];
            if (copy[i] != currentValue && currentValue != 0) {
                int targetIndex = biSearch(copy, 0, copy.length - 1, currentValue);
                if (currentValue != 0) {
                    sum += getManhattanDistance(i, targetIndex, dimension);
                }
                hc++;
            }
        }
        hammingCount = hc;
        manhattanSum = sum;
        stringKey = strOut;
    }

    private static int[][] getMatrix(int[] arr, int n) {
        int[][] m = new int[n][n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = arr[k++];
            }
        }
        return m;
    }

    private static int biSearch(int[] arr, int from, int to, int targetValue) {
        int mid;
        do {
            mid = (from + to) >>> 1;
            if (arr[mid] < targetValue) from = mid + 1;
            if (arr[mid] > targetValue) to = mid - 1;
        } while (from <= to && arr[mid] != targetValue);
        return arr[mid] == targetValue ? mid : -1;
    }

    private static int getIndexByMatrixCoords(int x, int y, int n) {
        return (x * n) + y;
    }

    private static int[] getMatrixCoordsByIndex(int index, int n) {
        int[] coords = {
            (short) (index / n),
            (short) (index % n)
        };
        return coords;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingCount;
    }

    private int getMhDistance(int[] c1, int[] c2) {
        return Math.abs(c1[0] - c2[0]) + Math.abs(c2[1] - c1[1]);
    }

    private int getManhattanDistance(int fromIndex, int toIndex, int size) {
        int[] from = getMatrixCoordsByIndex(fromIndex, size);
        int[] to = getMatrixCoordsByIndex(toIndex, size);
        int mh = getMhDistance(
            from,
            to
        );
        return mh;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        if (twinBoard == null) {
            twinBoard = getTwinBoard(zeroIndex, blocks, dimension);
        }
        return twinBoard;
    }

    private static Board getTwinBoard(int zeroIndex, int[] m, int size) {
        int r1 = -1;
        int r2 = -1;
        for (int i = 0; i < size*size; i++) {
            if (i != zeroIndex && r1 == -1) {
                r1 = i;
                continue;
            }
            if (i != zeroIndex && r2 == -1) {
                r2 = i;
                break;
            }
        }
        exch(m, r1, r2);
        Board b = new Board(
            getMatrix(m, size)
        );
        exch(m, r1, r2);
        return b;
    }

    @Override
    public boolean equals(Object y) {
        if (Objects.isNull(y) || y.getClass() != getClass()) return false;
        Board that = (Board) y;
        return stringKey.equals(that.stringKey);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbors == null) {
            neighbors = findNeighbors(zeroIndex, blocks, dimension);
        }
        return () -> {
            return new NeighborIterator();
        };
    }

    private static void exch(int[] arr, int x, int y) {
        int v = arr[x];
        arr[x] = arr[y];
        arr[y] = v;
    }

    private static Board[] findNeighbors(int zeroIndex, int[] blocks, int n) {
        int[] zeroCoords = getMatrixCoordsByIndex(zeroIndex, n);
        int x = zeroCoords[0];
        int y = zeroCoords[1];

        int k = 0;
        Board[] boards = new Board[4];
        // left
        if (y > 0) {
            int targetI = getIndexByMatrixCoords(x, y - 1, n);
            exch(blocks, zeroIndex, targetI);
            boards[k++] = new Board(getMatrix(blocks, n));
            exch(blocks, zeroIndex, targetI);
        }
        // right
        if (y < n - 1) {
            int targetI = getIndexByMatrixCoords(x, y + 1, n);
            exch(blocks, zeroIndex, targetI);
            boards[k++] = new Board(getMatrix(blocks, n));
            exch(blocks, zeroIndex, targetI);
        }
        // top
        if (x > 0) {
            int targetI = getIndexByMatrixCoords(x - 1, y, n);
            exch(blocks, zeroIndex, targetI);
            boards[k++] = new Board(getMatrix(blocks, n));
            exch(blocks, zeroIndex, targetI);
        }
        // bottom
        if (x < n - 1) {
            int targetI = getIndexByMatrixCoords(x + 1, y, n);
            exch(blocks, zeroIndex, targetI);
            boards[k++] = new Board(getMatrix(blocks, n));
            exch(blocks, zeroIndex, targetI);
        }
        Board[] out = new Board[k];
        System.arraycopy(boards, 0, out, 0, k);
        return out;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        return stringKey;
    }

    private class NeighborIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < neighbors.length;
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return neighbors[index++];
            } else {
                throw new NoSuchElementException("There is no next neighbor.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of neighbors not supported.");
        }
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.print(initial.toString());
    }
}