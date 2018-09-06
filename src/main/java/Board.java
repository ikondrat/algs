import java.util.ArrayList;

public class Board {
    private int manhattanSum = 0;
    private int hammingCount = 0;
    private final int dimension;
    private int zeroIndex;
    private ArrayList<Board> nBoards;
    private final int[] blocks;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] arr) {
        dimension = arr.length;
        int n = dimension*dimension;
        blocks = new int[n];

        int k = 0;
        int[] sorted = new int[n];
        for (short i = 0; i < dimension; i++) {
            for (short j = 0; j < dimension; j++) {
                if (arr[i][j] == 0) {
                    zeroIndex = k;
                }
                int v = arr[i][j];
                blocks[k] = v;
                sorted[k] = v;
                int m = k;
                while (m > 0 && (sorted[m - 1] == 0 || sorted[m] != 0 && sorted[m] < sorted[m - 1])) {
                    exch(sorted, m, m - 1);
                    m--;
                }
                k++;
            }
        }

        for (short i = 0; i < sorted.length; i++) {
            int currentValue = blocks[i];
            if (sorted[i] != currentValue && currentValue != 0) {
                int targetIndex = biSearch(sorted, 0, sorted.length - 1, currentValue);
                manhattanSum += getManhattanDistance(i, targetIndex, dimension);
                hammingCount++;
            }
        }
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
        return hammingCount == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        return getTwinBoard(zeroIndex, blocks, dimension);
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
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension != that.dimension) return false;
        for (int i = 0; i < this.blocks.length; i++) {
            if (this.blocks[i] != that.blocks[i]) return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (nBoards == null) {
            nBoards = findNeighbors(zeroIndex, blocks, dimension);
        }
        return nBoards;
    }

    private static void exch(int[] arr, int x, int y) {
        arr[x] = arr[x] ^ arr[y];
        arr[y] = arr[x] ^ arr[y];
        arr[x] = arr[x] ^ arr[y];
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
}