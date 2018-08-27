import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {
    private final boolean isSolved;
    private Node goalBoard;

    private Board[] solutionBoards;

    public Solver(Board b) {
        if (isNull(b)) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        MinPQ<Node> bs = new MinPQ<>();
        MinPQ<Node> bsTwin = new MinPQ<>();
        Board twinB = b.twin();
        Node initialNode = new Node(
            b,
            b.manhattan(),
            0,
            null
        );
        Node initialNodeTwin = new Node(
            twinB,
            twinB.manhattan(),
            0,
            null
        );

        bs.insert(initialNode);
        bsTwin.insert(initialNodeTwin);

        boolean hasSolution = false;
        boolean hasNoSolution = false;
        ArrayList<String> costsSoFarKeys = new ArrayList<>();
        ArrayList<Integer> costsSoFar = new ArrayList<>();
        ArrayList<String> costsSoFarKeysT = new ArrayList<>();
        ArrayList<Integer> costsSoFarT = new ArrayList<>();
        setPrioritySoFar(b, costsSoFarKeys, costsSoFar, 0);
        setPrioritySoFar(twinB, costsSoFarKeysT, costsSoFarT, 0);

        while (!bs.isEmpty() || !hasNoSolution) {
            Node current = bs.delMin();
            Node currentTwin = bsTwin.delMin();
            Node currentBoardNode = getGoal(
                current,
                bs,
                costsSoFarKeys,
                costsSoFar
            );
            Node currentBoardTwinNode = getGoal(
                currentTwin,
                bsTwin,
                costsSoFarKeysT,
                costsSoFarT
            );
            if (currentBoardNode.board.isGoal()) {
                goalBoard = currentBoardNode;
                hasSolution = true;
                break;
            }

            if (currentBoardTwinNode.board.isGoal()) {
                hasNoSolution = true;
            }
        }
        isSolved = hasSolution;
    }

    private int getPrioritySoFar(Board b, ArrayList<String> costsSoFarKeys, ArrayList<Integer> costsSoFar) {
        String key = b.toString();
        if (costsSoFarKeys.contains(key)) {
            int i = costsSoFarKeys.indexOf(key);
            return costsSoFar.get(i);
        }
        return -1;
    }

    private void setPrioritySoFar(Board b, ArrayList<String> costsSoFarKeys, ArrayList<Integer> costsSoFar, int v) {
        String key = b.toString();
        if (costsSoFarKeys.contains(key)) {
            int i = costsSoFarKeys.indexOf(key);
            costsSoFar.set(i, v);
        } else {
            costsSoFarKeys.add(key);
            costsSoFar.add(v);
        }
    }

    private Node getGoal(Node current, MinPQ<Node> nodes, ArrayList<String> costsSoFarKeys, ArrayList<Integer> costsSoFar) {
        if (current.board.isGoal()) {
            return current;
        }
        for (Board next: current.board.neighbors()) {
            int moves = current.moves + 1;
            int priority = next.manhattan();
            
            int prio = getPrioritySoFar(next, costsSoFarKeys, costsSoFar);
            if (prio == -1 || prio < priority) {
                Node n = new Node(
                    next,
                    priority + moves,
                    moves,
                    current
                );
                nodes.insert(n);
                setPrioritySoFar(next, costsSoFarKeys, costsSoFar, priority);
            }
        }
        return current;
    }

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int priority;
        public final int moves;
        private final Node prev;

        public Node(Board board, int priority, int moves, Node prev) {
            this.board = board;
            this.priority = priority;
            this.moves = moves;
            this.prev = prev;
        }

        public Stack<Board> getSolutionForBoard() {
            Stack<Board> solutionBoards = new Stack<>();
            Node current = this;
            while (current != null) {
                solutionBoards.push(current.board);
                current = current.prev;
            }
            return solutionBoards;
        }

        public boolean equals(Node that) {
            return this.board.equals(that.board);
        }
        public boolean equals(Board that) {
            return this.board.equals(that);
        }

        public int compareTo(Node that) {
            int diff = this.priority - that.priority;
            if (diff < 0) return -1;
            if (diff > 0) return 1;
            return 0;
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
            if (solutionBoards == null) {
                findSolutionBoards();
            }
            return new SolutionsIterator();
        };
    }

    private void findSolutionBoards() {
        Stack<Board> solutionItems = goalBoard.getSolutionForBoard();
        int i = 0;
        solutionBoards = new Board[solutionItems.size()];
        for (Board board: solutionItems) {
            solutionBoards[i++] = board;
        }
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