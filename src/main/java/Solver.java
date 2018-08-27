import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class Solver {
    private final boolean isSolved;
    private Board goalBoard;
    private final ArrayList<String> boardKeys;
    private final ArrayList<Board> boards;
    private final ArrayList<Integer> boardMoves;
    private final ArrayList<Integer> boardPrevs;
    private Board[] solutionBoards;

    public Solver(Board initial) {
        if (isNull(initial)) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        ArrayList<String> visitedBoards = new ArrayList<>();
        boards = new ArrayList<>();
        boardKeys = new ArrayList<>();
        boardMoves = new ArrayList<>();
        boardPrevs = new ArrayList<>();

        MinPQ<Board> bs = new MinPQ<>(new PriorityOrder(boardKeys, boardMoves));
        bs.insert(initial);
        String initialKey = initial.toString();

        boards.add(initial);
        boardKeys.add(initialKey);
        boardMoves.add(0);
        boardPrevs.add(-1);

        boolean solved = false;
        while (!bs.isEmpty()) {
            Board current = bs.delMin();
            
            String currentKey = current.toString();
            //if (visitedBoards.contains(currentKey)) continue;
            int indexCurrent = boardKeys.indexOf(currentKey);
            if (current.isGoal()) {
                solved = true;
                goalBoard = current;
                break;
            }

            for (Board next: current.neighbors()) {
                String nextKey = next.toString();
                if (visitedBoards.contains(nextKey)) continue;
                boardKeys.add(nextKey);
                int moves = boardMoves.get(indexCurrent) + 1;
                boardMoves.add(moves);
                boards.add(next);
                boardPrevs.add(indexCurrent);
                bs.insert(next);
            }
            visitedBoards.add(currentKey);
        }
        isSolved = solved;
    }

    private boolean isNull(Object x) {
        return x == null;
    }

    private static class PriorityOrder implements Comparator<Board> {
        ArrayList<String> boards;
        ArrayList<Integer> moves;

        PriorityOrder(ArrayList<String> b, ArrayList<Integer> m) {
            this.boards = b;
            this.moves = m;
        }
        private int getPriority(Board b) {
            String boardKey = b.toString();
            int i = boards.indexOf(boardKey);
            return b.manhattan() + moves.get(i);
        }

        @Override
        public int compare(Board x, Board y) {
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
        String k = goalBoard.toString();
        int i = boardKeys.indexOf(k);
        return boardMoves.get(i);
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
        Board current = goalBoard;
        int i = boardKeys.indexOf(current.toString());
        int n = boardMoves.get(i) + 1;
        solutionBoards = new Board[n];
        while (n > 0) {
            solutionBoards[--n] = current;
            String k = current.toString();
            i = boardKeys.indexOf(k);
            int index = boardPrevs.get(i);
            current = index != -1 ? boards.get(index) : null;
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