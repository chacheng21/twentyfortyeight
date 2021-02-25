import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.*;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact with one another.
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private TwentyFortyEight game; // TwentyFortyEight Game Board


    private boolean playing = false; // whether the game is running
    private boolean loadGame = false;
    private int score = 0; // Current total points
    private int moves = 0; // Current total moves
    private int highScore = 0; // Highest score
    private int lowMoves = 0; // Lowest moves
    private int[][] savedGameBoard = new int[4][4];
    private LinkedList<Integer> pointsPerRound = new LinkedList<Integer>();
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel showScore;
    private JLabel showMoves;
    private JFrame frame;

    // Game constants
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 400;


    public GameCourt(JLabel status, JLabel showScore, JLabel showMoves,
            int savedScore, int savedMoves, boolean loadGame, int[][] savedGameBoard,
            int highestScore, int lowestMoves, JFrame frames) {

        this.highScore = highestScore;
        this.lowMoves = lowestMoves;
        this.frame = frames;

        if (loadGame) {
            this.savedGameBoard = savedGameBoard;
            this.loadGame = loadGame;
            this.score = savedScore;
            this.moves = savedMoves;
        }




        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the tiles to shift long as an arrow key is pressed, by
        // shifting the tiles accordingly.
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int points = 0;

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    points = game.shiftLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    points = game.shiftRight();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    points = game.shiftDown();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    points = game.shiftUp();
                }

                if (points >= 0) {
                    repaint();
                    score += points;
                    pointsPerRound.add(points);
                    moves += 1;
                    showScore.setText("Score: " + Integer.toString(score));
                    showMoves.setText("Moves: " + Integer.toString(moves));
                }

                if (game.contains2048()) {
                    status.setText("Game Over!");
                    playing = false;

                    if (moves < lowMoves) {
                        lowMoves = moves;
                        try {
                            overwriteRecords();
                        } catch (IOException e1) {
                            System.out.println("Encountered IOException");
                        }
                    }

                    if (score > highScore) {
                        highScore = score;
                        try {
                            overwriteRecords();
                        } catch (IOException e1) {
                            System.out.println("Encountered IOException");
                        }
                    }

                    JOptionPane.showMessageDialog(frame,
                            "Congrats! You have gotten 2048 in " +
                            Integer.toString(moves) + " moves!",
                            "You win!",
                            JOptionPane.PLAIN_MESSAGE);
                }

                if (game.gameOver()) {
                    status.setText("Game Over!");
                    playing = false;

                    if (score > highScore) {
                        highScore = score;
                        try {
                            overwriteRecords();
                        } catch (IOException e1) {
                            System.out.println("Encountered IOException");
                        }
                    }

                    JOptionPane.showMessageDialog(frame, "Better Luck Next Time!",
                            "You Lose!",
                            JOptionPane.PLAIN_MESSAGE);
                }

                try {
                    saveProgress();
                } catch (IOException e1) {
                    System.out.println("Encountered IOException");
                }

            }

            public void keyReleased(KeyEvent e) {
            }
        });

        this.status = status;
        this.showScore = showScore;
        this.showMoves = showMoves;

    }

    /**
     * start game
     */
    public void startGame() {
        game = new TwentyFortyEight();
        if (loadGame) {
            game.setBoard(savedGameBoard);
        }

        playing = true;
        status.setText("Running...");
        showScore.setText("Score: " + Integer.toString(score));
        showMoves.setText("Moves: " + Integer.toString(moves));

        requestFocusInWindow();
    }

    /**
     * resume game after pausing to check leaderboards
     */
    public void resume() {
        playing = true;

        requestFocusInWindow();
    }

    /**
     * resets game
     */
    public void reset() {
        game = new TwentyFortyEight();
        score = 0;
        moves = 0;
        pointsPerRound = new LinkedList<Integer>();

        playing = true;
        status.setText("Running...");
        showScore.setText("Score: " + Integer.toString(score));
        showMoves.setText("Moves: " + Integer.toString(moves));


        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * Undo undoes a move and modifies the score and moves
     */
    public void undo() {
        boolean check = game.undo();
        if (check) {
            repaint();
            score -= pointsPerRound.removeLast();
            moves -= 1;

            playing = true;
            status.setText("Running...");
            showScore.setText("Score: " + Integer.toString(score));
            showMoves.setText("Moves: " + Integer.toString(moves));

            try {
                saveProgress();
            } catch (IOException e1) {
                System.out.println("Encountered IOException");
            }

            // Make sure that this component has the keyboard focus
            requestFocusInWindow();
        } else {
            requestFocusInWindow();
        }
    }

    /**
     * SaveProgress saves the progress of the game
     * @throws IOException
     */
    public void saveProgress() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("files/progress.txt", false));
        try {
            bw.write(Integer.toString(score));
            bw.newLine();
            bw.write(Integer.toString(moves));
            bw.newLine();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    bw.write(Integer.toString(game.getBoard()[i][j]));
                    bw.newLine();
                }
            }

            bw.close();

        } catch (IOException e) {
            bw.close();
            System.out.println("Encountered IOException");
        }
    }

    /**
     * OverwriteRecords replaces highest score or lowest moves if necessary
     * @throws IOException
     */
    public void overwriteRecords() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("files/highScores.txt", false));
        try {
            bw.write(Integer.toString(highScore));
            bw.newLine();
            bw.write(Integer.toString(lowMoves));
            bw.close();
        } catch (IOException e) {
            bw.close();
            System.out.println("Encountered IOException");
        }
    }

    /**
     * shows personal bests in score and oves
     */
    public String showHighScores() {
        String highScoreLabel = ("Highest Score: " + Integer.toString(highScore) + "\n");
        String lowMovesLabel = ("Lowest Moves: " + Integer.toString(lowMoves) + "\n");
        return highScoreLabel + lowMovesLabel;
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.drawGame(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
