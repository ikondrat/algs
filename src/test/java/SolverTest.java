import org.junit.Test;
import static org.junit.Assert.*;

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
            1,
            s.moves()
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

    // @Test
    // public void testSorting() {
    //     int[][] blocks = {
    //         {1, 0, 2},
    //         {4, 6, 3},
    //         {7, 5, 8}
    //     };
    //     int[][] blocks1 = {
    //         {1, 2, 0},
    //         {4, 6, 3},
    //         {7, 5, 8}
    //     };
    //     int[][] blocks2 = {
    //         {1, 0, 2},
    //         {4, 6, 3},
    //         {7, 5, 8}
    //     };
    //     int[][] blocks4 = {
    //         {0, 1, 2},
    //         {4, 6, 3},
    //         {7, 5, 8}
    //     };
    //     ArrayList<Solver.Node> arr = new ArrayList<>();

    //     Solver.growArr(arr, new Solver.Node(
    //         new Board(blocks),
    //         0,
    //         null
    //     ));
    //     Solver.growArr(arr, new Solver.Node(
    //         new Board(blocks1),
    //         0,
    //         null
    //     ));
    //     Solver.growArr(arr, new Solver.Node(
    //         new Board(blocks2),
    //         0,
    //         null
    //     ));
    //     Solver.growArr(arr, new Solver.Node(
    //         new Board(blocks4),
    //         0,
    //         null
    //     ));

    //     assertEquals(
    //         4,
    //         arr.size()
    //     );
    // }

    // @Test
    // public void testBiNodeSearch() {
    //     int[][] blocks = {
    //         {1, 0, 2},
    //         {4, 6, 3},
    //         {7, 5, 8}
    //     };
    //     int[][] blocks1 = {
    //         {1, 2, 0},
    //         {4, 6, 3},
    //         {7, 5, 8}
    //     };
    //     int[][] blocks2 = {
    //         {1, 0, 2},
    //         {4, 6, 3},
    //         {7, 5, 8}
    //     };
    //     int[][] blocks4 = {
    //         {0, 1, 2},
    //         {4, 6, 3},
    //         {7, 5, 8}
    //     };
    //     ArrayList<Solver.Node> arr = new ArrayList<>();

    //     Solver.growArr(arr, new Solver.Node(
    //         new Board(blocks),
    //         0,
    //         null
    //     ));
    //     Solver.growArr(arr, new Solver.Node(
    //         new Board(blocks1),
    //         0,
    //         null
    //     ));
    //     Solver.growArr(arr, new Solver.Node(
    //         new Board(blocks2),
    //         0,
    //         null
    //     ));
    //     Solver.growArr(arr, new Solver.Node(
    //         new Board(blocks4),
    //         0,
    //         null
    //     ));

    //     assertEquals(
    //         Solver.indexOf(arr, 5),
    //         1
    //     );
    //     assertEquals(
    //         Solver.indexOf(arr, 4),
    //         0
    //     );
    //     assertEquals(
    //         Solver.indexOf(arr, 6),
    //         3
    //     );
        
    // }

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

    // @Test
    // public void puzzle_38(){
    //     int[][] blocks = {
    //         {13, 1, 5, 4},
    //         {2, 3, 6, 8},
    //         {7, 10, 0, 9},
    //         {11, 14, 15, 12}
    //     };
        
    //     Solver s = new Solver(new Board(blocks));
    //     assertEquals(
    //         38,
    //         s.moves()
    //     );
    // }

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
            30,
            s.moves()
        );
    }
    
    @Test
    public void testPuzzle20() {
        int[][] blocks = {
            {1, 6, 4},
            {7, 0, 8},
            {2, 3, 5}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            20,
            s.moves()
        );
    }

    @Test
    public void testPuzzle30() {
        int[][] blocks = {
            {8, 4, 7},
            {1, 5, 6},
            {3, 2, 0}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            30,
            s.moves()
        );
    }

    @Test
    public void test25Moves() {
        int[][] blocks = {
            {2, 5, 1, 3},
            {9, 6, 12, 4},
            {10, 14, 8, 0},
            {13, 11, 15, 7}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            25,
            s.moves()
        );
    }

    // @Test
    // public void test80Moves() {
    //     int[][] blocks = {
    //         {0, 12, 9, 13},
    //         {15, 11, 10, 14},
    //         {3, 7, 5, 6},
    //         {4, 8, 2, 1}
    //     };
        
    //     Solver s = new Solver(new Board(blocks));
    //     assertEquals(
    //         80,
    //         s.moves()
    //     );
    // }

    @Test(expected = IllegalArgumentException.class)
    public void testNullException() {
        new Solver(null);
    }

    @Test
    public void testMoves4() {
        int[][] blocks = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            4,
            s.moves()
        );
    }

    @Test
    public void testMoves11() {
        int[][] blocks = {
            {1, 6, 2},
            {4, 8, 0},
            {7, 3, 5}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            11,
            s.moves()
        );
    }

    @Test
    public void testPuzzle28() {
        int[][] blocks = {
            {7, 8, 5},
            {4, 0, 2},
            {3, 6, 1}
        };

        Solver s = new Solver(new Board(blocks));
        assertEquals(
            28,
            s.moves()
        );
    }

    @Test
    public void testSolutions5() {
        int[][] blocks = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        
        Solver s = new Solver(new Board(blocks));
        int n = 0;
        for (Board b: s.solution()) {
            if (b != null) {
                n++;
            }
        }
        assertEquals(
            5,
            n
        );
    }

    @Test
    public void testSolvable() {
        int[][] blocks = {
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertTrue(
            s.isSolvable()
        );
    }

    @Test
    public void testUnsolvable() {
        int[][] blocks = {
            {1, 2, 3},
            {4, 6, 5},
            {7, 8, 0}
        };
        
        Solver s = new Solver(new Board(blocks));
        assertFalse(
            s.isSolvable()
        );
    }
    


    @Test
    public void testUnsolvedBoardSolutions() {
        int[][] blocks = {
            {3, 2, 4, 8},
            {1, 6, 0, 12},
            {5, 10, 7, 11},
            {9, 13, 14, 15}
        };
        Solver s = new Solver(new Board(blocks));
        assertEquals(
            s.moves(),
            -1
        );
    }

    @Test
    public void testUnsolvedNullSolutions() {
        int[][] blocks = {
            {3, 2, 4, 8},
            {1, 6, 0, 12},
            {5, 10, 7, 11},
            {9, 13, 14, 15}
        };
        Solver s = new Solver(new Board(blocks));
        assertFalse(
            s.isSolvable()
        );
        assertNull(
            s.solution()
        );
    }
}