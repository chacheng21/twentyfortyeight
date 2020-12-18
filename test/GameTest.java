

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;







/** 
 *  You can use this file (and others) to array your
 *  implementation.
 */

public class GameTest {

    @Test
    public void generateNewTileOnlyOneSpot() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 0;
        array[0][1] = 4;
        array[0][2] = 8;
        array[0][3] = 16;
        array[1][0] = 32;
        array[1][1] = 64;
        array[1][2] = 128;
        array[1][3] = 256;
        array[2][0] = 512;
        array[2][1] = 1024;
        array[2][2] = 8;
        array[2][3] = 4;
        array[3][0] = 2;
        array[3][1] = 512;
        array[3][2] = 4;
        array[3][3] = 2;
        game.setBoard(array);
        game.generateNewTile();
        try {
            assertEquals(4, game.getBoard()[0][0]);
        } catch (AssertionFailedError e) {
            assertEquals(2, game.getBoard()[0][0]);
        }
    }
    
    @Test
    public void shiftLeftCollision() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[3][0] = 4;
        array[0][0] = 4;
        game.setBoard(array);
        int score = game.shiftLeft();
        assertEquals(8, score);
        assertEquals(8, array[0][0]);
    }
    
    @Test
    public void shiftLeftNoCollision() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 2;
        array[1][1] = 2;
        game.setBoard(array);
        int score = game.shiftLeft();
        assertEquals(0, score);
        assertEquals(2, array[0][0]);
        assertEquals(2, array[0][1]);
    }
    
    @Test
    public void shiftLeftMultipleCollisions() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[3][0] = 4;
        array[1][0] = 4;
        array[3][1] = 2;
        array[1][1] = 2;
        array[2][3] = 8;
        array[3][3] = 8;
        game.setBoard(array);
        int score = game.shiftLeft();
        assertEquals(28, score);
        assertEquals(8, array[0][0]);
        assertEquals(4, array[0][1]);
        assertEquals(16, array[0][3]);
    }
    
    @Test
    public void shiftManyLeftNoCollisions() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[3][0] = 2;
        array[3][1] = 4;
        array[1][1] = 8;
        array[2][3] = 16;
        array[3][3] = 32;
        game.setBoard(array);
        int score = game.shiftLeft();
        assertEquals(0, score);
        assertEquals(2, array[0][0]);
        assertEquals(8, array[0][1]);
        assertEquals(4, array[1][1]);
        assertEquals(16, array[0][3]);
        assertEquals(32, array[1][3]);
    }
    
    @Test
    public void shiftLeftNoMoves() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 2;
        array[0][1] = 2;
        array[0][2] = 2;
        array[0][3] = 2;
        game.setBoard(array);
        int score = game.shiftLeft();
        assertEquals(-1, score);
        assertEquals(2, array[0][0]);
        assertEquals(2, array[0][1]);
        assertEquals(2, array[0][2]);
        assertEquals(2, array[0][3]);
    }
    
    @Test
    public void shiftLeftNoDoubleCollision() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 4;
        array[1][0] = 0;
        array[2][0] = 4;
        array[3][0] = 8;
        game.setBoard(array);
        int score = game.shiftLeft();
        assertEquals(8, score);
        assertEquals(8, array[0][0]);
        assertEquals(8, array[1][0]);
    }
    
    
    @Test
    public void shiftLeftTwoAdjacentIdenticalPairsCollisions() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 4;
        array[1][0] = 4;
        array[2][0] = 4;
        array[3][0] = 4;
        game.setBoard(array);
        int score = game.shiftLeft();
        assertEquals(16, score);
        assertEquals(8, array[0][0]);
        assertEquals(8, array[1][0]);
    }
    
    
    @Test
    public void gameOverReturnsTrue() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 2;
        array[0][1] = 4;
        array[0][2] = 8;
        array[0][3] = 16;
        array[1][0] = 32;
        array[1][1] = 64;
        array[1][2] = 128;
        array[1][3] = 256;
        array[2][0] = 512;
        array[2][1] = 1024;
        array[2][2] = 8;
        array[2][3] = 4;
        array[3][0] = 2;
        array[3][1] = 512;
        array[3][2] = 4;
        array[3][3] = 2;
        game.setBoard(array);
        assertTrue(game.gameOver());
    }
    
    @Test
    public void gameOverReturnsFalseContainsBlank() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 0;
        array[0][1] = 4;
        array[0][2] = 8;
        array[0][3] = 16;
        array[1][0] = 32;
        array[1][1] = 64;
        array[1][2] = 128;
        array[1][3] = 256;
        array[2][0] = 512;
        array[2][1] = 1024;
        array[2][2] = 8;
        array[2][3] = 4;
        array[3][0] = 2;
        array[3][1] = 512;
        array[3][2] = 4;
        array[3][3] = 2;
        game.setBoard(array);
        assertFalse(game.gameOver());
    }
    
    @Test
    public void gameOverReturnsFalseAdjacentMatch() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 2;
        array[0][1] = 2;
        array[0][2] = 8;
        array[0][3] = 16;
        array[1][0] = 32;
        array[1][1] = 64;
        array[1][2] = 128;
        array[1][3] = 256;
        array[2][0] = 512;
        array[2][1] = 1024;
        array[2][2] = 8;
        array[2][3] = 4;
        array[3][0] = 2;
        array[3][1] = 512;
        array[3][2] = 4;
        array[3][3] = 2;
        game.setBoard(array);
        assertFalse(game.gameOver());
    }
    
    @Test
    public void undoDoesNothing() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 2;
        array[0][1] = 2;
        game.setBoard(array);
        game.undo();
        assertArrayEquals(array, game.getBoard());
    }
    
    @Test
    public void contains2048ReturnsTrue() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        array[0][0] = 2048;
        game.setBoard(array);
        assertTrue(game.contains2048());
    }
    
    @Test
    public void contains2048ReturnsFalse() {
        TwentyFortyEight game = new TwentyFortyEight();
        int [][] array = new int[4][4];
        game.setBoard(array);
        assertFalse(game.contains2048());
    }
}
