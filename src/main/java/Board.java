import java.util.Arrays;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int[] blocks;
    private final int n;
    private int[] goalBlocks;
    private Board[] neighbors;
    private int manhattanSum = -1;

    // construct a board from an n-by-n array of blocks
    public Board(int[][] arr) {
        n = arr.length;
        blocks = new int[n*n];
        goalBlocks = new int[n*n];

        int spaceIndex = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = arr[i][j];
                blocks[getIndexByMatrixCoords(i, j)] = v;
                if (v == 0) {
                    spaceIndex = getIndexByMatrixCoords(i, j);
                }
                goalBlocks[getIndexByMatrixCoords(i, j)] = v;
            }
        }
        exch(goalBlocks, spaceIndex, goalBlocks.length - 1);
        Arrays.sort(goalBlocks, 0, goalBlocks.length - 1);
        manhattanSum = this.manhattan();
    }

    private int[] getPlain(int[][] arr) {
        int[] plainArr = new int[n*n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                plainArr[getIndexByMatrixCoords(i, j)] = arr[i][j];
            }
        }
        return plainArr;
    }

    private void exch(int[] arr, int x, int y) {
        if (x > arr.length - 1) {
            throw new NoSuchElementException("x out of range");
        }
        if (y > arr.length - 1) {
            throw new NoSuchElementException("y out of range");
        }
        int v = arr[x];
        arr[x] = arr[y];
        arr[y] = v;
    }

    private int[][] getMatrix(int[] arr) {
        int[][] mtr = new int[n][n];
        for (int i = 0; i < arr.length; i++) {
            int[] c = getMatrixCoordsByIndex(i);
            mtr[c[0]][c[1]] = arr[i];
        }
        return mtr;
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
        int hammingDistance = 0;
        for (int i = 0; i < blocks.length - 1; i++) {
            if (goalBlocks[i] != blocks[i]) hammingDistance++;
        }
        return hammingDistance;
    }

    private int getMhDistance(int[] c1, int[] c2) {
        return Math.abs(c1[0] - c2[0]) + Math.abs(c2[1] - c1[1]);
    }

    private int getManhattanDistanceForBlock(int index) {
        int targetIndex = -1;
        
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[index] == 0) {
                targetIndex = 0;
                break;
            }
            if (blocks[index] == goalBlocks[i]) {
                targetIndex = i;
                break;
            }
        }
        int[] p = getMatrixCoordsByIndex(targetIndex);
        int[] p2 = getMatrixCoordsByIndex(index);
        int mh = getMhDistance(
            p,
            p2
        );
        return mh;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattanSum != -1) return manhattanSum;
        int sum = 0;
        for (int i = 0; i < blocks.length - 1; i++) {
            sum += getManhattanDistanceForBlock(i);
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int rIndex = StdRandom.uniform(0, n*n);
        int rIndex2 = StdRandom.uniform(0, n*n);
        while (rIndex == rIndex2) {
            rIndex2 = StdRandom.uniform(0, n*n);
        }
        int[] copy = Arrays.copyOf(blocks, blocks.length);
        exch(copy, rIndex, rIndex2);
        return new Board(
            getMatrix(copy)
        );
    }

    @Override
    public boolean equals(Object y) {
        Board b = (Board) y;
        for (int i = 0; i < blocks.length; i++) {
            if (b.blocks[i] != blocks[i]) return false;
        }
        return true;
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

    private void findNeighbors() {
        ArrayList<Board> foundNeighbors = new ArrayList<>();
        int i = 0;

        while (blocks[i] != 0) {
            i++;
        }

        // left
        if (i % n != 0) {
            int[] copy = Arrays.copyOf(blocks, blocks.length);
            exch(copy, i, i-1);
            foundNeighbors.add(
                new Board(
                    getMatrix(copy)
                )
            );
        }
        // right
        if (i % n != 2) {
            int[] copy = Arrays.copyOf(blocks, blocks.length);
            exch(copy, i, i+1);
            foundNeighbors.add(
                new Board(
                    getMatrix(copy)
                )
            );
        }
        // top
        if ((i + 1) > n) {
            int[] copy = Arrays.copyOf(blocks, blocks.length);
            exch(copy, i, i-n);
            foundNeighbors.add(
                new Board(
                    getMatrix(copy)
                )
            );
        }
        // bottom
        if (blocks.length - i > n) {
            int[] copy = Arrays.copyOf(blocks, blocks.length);
            exch(copy, i, i+n);
            foundNeighbors.add(
                new Board(
                    getMatrix(copy)
                )
            );
        }
        neighbors = foundNeighbors.toArray(new Board[foundNeighbors.size()]);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        String output = String.format("%d\n", n);
        for (int i = 0; i < blocks.length; i += n) {
            for (int j = 0; j < n; j++) {
                if (j > 0) {
                    output += " ";
                }
                output += String.format("%d", blocks[i + j]);
            }
            output += "\n";
        }
        return output;
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