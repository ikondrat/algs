import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int manhattanSum;
    private final int hammingCount;
    private final int dimension;
    private final boolean isSorted;
    private final int key;
    private final int currentClass;
    private int zeroIndex;
    private ArrayList<Board> nBoards;
    private Board twinBoard;
    private final int[] blocks;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] arr) {
        dimension = arr.length;
        blocks = new int[dimension*dimension];

        int k = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (arr[i][j] == 0) {
                    zeroIndex = k;
                }
                blocks[k++] = arr[i][j];
            }
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
                sum += getManhattanDistance(i, targetIndex, dimension);
                hc++;
            }
        }
        hammingCount = hc;
        manhattanSum = sum;
        key = Arrays.hashCode(blocks);
        isSorted = hammingCount == 0;
        currentClass = getClass().hashCode();
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

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingCount;
    }

    private int getManhattanDistance(int fromIndex, int toIndex, int size) {
        return (
            Math.abs((fromIndex / size) - (toIndex / size)) + 
            Math.abs((fromIndex % size) - (toIndex % size))
        );
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return isSorted;
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
        if (y == null || y.getClass().hashCode() != currentClass) return false;
        Board that = (Board) y;
        return key == that.key;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (nBoards == null) {
            nBoards = findNeighbors(zeroIndex, blocks, dimension);
        }
        return nBoards;
    }

    private static void exch(int[] arr, int x, int y) {
        int v = arr[x];
        arr[x] = arr[y];
        arr[y] = v;
    }

    private static ArrayList<Board> findNeighbors(int zeroIndex, int[] blocks, int n) {
        int x = (zeroIndex / n);
        int y = (zeroIndex % n);
        ArrayList<Board> nb = new ArrayList<>();
        // left
        if (y > 0) {
            int targetI = (x * n) + y - 1;
            exch(blocks, zeroIndex, targetI);
            nb.add(new Board(getMatrix(blocks, n)));
            exch(blocks, zeroIndex, targetI);
        }
        // right
        if (y < n - 1) {
            int targetI = (x * n) + y + 1;
            exch(blocks, zeroIndex, targetI);
            nb.add(new Board(getMatrix(blocks, n)));
            exch(blocks, zeroIndex, targetI);
        }
        // top
        if (x > 0) {
            int targetI = ((x - 1) * n) + y;
            exch(blocks, zeroIndex, targetI);
            nb.add(new Board(getMatrix(blocks, n)));
            exch(blocks, zeroIndex, targetI);
        }
        // bottom
        if (x < n - 1) {
            int targetI = ((x + 1) * n) + y;
            exch(blocks, zeroIndex, targetI);
            nb.add(new Board(getMatrix(blocks, n)));
            exch(blocks, zeroIndex, targetI);
        }
        return nb;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder stringB = new StringBuilder();
        stringB.append(dimension);
        for (int i = 0; i < blocks.length; i++) {
            if (i % dimension == 0) {
                stringB.append("\n");
            } else {
                stringB.append(" ");
            }
            stringB.append(blocks[i]);
        }
        stringB.append("\n");
        return stringB.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
    }
}