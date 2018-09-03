import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private final boolean isSolved;
    private int moves;
    private ArrayList<Board> solutionBoards;

    public Solver(Board initial) {
        if (isNull(initial)) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        MinPQ<Node> bs = new MinPQ<>();
        Node rootNode = new Node(initial, 0, null);
        Node rootNodeTwin = new Node(initial.twin(), 0, null);
        rootNodeTwin.isTwin = true;
        bs.insert(rootNode);
        bs.insert(rootNodeTwin);
        Node current = null;
        Board pred = null;
        Board predTwin = null;
        Board b;
        boolean isUnsolvable = false;
        while (!bs.isEmpty()) {
            current = bs.delMin();
            b = current.board;
            if (b.isGoal()) break;
            for (Board next: b.neighbors()) {
                if (!current.isTwin && pred != null && pred == next) continue;
                if (current.isTwin && predTwin != null && predTwin == next) continue;
                bs.insert(new Node(
                    next,
                    current.moves + 1,
                    current
                ));
            }
            if (current.isTwin) {
                predTwin = b;
            } else {
                pred = b;
            }
        }

        isUnsolvable = current.isTwin;
        isSolved = !isUnsolvable;
        if (!isSolved) {
            solutionBoards = null;
            moves = -1;
            return;
        }
        moves = current.moves;
        solutionBoards = new ArrayList<>();
        while (current != null) {
            solutionBoards.add(current.board);
            current = current.prev;
        }
        Collections.reverse(solutionBoards);
    }

    private static class Node implements Comparable<Node> {
        public boolean isTwin;
        private final int moves;
        private final Node prev;
        private final Board board;
        private final int priority;

        public Node(Board b, int m, Node p) {
            moves = m;
            prev = p;
            board = b;
            priority = b.manhattan() + m;
            isTwin = p == null ? false : p.isTwin;
        }

        @Override
        public int compareTo(Node that) {
            int diff = priority - that.priority;
            if (diff < 0) return -1;
            if (diff > 0) return 1;
            return 0;
        }
    }

    private static boolean isNull(Object x) {
        return x == null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionBoards;
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