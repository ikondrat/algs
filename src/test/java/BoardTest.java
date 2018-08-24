import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.*;

public class BoardTest {
    @Test 
    public void TestHammingDistance() {
        int[][] blocks = {
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5},
        };
        Board b = new Board(blocks);
        assertEquals(
            5,
            b.hamming()
        );
    }

    @Test
    public void TestManhattanDistance() {
        int[][] blocks = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5},
        };
        Board b = new Board(blocks);

        assertEquals(
            10,
            b.manhattan()
        );
    }

    @Test 
    public void testManhattanDistance4() {
        int[][] blocks = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6},
        };
        Board b = new Board(blocks);

        assertEquals(
            4,
            b.manhattan()
        );
    }

    @Test 
    public void testManhattanDistance3() {
        int[][] blocks = {
            {1, 0, 3},
            {4, 2, 5},
            {7, 8, 6},
        };
        Board b = new Board(blocks);

        assertEquals(
            3,
            b.manhattan()
        );
    }

    @Test 
    public void testManhattanDistance5() {
        int[][] blocks = {
            {4, 1, 3},
            {0, 2, 5},
            {7, 8, 6},
        };
        Board b = new Board(blocks);

        assertEquals(
            5,
            b.manhattan()
        );
    }

    @Test
    public void TestDimensions() {
        int[][] blocks = {
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5},
        };
        Board b = new Board(blocks);

        assertEquals(
            b.dimension(),
            3
        );
    }

    @Test
    public void TestNeughborBoards() {
        int[][] blocks = {
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5},
        };
        Board b = new Board(blocks);
        Iterable<Board> boards = b.neighbors();
        int index = 0;
        
        for (Board board: boards) {
            index += 1;
        }
        assertEquals(
            index,
            4
        );
    }
}