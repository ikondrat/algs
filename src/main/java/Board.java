import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] blocks;
    private final int n;
    private final int[][] sortedBlocks;
    private final ArrayList<Integer> blocksToMove;
    private String stringKey;
    private Board[] neighbors;
    private int manhattanSum;
    private int zeroIndex;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] arr) {
        n = arr.length;
        blocks = arr;
        manhattanSum = -1;
        blocksToMove = new ArrayList<>();
        sortedBlocks = getSortedMatrix(blocks);
    }

    private int[][] getSortedMatrix(int[][] sourceMatrix) { 
        int[] temp = new int[n*n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = sourceMatrix[i][j];
                if (v == 0) {
                    zeroIndex = getIndexByMatrixCoords(i, j);
                    continue;
                }
                temp[k++] = v;
            }
        }
        Arrays.sort(temp);
        k = 1;
        // copy the elements of temp[]
        // one by one in mat[][]
        int[][] res = new int[n][n];
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (k < n*n) {
                    int v = temp[k++];
                    res[x][y] = v;
                    if (v != sourceMatrix[x][y]) {
                        int indx = getIndexByMatrixCoords(x, y);
                        blocksToMove.add(
                            indx
                        );
                    }
                }
                
            }
        }
        return res;
    }

    private int getIndexByMatrixCoords(int x, int y) {
        return (x * n) + y;
    }
    private int[] getMatrixCoordsByIndex(int index) {
        int[] coords = new int[]{
            index / n,
            index % n
        };
        return coords;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        return blocksToMove.size();
    }

    private int getMhDistance(int[] c1, int[] c2) {
        return Math.abs(c1[0] - c2[0]) + Math.abs(c2[1] - c1[1]);
    }

    private int[] getTarget(int index) {
        int[] target = new int[2];
        int[] tc = getMatrixCoordsByIndex(index);
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == sortedBlocks[tc[0]][tc[1]]) {
                    target[0] = i;
                    target[1] = j;
                }
            }
        }
        return target;
    }

    private int getManhattanDistanceForBlock(int index) {
        int[] target = getTarget(index);
        int[] source = getMatrixCoordsByIndex(index);
        int mh = getMhDistance(
            target,
            source
        );
        return mh;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattanSum != -1) return manhattanSum;
        manhattanSum = 0;
        for (int b: blocksToMove) {
            manhattanSum += getManhattanDistanceForBlock(b);
        }
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    private int[][] copy(int[][] arr) {
        int[][] copy = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            System.arraycopy(blocks[i], 0, copy[i], 0, blocks[i].length);
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int r1 = -1;
        int r2 = -1;
        for (int i = 0; i < n*n; i++) {
            if (i != zeroIndex && r1 == -1) {
                r1 = i;
                continue;
            }
            if (i != zeroIndex && r2 == -1) {
                r2 = i;
                break;
            }
        }
        int[] b1 = getMatrixCoordsByIndex(r1);
        int[] b2 = getMatrixCoordsByIndex(r2);
        int[][] copy = copy(blocks);
        exch(copy, b1[0], b1[1], b2[0], b2[1]);
        return new Board(
            copy
        );
    }

    @Override
    public boolean equals(Object y) {
        if (y == null) return false;
        return (y.toString()).equals(this.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return () -> {
            if (neighbors == null) {
                findNeighbors();
            }
            return new NeighborIterator();
        };
    }

    private void exch(int[][] arr, int x1, int y1, int x2, int y2) {
        int v = arr[x1][y1];
        arr[x1][y1] = arr[x2][y2];
        arr[x2][y2] = v;
    }

    private void findNeighbors() {
        ArrayList<Board> foundNeighbors = new ArrayList<>();
        int[] zcoords = getMatrixCoordsByIndex(zeroIndex);
        int x = zcoords[0];
        int y = zcoords[1];
        // left
        if (zcoords[1] > 0) {
            int[][] copy = copy(blocks);
            exch(copy, x, y, x, y - 1);
            foundNeighbors.add(
                new Board(
                    copy
                )
            );
        }
        // right
        if (zcoords[1] < n - 1) {
            int[][] copy = copy(blocks);
            exch(copy, x, y, x, y + 1);
            foundNeighbors.add(
                new Board(
                    copy
                )
            );
        }
        // top
        if (x > 0) {
            int[][] copy = copy(blocks);
            exch(copy, x, y, x - 1, y);
            foundNeighbors.add(
                new Board(
                    copy
                )
            );
        }
        // bottom
        if (x < n - 1) {
            int[][] copy = copy(blocks);
            exch(copy, x, y, x + 1, y);
            foundNeighbors.add(
                new Board(
                    copy
                )
            );
        }
        neighbors = foundNeighbors.toArray(new Board[foundNeighbors.size()]);
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