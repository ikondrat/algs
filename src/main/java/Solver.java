import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final boolean isSolved;
    private final Node goalNode;

    public Solver(Board initial) {
        if (isNull(initial)) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        MinPQ<Node> pq = new MinPQ<>();
        Node current = null;
        Node initialNode = new Node(initial, 0, initial.manhattan(), null);
        Board t = initial.twin();
        Node initialNodeTwin = new Node(t, 0, t.manhattan(), null);
        initialNodeTwin.isTwin = true;
        pq.insert(initialNode);
        pq.insert(initialNodeTwin);
        while (current == null || !current.isDone) {
            current = pq.delMin();
            int m = current.moves + 1;
            for (Board next: current.board.neighbors()) {
                if (current.prev != null && next.equals(current.prev.board)) continue;
                int mh = next.manhattan();
                if (Math.abs(current.manhattan - mh) != 1) continue;
                if (current.manhattan == mh) continue;
                Node n = new Node(
                    next,
                    m,
                    mh,
                    current
                );
                pq.insert(n);
            }
        }
        goalNode = current;
        isSolved = !goalNode.isTwin;
    }

    private static class Node implements Comparable<Node> {
        public final boolean isDone;
        public boolean isTwin;
        public final int manhattan;
        private final int moves;
        private final Node prev;
        private final Board board;
        private final int priority;

        public Node(Board b, int m, int mh,  Node p) {
            moves = m;
            prev = p;
            board = b;
            manhattan = mh;
            priority = manhattan + m;
            isDone = b.isGoal();
            isTwin = p != null && p.isTwin;
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
        return isSolved ? goalNode.moves : -1;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolved) return null;
        Stack<Board> solutionBoards = new Stack<>();
        Node current = goalNode;
        while (current != null) {
            solutionBoards.push(current.board);
            current = current.prev;
        }
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