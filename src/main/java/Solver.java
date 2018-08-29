import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private final boolean isSolved;
    private final Node goalBoard;
    private final ArrayList<Board> solutionBoards;

    public Solver(Board initial) {
        if (isNull(initial)) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        MinPQ<Node> bs = new MinPQ<>();
        Node rootNode = new Node(initial, 0, null);
        Node rootNodeTwin = new Node(initial.twin(), 0, null);
        bs.insert(rootNode);
        bs.insert(rootNodeTwin);
        rootNodeTwin.isTwin = true;

        ArrayList<Board> visited = new ArrayList<>();
        Node current = null;
        while (!bs.isEmpty()) {
            current = bs.delMin();
            if (visited.contains(current.board)) continue;
            if (current.board.isGoal()) break;
            

            for (Board next: current.board.neighbors()) {
                if (!visited.contains(next)) {
                    bs.insert(new Node(
                        next,
                        current.moves + 1,
                        current
                    ));
                    //visited.add(current.board);
                }
            }
            visited.add(current.board);
        }

        goalBoard = current.board.isGoal() ? current : null;
        isSolved = !current.isTwin;

        solutionBoards = !isSolved ? null : new ArrayList<>();
        if (!isSolved) return;
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
            isTwin = p != null ? p.isTwin : false;
        }

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
        return goalBoard.moves;
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