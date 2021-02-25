// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("2048");
        frame.setLocation(400, 400);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");

        // Scoreboard panel
        final JLabel showScore = new JLabel("0");

        // Moves panel
        final JLabel showMoves = new JLabel("0");


        // Asks user whether to load progress
        int choice = JOptionPane.showConfirmDialog(frame, "Do you want to load your progress");
        int[][] gameBoard = new int[4][4];
        int score = 0;
        int moves = 0;
        boolean loadGame = false;

        if (choice == JOptionPane.YES_OPTION) {
            try {
                loadGame = true;
                BufferedReader read = new BufferedReader(new FileReader("files/progress.txt"));
                String line = read.readLine();
                score = Integer.parseInt(line);
                line = read.readLine();
                moves = Integer.parseInt(line);


                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        line = read.readLine();
                        gameBoard[i][j] = Integer.parseInt(line);
                    }
                }

                read.close();
            } catch (IOException e) {
                System.out.println("Encountered IO Exception");
            }
        }

        // Checks for highest scores and lowest moves
        int highestScore = 0;
        int lowestMoves = 0;
        try {
            BufferedReader read2 = new BufferedReader(new FileReader("files/highScores.txt"));
            String line = read2.readLine();
            highestScore = Integer.parseInt(line);
            line = read2.readLine();
            lowestMoves = Integer.parseInt(line);
            read2.close();
        } catch (IOException e) {
            System.out.println("Encountered IO Exception");
        }

        // Instructions
        final String INSTRUCTIONS = "This game is called 2048 - a tile game"
                + "where the aim is to shift the tiles to combine like tiles and \n"
                + "make tiles of larger value until the 2048 tile is obtained. "
                + "The game automatically saves your progress \n"
                + "and you can choose to load this "
                + "when you play the game again. \n"
                + "\n"
                + "Controls: \n"
                + "* Use the arrow keys to shift tiles \n"
                + "* Click on the undo button to undo a move \n"
                + "* Click on PBs to reveal personal bests \n"
                + "\n"
                + "Extras to Original game: \n"
                + "I added the undo feature which allows users to access previous moves if a \n"
                + "mistake is made or if there \n"
                + "is an unlucky spawn of a tile";
        JOptionPane.showMessageDialog(frame, INSTRUCTIONS, "Instructions:",
                JOptionPane.PLAIN_MESSAGE);

        // Main playing area
        final GameCourt court = new GameCourt(status, showScore, showMoves, score, moves,
                loadGame, gameBoard, highestScore, lowestMoves, frame);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });

        final JButton showHighScore = new JButton("PBs");
        showHighScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, court.showHighScores(), "Personal Bests:",
                        JOptionPane.PLAIN_MESSAGE);
                court.resume();
            }
        });


        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.undo();
            }
        });

        control_panel.add(showScore);
        control_panel.add(reset);
        control_panel.add(showHighScore);
        control_panel.add(undo);
        control_panel.add(showMoves);
        status_panel.add(status);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.startGame();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
