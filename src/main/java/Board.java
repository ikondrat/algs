import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] blocks;
    private final short n;
    private String stringKey;
    private Board[] neighbors;
    private short manhattanSum;
    private short hammingCount;
    private short[] zeroCoords;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] arr) {
        n = (short) arr.length;
        blocks = copy(arr);
        int[] plainArr = new int[n*n];
        short k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] == 0) {
                    zeroCoords = new short[]{(short) i,(short) j};
                    continue;
                }
                plainArr[k++] = arr[i][j];
            }
        }
        Arrays.sort(plainArr, 0, plainArr.length - 1);

        short index = 0;
        for (int sortedValue: plainArr) {
            short[] sc = getMatrixCoordsByIndex(index, n);
            int currentValue = arr[sc[0]][sc[1]];
            if (sortedValue != currentValue && currentValue != 0) {
                int targetCoords = biSearch(plainArr, 0, plainArr.length - 1, currentValue);
                manhattanSum += getManhattanDistance(index, targetCoords);
                hammingCount++;
            }
            index++;
        }
        plainArr = null;
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

    private static short[] getMatrixCoordsByIndex(int index, int n) {
        short[] coords = new short[]{
            (short) (index / n),
            (short) (index % n)
        };
        return coords;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingCount;
    }

    private int getMhDistance(short[] c1, short[] c2) {
        return Math.abs(c1[0] - c2[0]) + Math.abs(c2[1] - c1[1]);
    }

    private int getManhattanDistance(int fromIndex, int toIndex) {
        short[] from = getMatrixCoordsByIndex(fromIndex, n);
        short[] to = getMatrixCoordsByIndex(toIndex, n);
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

    private static int[][] copy(int[][] arr) {
        int[][] copy = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            System.arraycopy(arr[i], 0, copy[i], 0, arr[i].length);
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        return getTwinBoard(zeroCoords, blocks, n);
    }

    private static Board getTwinBoard(short[] zeroCoords, int[][] m, short size) {
        int r1 = -1;
        int r2 = -1;
        int zeroIndex = getIndexByMatrixCoords(zeroCoords[0], zeroCoords[1], size);
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
        short[] b1 = getMatrixCoordsByIndex(r1, size);
        short[] b2 = getMatrixCoordsByIndex(r2, size);

        exch(m, b1[0], b1[1], b2[0], b2[1]);
        Board b = new Board(
            m
        );
        exch(m, b1[0], b1[1], b2[0], b2[1]);
        return b;
    }

    @Override
    public boolean equals(Object y) {
        if (Objects.isNull(y) || !y.getClass().equals(this.getClass())) return false;
        Board x = (Board) y;
        if (x.n != n || x.hammingCount != hammingCount || x.manhattanSum != manhattanSum) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (x.blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return () -> {
            if (neighbors == null) {
                neighbors = findNeighbors(zeroCoords, blocks, n);
            }
            return new NeighborIterator();
        };
    }

    private static void exch(int[][] arr, int x1, int y1, int x2, int y2) {
        arr[x1][y1] = arr[x1][y1] ^ arr[x2][y2];
        arr[x2][y2] = arr[x1][y1] ^ arr[x2][y2];
        arr[x1][y1] = arr[x1][y1] ^ arr[x2][y2];
    }

    private static Board[] findNeighbors(short[] zeroCoords, int[][] blocks, int n) {
        ArrayList<Board> foundNeighbors = new ArrayList<>();
        short x = zeroCoords[0];
        short y = zeroCoords[1];
        // left
        if (y > 0) {
            exch(blocks, x, y, x, y - 1);
            foundNeighbors.add(
                new Board(
                    blocks
                )
            );
            exch(blocks, x, y, x, y - 1);
        }
        // right
        if (y < n - 1) {
            exch(blocks, x, y, x, y + 1);
            foundNeighbors.add(
                new Board(
                    blocks
                )
            );
            exch(blocks, x, y, x, y + 1);
        }
        // top
        if (x > 0) {
            exch(blocks, x, y, x - 1, y);
            foundNeighbors.add(
                new Board(
                    blocks
                )
            );
            exch(blocks, x, y, x - 1, y);
        }
        // bottom
        if (x < n - 1) {
            exch(blocks, x, y, x + 1, y);
            foundNeighbors.add(
                new Board(
                    blocks
                )
            );
            exch(blocks, x, y, x + 1, y);
        }
        return foundNeighbors.toArray(new Board[foundNeighbors.size()]);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        if (stringKey != null) return stringKey;
        StringBuilder output = new StringBuilder();
        output.append(String.format("%d\n", n));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j > 0) {
                    output.append(" ");
                }
                output.append(String.format("%d", blocks[i][j]));
            }
            output.append("\n");
        }
        stringKey = output.toString();
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