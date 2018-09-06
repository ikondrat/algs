import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
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
        MinPQ<Node> x = new MinPQ<>();
        ArrayList<Board> nodes = new ArrayList<>();
        ArrayList<Integer> moves = new ArrayList<>();
        Node current = null;
        Node pred = null;
        Node initialNode = new Node(initial, 0, null);
        x.insert(initialNode);
        nodes.add(initialNode.board);
        moves.add(0);
        while (current == null || !current.isDone) {
            current = x.delMin();
            for (Board next: current.board.neighbors()) {
                if (pred != null && next.equals(pred.board)) continue;
                if (Math.abs(current.board.manhattan() - next.manhattan()) != 1) continue;
                if (current.board.manhattan() == next.manhattan()) continue;
                int indx = nodes.indexOf(next);
                int m = current.moves + 1;
                if (indx == -1 || moves.get(indx) > m) {
                    Node n = new Node(
                        next,
                        m,
                        current
                    );
                    x.insert(n);
                    if (indx != -1) {
                        nodes.remove(indx);
                        moves.remove(indx);
                    }
                    nodes.add(n.board);
                    moves.add(m);
                }
            }
            pred = current;
        }
        goalNode = current;
        isSolved = true;
    }

    // private static int indexOf(ArrayList<Node> arr, Board search, int moves) {
    //     int mid;
    //     int from = 0;
    //     int to = arr.size();
    //     do {
    //         mid = (from + to) >>> 1;
    //         if (arr.get(mid).moves < moves) from = mid + 1;
    //         if (arr.get(mid).moves > moves) to = mid - 1;
    //     } while (from < to && arr.get(mid).moves != moves);

    //     if (arr.get(mid).equals(search)) return mid;
    //     while (mid > from + 1 && arr.get(mid - 1).moves == moves) {
    //         if (arr.get(mid).equals(search)) return mid;
    //         mid--;
    //     }

    //     while (mid < to - 1 && arr.get(mid + 1).moves == moves) {
    //         if (arr.get(mid).equals(search)) return mid;
    //         mid++;
    //     }
    //     return -1;
    // }

    private static class Node implements Comparable<Node> {
        public final boolean isDone;
        private final int moves;
        private final Node prev;
        private final Board board;
        private final int priority;

        public Node(Board b, int m,  Node p) {
            moves = m;
            prev = p;
            board = b;
            priority = b.manhattan() + m;
            isDone = b.isGoal();
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