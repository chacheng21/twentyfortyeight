import java.awt.Graphics;
import java.util.LinkedList;


public class TwentyFortyEight {
    
    // create the board
    private int[][] board = new int[4][4];
    
    private LinkedList<int[][]> storeBoards = new LinkedList<int[][]>();
    
    // create variables for size of tiles 
    public static final int TILE_SIZE = 100;
    
    // constructor randomly generated 2 tiles anywhere on the board
    // with value of tile either being 2 or 4
    public TwentyFortyEight() {
        this.storeBoards = new LinkedList<int[][]>();
        
        int row1, row2, col1, col2;
        int firstNum, secondNum;
        double firstProb, secondProb;
        
        // generate a number from 0 to 1 to determine whether a tile is a 2 or 4
        firstProb = Math.random();
        secondProb = Math.random();
        
        // use type casting to generate an integer from 0 to 3
        row1 = (int) (Math.random() * 4);
        row2 = (int) (Math.random() * 4);
        col1 = (int) (Math.random() * 4);
        col2 = (int) (Math.random() * 4);

        while (row1 == row2 && col1 == col2) {
            row2 = (int) (Math.random() * 4);
            col2 = (int) (Math.random() * 4);
        }
        
        // probability of getting 2 is around 0.85 in the real game according to simulation
        if (firstProb > 0.85) {
            firstNum = 4;
        } else {
            firstNum = 2;
        }
        
        if (secondProb > 0.85) {
            secondNum = 4;
        } else {
            secondNum = 2;
        }
        
        board[row1][col1] = firstNum;
        board[row2][col2] = secondNum;
        
        storeBoards.add(board);
    }
    
    
    /*** GETTERS **********************************************************************************/
    public int[][] getBoard() {
        int[][] copy = new int[4][4];
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                copy[i][j] = board[i][j];
            }
        }
        
        return copy;
    }
    
    
    /*** SETTERS **********************************************************************************/
    public void setBoard(int[][] twentyFortyEight) {
        this.board = twentyFortyEight;
    }
    
    
    // method to randomly generate a new tile on the board (similar to code in constructor)
    public void generateNewTile() {
        int counter = 0;
        int index = 0;
        int num;
        double prob;
        
        prob = Math.random();
        
        if (prob > 0.85) {
            num = 4;
        } else {
            num = 2;
        }
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    counter += 1;
                }
            }
        }
       
        int[][] store = new int[counter][2];
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    int[] arr = {i, j};
                    store[index] = arr;
                    index += 1;
                }
            }
        }
        
        int selectRandom = (int) (Math.random() * (counter));
        
        int[] position = store[selectRandom];
        
        board[position[0]][position[1]] = num;
    }
    
    
    // checks for a 2048 tile.  Returns true if the board contains a 2048
    public boolean contains2048() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    // checks if there are no more possible moves.  Returns true if this is the case
    public boolean gameOver() {
        // first checks if the board if full or not. If not, game is not over
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boolean verdict = noAdjacentTilesIdentical(i, j);
                if (!(verdict)) {
                    return false;
                } 
            }
        }
        
        return true; 
    }
    
    
    // method to see if adjacent tiles are identical.  Helper function for gameOver()
    public boolean noAdjacentTilesIdentical(int row, int col) {
        int left, right, up, down;
        int value = board[row][col];
        
        if (col - 1 < 0) {
            left = -1;
        } else {
            left = board[row][col - 1];
        }
        
        if (col + 1 > 3) {
            right = -1;
        } else {
            right = board[row][col + 1];
        }
        
        if (row - 1 < 0) {
            up = -1;
        } else {
            up = board[row - 1][col];
        }
        
        if (row + 1 > 3) {
            down = -1;
        } else {
            down = board[row + 1][col];
        }
        
        return !(left == value || up == value || right == value || down == value);
    }
    
    
    // methods below to shift tiles in all 4 directions
    public int shiftLeft() {
        int [][] copy = makeCopy();
        storeBoards.add(copy);
        
        boolean[] combined = new boolean[4];
        boolean movedNoCollision = false;
        int total = 0;
        
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0) {
                    int index = i;
                    
                    // keep moving left until the tile is not empty or the end of the 
                    // board has been reached
                    while (board[index - 1][j] == 0) {
                        movedNoCollision = true;
                        board[index - 1][j] = board[index][j];
                        board[index][j] = 0;
                        if (index - 1 == 0) {
                            break;
                        } else {
                            index = index - 1;
                        }
                    }
                    
                    
                    // checks for case where there are two adjacent pairs in a row
                    boolean twoAdjacentIdentical = (board[0][j] == board[1][j]
                            && board[2][j] == board[3][j] && board[2][j] != 0);
                    
                    if (index != 0 && board[index][j] != 0 
                            && !(combined[j]) && twoAdjacentIdentical) {
                        board[index - 1][j] *= 2;
                        total += board[index - 1][j];
                        board[index][j] = board[index + 1][j] * 2;
                        total += board[index][j];
                        board[index + 1][j] = 0;
                        board[index + 2][j] = 0;
                        combined[j] = true;
                        movedNoCollision = false;
                    }
                    
                    // check if two adjacent pieces are identical after moving left and 
                    // combine them if they have not already been combined yet
                    boolean adjacentIdentical = (board[index - 1][j] == board[index][j]);
                                      
                    if (index != 0 && board[index][j] != 0 
                            && !(combined[j]) && adjacentIdentical) {
                        board[index - 1][j] *= 2;
                        total += board[index - 1][j];
                        board[index][j] = 0;         
                        combined[j] = true;
                        movedNoCollision = false;                
                    }
                    
                    
                }
            }
        }
        
        // case where user presses key but no move can be made for that specific move (return -1)
        if (!(movedNoCollision) && total == 0) {
            storeBoards.removeLast();
            return -1;
        } else {
            this.generateNewTile();
            printBoard();
            return total;
        }
    }
    
    
    public int shiftRight() {
        int [][] copy = makeCopy();
        storeBoards.add(copy);
        
        boolean[] combined = new boolean[4];
        boolean movedNoCollision = false;
        int total = 0;
        
        for (int i = 2; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0) {
                    int index = i;
                    
                    // keep moving right until the tile is not empty or the end of the 
                    // board has been reached
                    while (board[index + 1][j] == 0) {
                        movedNoCollision = true;
                        board[index + 1][j] = board[index][j];
                        board[index][j] = 0;
                        if (index + 1 == 3) {
                            break;
                        } else {
                            index = index + 1;
                        }
                    }
                    
                    
                    // checks for case where there are two adjacent pairs in a row
                    boolean twoAdjacentIdentical = (board[3][j] == board[2][j]
                            && board[1][j] == board[0][j] && board[1][j] != 0);
                    
                    if (index != 3 && board[index][j] != 0 
                            && !(combined[j]) && twoAdjacentIdentical) {
                        board[index + 1][j] *= 2;
                        total += board[index + 1][j];
                        board[index][j] = board[index - 1][j] * 2;
                        total += board[index][j];
                        board[index - 1][j] = 0;
                        board[index - 2][j] = 0;
                        combined[j] = true;
                        movedNoCollision = false;
                    }
                    
                    // check if two adjacent pieces are identical after moving right and 
                    // combine them if they have not already been combined yet
                    boolean adjacentIdentical = (board[index + 1][j] == board[index][j]);
                                      
                    if (index != 3 && board[index][j] != 0 
                            && !(combined[j]) && adjacentIdentical) {
                        board[index + 1][j] *= 2;
                        total += board[index + 1][j];
                        board[index][j] = 0;
                        combined[j] = true;
                        movedNoCollision = false;                
                    }
                    
                    
                }
            }
        }
        
        // case where user presses key but no move can be made for that specific move (return -1)
        if (!(movedNoCollision) && total == 0) {
            storeBoards.removeLast();
            return -1;
        } else {
            this.generateNewTile();
            return total;
        }
    }
    
    
    public int shiftUp() {
        int [][] copy = makeCopy();
        storeBoards.add(copy);
        
        boolean[] combined = new boolean[4];
        boolean movedNoCollision = false;
        int total = 0;
        
        for (int j = 1; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (board[i][j] != 0) {
                    int index = j;
                    
                    // keep moving up until the tile is not empty or the end of the 
                    // board has been reached
                    while (board[i][index - 1] == 0) {
                        movedNoCollision = true;
                        board[i][index - 1] = board[i][index];
                        board[i][index] = 0;
                        if (index - 1 == 0) {
                            break;
                        } else {
                            index = index - 1;
                        }
                    }
                    
                    
                    // checks for case where there are two adjacent pairs in a column
                    boolean twoAdjacentIdentical = (board[i][1] == board[i][0]
                            && board[i][2] == board[i][3] && board[i][3] != 0);
                    
                    if (index != 0 && board[i][index] != 0 
                            && !(combined[i]) && twoAdjacentIdentical) {
                        board[i][index - 1] *= 2;
                        total += board[i][index - 1];
                        board[i][index] = board[i][index + 1] * 2;
                        total += board[i][index];
                        board[i][index + 1] = 0;
                        board[i][index + 2] = 0;
                        combined[i] = true;
                        movedNoCollision = false;
                    }
                    
                    // check if two adjacent pieces are identical after moving left and 
                    // combine them if they have not already been combined yet
                    boolean adjacentIdentical = (board[i][index] == board[i][index - 1]);
                                      
                    if (index != 0 && board[i][index] != 0 
                            && !(combined[i]) && adjacentIdentical) {
                        board[i][index - 1] *= 2;
                        total += board[i][index - 1];
                        board[i][index] = 0;
                        combined[i] = true;
                        movedNoCollision = false;                
                    }
                    
                    
                }
            }
        }
        
        // case where user presses key but no move can be made for that specific move (return -1)
        if (!(movedNoCollision) && total == 0) {
            storeBoards.removeLast();
            return -1;
        } else {
            this.generateNewTile();
            return total;
        }
    }
    
    
    public int shiftDown() {
        int [][] copy = makeCopy();
        storeBoards.add(copy);
        
        boolean[] combined = new boolean[4];
        boolean movedNoCollision = false;
        int total = 0;
        
        for (int j = 2; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (board[i][j] != 0) {
                    int index = j;
                    
                    // keep moving up until the tile is not empty or the end of the 
                    // board has been reached
                    while (board[i][index + 1] == 0) {
                        movedNoCollision = true;
                        board[i][index + 1] = board[i][index];
                        board[i][index] = 0;
                        if (index + 1 == 3) {
                            break;
                        } else {
                            index = index + 1;
                        }
                    }
                    
                    
                    // checks for case where there are two adjacent pairs in a column
                    boolean twoAdjacentIdentical = (board[i][2] == board[i][3]
                            && board[i][1] == board[i][0] && board[i][0] != 0);
                    
                    if (index != 3 && board[i][index] != 0 
                            && !(combined[i]) && twoAdjacentIdentical) {
                        board[i][index + 1] *= 2;
                        total += board[i][index + 1];
                        board[i][index] = board[i][index - 1] * 2;
                        total += board[i][index];
                        board[i][index - 1] = 0;
                        board[i][index - 2] = 0;
                        combined[i] = true;
                        movedNoCollision = false;
                    }
                    
                    // check if two adjacent pieces are identical after moving left and 
                    // combine them if they have not already been combined yet
                    boolean adjacentIdentical = (board[i][index] == board[i][index + 1]);
                                      
                    if (index != 3 && board[i][index] != 0 
                            && !(combined[i]) && adjacentIdentical) {
                        board[i][index + 1] *= 2;
                        total += board[i][index + 1];
                        board[i][index] = 0;
                        combined[i] = true;
                        movedNoCollision = false;                
                    }
                    
                    
                }
            }
        }
        
        // case where user presses key but no move can be made for that specific move (return -1)
        if (!(movedNoCollision) && total == 0) {
            storeBoards.removeLast();
            return -1;
        } else {
            this.generateNewTile();
            return total;
        }
    }
    
    
    // sets the current board to the most recent board state.
    // returns false if undo is pressed when the board is initialised or the board is 
    // at the very first state
    public boolean undo() {
        if (storeBoards.size() == 1 || storeBoards.size() == 0) {
            return false;
        } else {
            int[][] mostRecentGameBoard = storeBoards.removeLast();
            this.board = mostRecentGameBoard;
            return true;
        }
    }
    
    
    // prints board to the console for debugging purposes
    public void printBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(this.board[i][j]);
            }
        }
    }
    
    
    // duplicate the board to store in the storeBoards linked list
    public int[][] makeCopy() {
        int[][] copy = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                copy[i][j] = this.board[i][j];
            }
        }
        return copy;
    }
    
    
    // draw the gameboard to be displayed
    public void drawGame(Graphics gc) {
        gc.drawImage(GameComponents.blankBoard(), 0, 0, TILE_SIZE * 4, TILE_SIZE * 4, null);
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                switch (board[i][j]) {
                    case 2:
                        gc.drawImage(GameComponents.tile2(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 4:
                        gc.drawImage(GameComponents.tile4(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 8:
                        gc.drawImage(GameComponents.tile8(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 16:
                        gc.drawImage(GameComponents.tile16(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 32:
                        gc.drawImage(GameComponents.tile32(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 64:
                        gc.drawImage(GameComponents.tile64(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 128:
                        gc.drawImage(GameComponents.tile128(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 256:
                        gc.drawImage(GameComponents.tile256(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 512:
                        gc.drawImage(GameComponents.tile512(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 1024:
                        gc.drawImage(GameComponents.tile1024(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    case 2048:
                        gc.drawImage(GameComponents.tile2048(), i * TILE_SIZE, j * TILE_SIZE, 
                                TILE_SIZE, TILE_SIZE, null);
                        break;
                    default:
                }
            }
        }
    }
}