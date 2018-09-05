import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {
    // @Test
    // public void biSearch_test3() {
    //     int[][] blocks = {
    //         {8, 1, 3},
    //         {4, 0, 2},
    //         {7, 6, 5},
    //     };
    //     Board b = new Board(blocks);

    //     int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    //     assertEquals(
    //         2,
    //         b.biSearch(
    //             arr,
    //             0,
    //             arr.length - 1,
    //             3
    //         )
    //     );
    // }

    // @Test
    // public void biSearch_test4() {
    //     int[][] blocks = {
    //         {8, 1, 3},
    //         {4, 0, 2},
    //         {7, 6, 5},
    //     };
    //     Board b = new Board(blocks);

    //     int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    //     assertEquals(
    //         3,
    //         b.biSearch(
    //             arr,
    //             0,
    //             arr.length - 1,
    //             4
    //         )
    //     );
    // }
    // @Test
    // public void biSearch_test8() {
    //     int[][] blocks = {
    //         {8, 1, 3},
    //         {4, 0, 2},
    //         {7, 6, 5},
    //     };
    //     Board b = new Board(blocks);

    //     int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    //     assertEquals(
    //         7,
    //         b.biSearch(
    //             arr,
    //             0,
    //             arr.length - 1,
    //             8
    //         )
    //     );
    // }

    // @Test
    // public void biSearch_test0() {
    //     int[][] blocks = {
    //         {8, 1, 3},
    //         {4, 0, 2},
    //         {7, 6, 5},
    //     };
    //     Board b = new Board(blocks);

    //     int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    //     assertEquals(
    //         -1,
    //         b.biSearch(
    //             arr,
    //             0,
    //             arr.length - 1,
    //             0
    //         )
    //     );
    // }

    // @Test
    // public void biSearch_test12() {
    //     int[][] blocks = {
    //         {8, 1, 3},
    //         {4, 0, 2},
    //         {7, 6, 5},
    //     };
    //     Board b = new Board(blocks);

    //     int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
    //     assertEquals(
    //         11,
    //         b.biSearch(
    //             arr,
    //             0,
    //             arr.length - 1,
    //             12
    //         )
    //     );
    // }

    // @Test
    // public void biSearch_test1() {
    //     int[][] blocks = {
    //         {8, 1, 3},
    //         {4, 0, 2},
    //         {7, 6, 5},
    //     };
    //     Board b = new Board(blocks);

    //     int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    //     assertEquals(
    //         0,
    //         b.biSearch(
    //             arr,
    //             0,
    //             arr.length - 1,
    //             1
    //         )
    //     );
    // }

    @Test 
    public void testHammingDistance() {
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
    public void testManhattanDistance() {
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
    public void testDimensions() {
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
    public void testNeughborBoards() {
        int[][] blocks = {
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5},
        };
        Board b = new Board(blocks);
        Iterable<Board> boards = b.neighbors();
        int index = 0;
        
        for (Board board: boards) {
            if (board != null) {
                index += 1;
            }
        }
        assertEquals(
            index,
            4
        );
    }

    @Test
    public void testTwin() {
        int[][] blocks = {
            {1, 0},
            {2, 3}
        };
        Board b = new Board(blocks);
        Board t = b.twin();
        assertFalse(b.equals(t));
    }
    @Test
    public void equalsNullTest() {
        int[][] blocks = {
            {1, 0},
            {2, 3}
        };
        Board b = new Board(blocks);
        assertFalse(b.equals(null));
    }

    @Test
    public void equalsStringTest() {
        int[][] blocks = {
            {1, 0},
            {2, 3}
        };
        Board b = new Board(blocks);
        assertFalse(b.equals("null"));
    }

    @Test
    public void equalsReflexive() {
        int[][] blocks = {
            {1, 0},
            {2, 3}
        };
        Board b = new Board(blocks);
        assertTrue(b.equals(b));
    }

    @Test
    public void equalsBoards() {
        int[][] blocks = {
            {0, 2},
            {1, 3}
        };
        int[][] blocks2 = {
            {0, 2},
            {1, 3}
        };
        Board b = new Board(blocks);
        Board b2 = new Board(blocks2);
        assertTrue(b.equals(b2));
    }


    @Test
    public void equalsDifferentBoards() {
        int[][] blocks = {
            {1, 0},
            {2, 3}
        };

        int[][] blocks2 = {
            {1, 0, 5},
            {2, 3, 6},
            {2, 3, 2}
        };
        Board b = new Board(blocks);
        Board b2 = new Board(blocks2);
        assertFalse(b.equals(b2));
    }
    @Test
    public void toStringIsCorrect() {
        int[][] blocks = {
            {1, 0},
            {2, 3}
        };
        Board b = new Board(blocks);
        assertEquals(
            b.toString(),
            "2\n1 0\n2 3\n"
        );
    }

    @Test
    public void neighborsCorrectness() {
        int[][] blocks = {
            {1, 0, 2},
            {4, 6, 3},
            {7, 5, 8}
        };
        Board b = new Board(blocks);
        Iterable<Board> boardsIterable = b.neighbors();
        int index = 0;
        Board[] boards = new Board[3];
        for (Board board: boardsIterable) {
            boards[index++] = board;
        }

        assertEquals(
            "3\n0 1 2\n4 6 3\n7 5 8\n",
            boards[0].toString()
        );

        assertEquals(
            "3\n1 2 0\n4 6 3\n7 5 8\n",
            boards[1].toString()
        );

        assertEquals(
            "3\n1 6 2\n4 0 3\n7 5 8\n",
            boards[2].toString()
        );
    }
}