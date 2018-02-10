import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/** .
 *  Percolation class
 */
public class Percolation {

    /**
     * we need 2 uf collections.
     */
    private WeightedQuickUnionUF uf, ufBottom;
    /** array to check open state. */
    private boolean[] cells;
    /** size of row. */
    private int size;
    /** top point. */
    private int top;
    /** bottom point. */
    private int bottom;

    /**
     * constructor of class.
     * @param n size of collection
     */
    public Percolation(final int n ) {
        if (n <= 0) throw new IllegalArgumentException("illegal argument for constructor");
        size = n;
        cells = new boolean[size * size + 2];
        uf = new WeightedQuickUnionUF(size * size + 2);
        ufBottom = new WeightedQuickUnionUF(size * size + 2);
        bottom = size * size;
        top = size * size + 1;
    }

    /**
     *
     * @param row number of row
     * @param col numer of col
     */
    public void open(final int row, final int col) {
        int i = getIndex(row, col),
            nIndex;

        if (cells[i]) {
            return;
        }

        if (row == 1) {
            uf.union(i, top);
            ufBottom.union(i, top);
        }

        if (row == size) {
            ufBottom.union(i, bottom);
        }

        // top
        if (row > 1 && isOpen(row - 1, col))  {
            nIndex = getIndex(row - 1, col);
            uf.union(i, nIndex);
            ufBottom.union(i, nIndex);
        }

        // bottom
        if (row < size && isOpen(row + 1, col)) {
            nIndex = getIndex(row + 1, col);
            ufBottom.union(i, nIndex);
            uf.union(i, nIndex);
        }

        // left
        if (col > 1 && isOpen(row, col - 1)) {
            nIndex = getIndex(row, col - 1);
            uf.union(i, nIndex);
            ufBottom.union(i, nIndex);
        }

        // right
        if (col < size && isOpen(row, col + 1))  {
            nIndex = getIndex(row, col + 1);
            uf.union(i, nIndex);
            ufBottom.union(i, nIndex);
        }

        cells[i] = true;
    }

    /**
     * @param row the row nunber
     * @param col the col number
     * @return index
     */
    private int getIndex(final int row, final int col) {
        checkBindings(row);
        checkBindings(col);
        int i = (row - 1) * size + col;
        if (i < 0 || i > size * size) {
            throw new IndexOutOfBoundsException(
                "row index i out of bounds: " + i
            );
        }
        return i - 1;
    }

    private void checkBindings(int i) {
        if (i <= 0 || i > size) throw new IndexOutOfBoundsException("index " + i + " out of bounds");
    }


    /**
     * @param row the row nunber
     * @param col the col number
     * @return open state
     */
    public boolean isOpen(final int row, final int col)  {
        return cells[getIndex(row, col)];
    }

    /*
     * @param row the row nunber
     * @param col the col number
     * @return filled state
     */
    public boolean isFull(final int row, final int col) {
        return uf.connected(getIndex(row, col), top);
    }

    /**
     * @return filled state
     */
    public boolean percolates() {
        return ufBottom.connected(bottom, top);
    }

    /**
     * @param args input to test the function
     */
    public static void main(final String[] args)   {
        Percolation pc = new Percolation(2);
        System.out.println("Percolates: " + pc.percolates());
        pc.open(1, 1);
        pc.open(2, 1);
        System.out.println("Percolates: " + pc.percolates());
    }
}
