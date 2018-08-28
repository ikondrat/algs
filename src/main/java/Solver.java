import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {
    private boolean isSolved;
    private SolverNode goalBoard;
    private Board[] solutionBoards;

    public Solver(Board initial) {
        if (isNull(initial)) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        isSolved = false;
        MinPQ<SolverNode> bs = new MinPQ<>();
        SolverNode rootNode = new SolverNode(initial, 0, null, null);
        SolverNode rootNodeTwin = new SolverNode(initial.twin(), 0, null, null);
        bs.insert(rootNode);
        bs.insert(rootNodeTwin);
        rootNodeTwin.isTwin = true;

        SolverNode current = null;
        while (!bs.isEmpty()) {
            current = bs.delMin();

            if (current.isGoal) break;

            for (Board b: current.neighbors) {
                SolverNode root = current.root != null ? current.root : current; 
                int x = root.getMoves(b);
                if (x == -1 || current.moves + 1 < x) {
                    root.addHistory(b, current.moves + 1, x);
                    SolverNode sn = new SolverNode(
                        b,
                        current.moves + 1,
                        root,
                        current
                    );
                    bs.insert(sn);
                }
                
            }
        }

        SolverNode root = current.root != null ? current.root : current;
        if (current != null && current.isGoal) {
            goalBoard = current;
            isSolved = !root.isTwin;
        }

        if (isSolved) {
            solutionBoards = new Board[goalBoard.moves + 1];
            int n = solutionBoards.length;
            while (current != null) {
                solutionBoards[--n] = current.board;
                current = current.prev;
            }
        }
    }

    private Board[] toArray(Iterable<Board> itr) {
        ArrayList<Board> ret = new ArrayList<>();
        for (Board t : itr) {
            ret.add(t);
        }
        Board[] b = new Board[ret.size()];
        ret.toArray(b);
        return b;
    }

    private class SolverNode implements Comparable<SolverNode> {
        public final short moves;
        public boolean isTwin = false;
        private final Board board;
        private final boolean isGoal;
        private final short priority;
        private final Board[] neighbors;
        private final SolverNode root;
        private final SolverNode prev;
        private final ArrayList<String> historyBoards;
        private final ArrayList<Short> historyBoardsPrio;

        public SolverNode(Board b, int m, SolverNode r, SolverNode p) {
            board = b;
            root = r;
            isGoal = b.isGoal();
            neighbors = toArray(b.neighbors());
            moves = (short) m;
            prev = p;
            priority = (short) (b.manhattan() + m);
            historyBoards = new ArrayList<>();
            historyBoardsPrio = new ArrayList<>();
            if (r == null) {
                historyBoards.add(b.toString());
                historyBoardsPrio.add((short) 0);
            }
        }

        public int compareTo(SolverNode that) {
            int diff = priority - that.priority;
            if (diff < 0) return -1;
            if (diff > 0) return 1;
            return 0;
        }

        public int getMoves(Board b) {
            int i = historyBoards.indexOf(b.toString());
            return i != -1 ? historyBoardsPrio.get(i) : -1;
        }

        public void addHistory(Board b, int m, int index) {
            historyBoards.add(b.toString());
            historyBoardsPrio.add((short) m);
        }
    }

    private boolean isNull(Object x) {
        return x == null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return goalBoard.moves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return () -> {
            return new SolutionsIterator();
        };
    }

    private class SolutionsIterator implements Iterator<Board> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < solutionBoards.length;
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return solutionBoards[index++];
            } else {
                throw new NoSuchElementException("There is no next solution.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of solutions is not supported.");
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}