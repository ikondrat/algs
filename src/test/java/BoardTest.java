import org.junit.Test;
import java.util.Iterator;
import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void biSearch_test3() {
        int[][] blocks = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5},
        };
        Board b = new Board(blocks);

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        assertEquals(
            2,
            b.biSearch(
                arr,
                0,
                arr.length - 1,
                3
            )
        );
    }

    @Test
    public void biSearch_test4() {
        int[][] blocks = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5},
        };
        Board b = new Board(blocks);

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        assertEquals(
            3,
            b.biSearch(
                arr,
                0,
                arr.length - 1,
                4
            )
        );
    }
    @Test
    public void biSearch_test8() {
        int[][] blocks = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5},
        };
        Board b = new Board(blocks);

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        assertEquals(
            7,
            b.biSearch(
                arr,
                0,
                arr.length - 1,
                8
            )
        );
    }

    @Test
    public void biSearch_test0() {
        int[][] blocks = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5},
        };
        Board b = new Board(blocks);

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        assertEquals(
            8,
            b.biSearch(
                arr,
                0,
                arr.length - 1,
                0
            )
        );
    }

    @Test
    public void biSearch_test1() {
        int[][] blocks = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5},
        };
        Board b = new Board(blocks);

        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        assertEquals(
            0,
            b.biSearch(
                arr,
                0,
                arr.length - 1,
                1
            )
        );
    }

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

    @Test
    public void TestTwin() {
        int[][] blocks = {
            {1, 0},
            {2, 3}
        };
        Board b = new Board(blocks);
        Board t = b.twin();
        assertFalse(b.equals(t));
    }
}