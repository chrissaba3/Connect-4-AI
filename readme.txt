Connect 4 AI Alpha Beta Pruning

Date: Aug. 4, 2020
****************************************************
How to run:

board.java

game.java (main)

located in src folder, only these two are required to run the program.
game.java is the main class, contains input and running methods.
board.java has the algorithms, and prints the board.
*****************************************************
Known issues:
- The A row and H row do not detect wins horizontally, 
but all columns can detect wins vertically, and B-G rows can detect both. 
With this in mind I made it so the bot will prefer spots in the middle, away from the top and bottom rows. 
This does not completely ruin the game, as most games are played around the center as they have a larger advantage of winning.

-The best move is much different than what is actually chosen to be played.
 The computer will try to win on the G row, but not try to block off a player potential win, and sometimes misses its own win.