import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SolverTest {
    @Test
    public void testOneMove() {
        int[][] blocks = {
            {1, 0},
            {3, 2}
        };
        Board b = new Board(blocks);
        Solver s = new Solver(b);

        assertEquals(
            s.moves(),
            1
        );
    }

    @Test
    public void testZeroMove() {
        int[][] blocks = {
            {1, 2},
            {3, 0}
        };
        Board b = new Board(blocks);
        Solver s = new Solver(b);

        assertEquals(
            s.moves(),
            0
        );
    }

    @Test
    public void test5Moves() {
        int[][] blocks = {
            {1, 0, 2},
            {4, 6, 3},
            {7, 5, 8}
        };

        Solver s = new Solver(new Board(blocks));
        assertEquals(
            5,
            s.moves()
        );
    }

    @Test
    public void test8Moves() {
        int[][] blocks = {
            {0, 4, 3},
            {2, 1, 6},
            {7, 5, 8}
        };

        Solver s = new Solver(new Board(blocks));
        assertEquals(
            8,
            s.moves()
        );
    }

    @Test
    public void test9Moves() {
        int[][] blocks = {
            {1, 3, 6},
            {5, 2, 8},
            {4, 0, 7}
        };

        Solver s = new Solver(new Board(blocks));
        assertEquals(
            9,
            s.moves()
        );
    }

    @Test
    public void test10Moves() {
        int[][] blocks = {
            {0, 4, 1},
            {5, 3, 2},
            {7, 8, 6}
        };

        Solver s = new Solver(new Board(blocks));
        assertEquals(
            10,
            s.moves()
        );
    }

    @Test
    public void test11Moves() {
        int[][] blocks = {
            {1, 3, 5},
            {7, 2, 6},
            {8, 0, 4}
        };

        Solver s = new Solver(new Board(blocks));
        assertEquals(
            11,
            s.moves()
        );
    }

    @Test
    public void test15Moves() {
        int[][] blocks = {
            {2, 0, 8},
            {1, 3, 5},
            {4, 6, 7}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            15,
            s.moves()
        );
    }

    @Test
    public void test20Moves() {
        int[][] blocks = {
            {7, 4, 3},
            {2, 8, 6},
            {0, 5, 1}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            20,
            s.moves()
        );
    }

    @Test
    public void test30Moves() {
        int[][] blocks = {
            {8, 6, 7},
            {2, 0, 4},
            {3, 5, 1}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            20,
            s.moves()
        );
    }
}