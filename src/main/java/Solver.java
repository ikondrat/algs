import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Collections;
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
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Node> nodesT = new ArrayList<>();
        Node current = null;
        Node initialNode = new Node(initial, 0, null);
        Node initialNodeTwin = new Node(initial.twin(), 0, null);
        initialNodeTwin.isTwin = true;
        pq.insert(initialNode);
        pq.insert(initialNodeTwin);
        while (current == null || !current.isDone) {
            current = pq.delMin();
            for (Board next: current.board.neighbors()) {
                if (current.prev != null && next.equals(current.prev.board)) continue;
                if (Math.abs(current.board.manhattan() - next.manhattan()) != 1) continue;
                if (current.board.manhattan() == next.manhattan()) continue;
                int p = next.manhattan() + current.moves + 1;
                ArrayList<Node> visited = current.isTwin ? nodesT : nodes;
                int indx = indexOf(visited, p);
                boolean has = false;
                if (indx != -1) {
                    while (indx < visited.size() && visited.get(indx).priority == p) {
                        Node n = visited.get(indx);
                        if (n.manhattan == next.manhattan() && n.moves == current.moves + 1 && n.board.equals(next)) {
                            has = true;
                        }
                        indx++;
                    }
                }
                if (has) continue;
                Node n = new Node(
                    next,
                    current.moves + 1,
                    current
                );
                pq.insert(n);
            }
            if (current.isTwin) {
                growArr(nodesT, current);
            } else {
                growArr(nodes, current);
            }
        }
        goalNode = current;
        isSolved = !goalNode.isTwin;
    }

    private static void growArr(ArrayList<Node> arr, Node n) {
        arr.add(n);
        int indx = arr.size() - 1;
        while (indx > 0 && (arr.get(indx).priority < arr.get(indx - 1).priority)) {
            Collections.swap(arr, indx, indx - 1);
            indx--;
        }
    }

    private static int indexOf(ArrayList<Node> arr, int prio) {
        int mid;
        int from = 0;
        int to = arr.size();
        if (arr.size() == 0) return -1;
        do {
            mid = (from + to) >>> 1;
            if (arr.get(mid).priority < prio) from = mid + 1;
            if (arr.get(mid).priority > prio) to = mid - 1;
            if (arr.get(mid).priority == prio) to = mid - 1;
        } while (from < to && arr.get(mid).priority != prio);

        if (arr.get(mid).priority == prio && mid - 1 > from) {
            while (arr.get(mid - 1).priority == prio && mid -1 > from) mid--;
        }
        return mid != -1 && arr.get(mid).priority == prio ? mid : -1;
    }

    private static class Node implements Comparable<Node> {
        public final boolean isDone;
        public boolean isTwin;
        public final int manhattan;
        private final int moves;
        private final Node prev;
        private final Board board;
        private final int priority;

        public Node(Board b, int m,  Node p) {
            moves = m;
            prev = p;
            board = b;
            manhattan = b.manhattan();
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