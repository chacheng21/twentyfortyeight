=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: chacheng
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. I used a 4x4 integer 2-D array to store the numbers on the board.  I used an int 
  array since the number on the board is always an integer. Using a 2-D array 
  is most appropriate here since the size is immutable and it is easy to index the
  position of the tile and update it accordingly.  The size of the board is fixed, 
  so making sure it's immutable is important.  It also stores initial values of 0, 
  which is desirable for the implementation of my game.  
  
  2. I used collections to store the specific state of the board after each move 
  as well as the change in the total score after each move.  For both, I used a 
  linked list to store a 2D integer array and integer respectively which was the 
  most appropriate since the order matters and I only need to remove from the list,
  so methods like add() and removeLast() were convenient to do this.  Undoing a move
  will get the item at the tail of the list and update the board and total score 
  accordingly.
  
  3. I used File I/O to store the state of the game (score, moves, board) each time 
  a user makes a move or undoes a move.  The game is saved automatically to
  progress.txt whenever a user makes a move or undoes a move.  To do this, the program
  overwrites the text file (first two lines being score and moves respectively) and 
  the rest is the value of the specific tile on the board.  When a user loads the game,
  they have the option to start a new game or load the previous game and the program
  will read the progress.txt file and set up the game accordingly.
  
  I also implemented it to store the highest scores and lowest moves, which will be
  overwriten if one of the two is improved.  The program reads the highScores.txt file
  whenever the game is played and shows the values in the game.

  4. I used a testable component to test all edge cases that would be hard to test if 
  playing the game since some do not come up very frequently.  I used the assertTrue,
  assertFalse, and assertEquals methods to check that various features of the game are
  performing as expected.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  The first class is TwentyFortyEight.java, which is where all the information of the state
  of the board is stored as well as the state of the boards of previous moves.  
  The methods include checking whether a user has won or can't make any new moves, 
  generating a new random tile on the board, moving tiles in all 4 directions, undoing 
  a move, and drawing the game board to be displayed.
  
  The next class is GameComponents.java, which just reads the image file for all the images
  in files and converts it into a BufferedImage for drawing the game board in 
  TwentyFortyEight.java.
  
  I added a few extra methods to GameCourt, such as startGame() that is a modified version of
  reset() where it considers the case where the user wants to load a previous game.  I also added
  methods such as resume() and saveProgress()/overwriteRecords() that both overwrite the text file
  and store the state of the game and personal bests respectively.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  Yes, the biggest stumbling block was implementing the methods to shift the tiles in any direction.
  I spent a lot of time debugging each one and making sure that all the edge cases were considered.
  It was very easy to confuse the logic in the for loops and nested loops so I spent quite a lot
  of time debugging each one to make sure that they work properly.
  
- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I believe that my private state was encapsulated well, since I made sure that the methods to get
  the values stored in these private states were only duplicates that would not modify the values in
  the private state.  



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  All libraries were used previously in class.
  
  https://gaming.stackexchange.com/questions/170665/what-is-the-probability-of-4
  
  Image of board: 
  https://scratch.mit.edu/discuss/topic/304813/?page=1#post-3152389
  
  Images for tiles were made on my own.
  
