import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class Solver {
    private boolean isSolved = false;
    private Node goalBoard;

    private class Node {
        public Board board;
        public int moves;
        public Node prev;

        Node(Board board, int moves) {
            this.board = board;
            this.moves = moves;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        ArrayList<String> visitedBoards = new ArrayList<>();
        MinPQ<Node> bs = new MinPQ<>(new PriorityOrder());
        bs.insert(new Node(initial, 0));
        while (!bs.isEmpty()) {
            Node current = bs.delMin();
            
            if (current.board.isGoal()) {
                isSolved = true;
                goalBoard = current;
                break;
            }
            if (visitedBoards.contains(current.board.toString())) continue;
            for (Board next: current.board.neighbors()) {
                if (visitedBoards.contains(next.toString())) continue;
                Node n = new Node(next, current.moves + 1);
                n.prev = current;
                bs.insert(n);
            }
            visitedBoards.add(current.board.toString());
        }
    }

    private static class PriorityOrder implements Comparator<Node> {
        private int getPriority(Node b) {
            return b.board.manhattan() + b.moves;
        }

        @Override
        public int compare(Node x, Node y) {
            int diff = getPriority(x) - getPriority(y);
            if (diff < 0) return -1;
            if (diff > 0) return 1;
            return 0;
        }
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
        Node current = goalBoard;

        @Override
        public boolean hasNext() {
            return current.prev != null;
        }

        @Override
        public Board next() {
            if (hasNext()) {
                current = current.prev;
                return current.board;
            } else {
                throw new NoSuchElementException("There is no next neighbor.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of neighbors not supported.");
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