import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import java.util.ArrayList;

public class Solver {
    private boolean isSolved;
    private Node goalNode;

    public Solver(Board initial) {
        if (isNull(initial)) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        MinPQ<Node> x = new MinPQ<>();
        Node current = null;
        Node pred = null;
        x.insert(new Node(initial, 0, null));
        while (current == null || !current.isDone) {
            current = x.delMin();
            for (Board next: current.board.neighbors()) {
                if (pred != null && pred.board.equals(next)) continue;
                x.insert(new Node(
                    next,
                    current.moves + 1,
                    current
                ));
            }
            pred = current;
        }

        goalNode = current;
        isSolved = true;
    }

    private static class Node implements Comparable<Node> {
        public final int manhattan;
        public final boolean isDone;
        private final int moves;
        private final Node prev;
        private final Board board;
        private final int priority;
        

        public Node(Board b, int m, Node p) {
            moves = m;
            prev = p;
            board = b;
            priority = b.manhattan() + m;
            manhattan = b.manhattan();
            isDone = b.isGoal();
        }

        public boolean isEqual(Board b) {
            return this.board.equals(b);
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

    private static int indexOf(ArrayList<Node> nodes, Node node) {
        for (int i = nodes.size() - 1; i >= 0; i--) {
            if (node.board.equals(nodes.get(i).board)) {
                return i;
            }
            if (nodes.get(i).manhattan > node.manhattan) return -1;
        }
        return -1;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return goalNode.moves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
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