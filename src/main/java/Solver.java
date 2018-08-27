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
    private final ArrayList<Board> boards;
    private final ArrayList<Board> boardPrevs;
    private Board[] solutionBoards;

    public Solver(Board initial) {
        if (isNull(initial)) {
            throw new java.lang.IllegalArgumentException(
                "The board is expected to be passed into the constructor"
            );
        }
        boards = new ArrayList<>();
        boardPrevs = new ArrayList<>();

        MinPQ<Board> bs = new MinPQ<>(new PriorityOrder(boards, boardPrevs));
        bs.insert(initial);
        boards.add(initial);
        boardPrevs.add(null);
        boolean solved = false;
        while (!bs.isEmpty()) {
            Board current = bs.delMin();
            
            if (current.isGoal()) {
                solved = true;
                goalBoard = current;
                break;
            }
            for (Board next: current.neighbors()) {
                if (boards.contains(next)) continue;
                boards.add(next);
                boardPrevs.add(current);
                bs.insert(next);
            }
        }
        isSolved = solved;
    }

    private boolean isNull(Object x) {
        return x == null;
    }

    private static class PriorityOrder implements Comparator<Board> {
        ArrayList<Board> boards;
        ArrayList<Board> prevBoards;

        PriorityOrder(ArrayList<Board> b, ArrayList<Board> p) {
            this.boards = b;
            this.prevBoards = p;
        }

        private int getDistanceToGoal(Board b) {
            int count = 0;
            while (b != null && !b.isGoal()) {
                int i = boards.indexOf(b);
                b = i != -1 ? prevBoards.get(i) : null;
                count++;
            }
            return count;
        }

        private int getPriority(Board b) {
            return b.manhattan() + getDistanceToGoal(b);
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

    private int getDistanceToGoal(Board b) {
        return getWayBoards(b).length;
    }

    private Board[] getWayBoards(Board b) {
        ArrayList<Board> wb = new ArrayList<>();
        while (b != null) {
            wb.add(b);
            int i = boards.indexOf(b);
            b = i != -1 ? boardPrevs.get(i) : null;
        }
        Board[] wbArr = new Board[wb.size()];
        wb.toArray(wbArr);
        
        return wbArr;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return getDistanceToGoal(goalBoard) - 1;
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
        Board[] bbb = getWayBoards(goalBoard);
        int n = bbb.length;
        solutionBoards = new Board[n];
        while (n > 0) {
            solutionBoards[--n] = bbb[solutionBoards.length - n - 1];
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