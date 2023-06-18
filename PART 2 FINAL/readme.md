#Part 2
## A. Member Contributions

No | ID         | Name                        | Task descriptions                                                        | Contribution %
-- | ---------- | ----                        | -----------------                                                        | :----------:
1  | 1211103123 |  AL ARSHAD BIN AL HANISHAM  |Recreated and reorganized the class files to implement better class design and data structures by creating the classes, Player, Game, Deck and Cards.                                      |29%
  |            |                             ||Implemented the accurate way of calculating the winner of the trick and prompted the winner to choose the card to start the next round  |
  |            |                             ||Added the functionality to save the game state to a file or database, allowing players to exit and resume the game later and Implemented the logic to restore the game state when resuming a game from a file or database   |
 |            |      || Modified the determind winner condition to allow the winner of the trick to be calculated only when all the players have played a card using cardsplayed                         |                                                           
  |            |      || Modified the pass command to be calculated that when player's pass their turn the winner is determined based on what cards are played       |
 |            |      ||Added support for a GUI through gamemenu which uses the swing library
  |            |      ||Made the GUI more visually appealing by adding confetti feature and a way to display a background image 
2  | 1211103216           |  SOFEA HAZREENA BINTI HASDI    |Modified the code to display the score board for each trick|     29%                 |
  |            |      ||Modified the code to ensure that after each round, it prints out the score for each player.|                      |
 |            |      ||Rearranged the code within the loop to ensure that the output is printed in the correct sequence and is readable.|                     |
|            |     ||Made the GUI more visually appealing by designing the background and ensuring that the buttons are centered.                      |
|            |     ||Implemented the logic for scoring based on suits and ranks, successfully printing out the correct remaining cards for each player.                     |             
|           |      ||Added the functionality of a save button in the GUI and included features that allow the user to enter the name of the saved file. |                      |
|            |       ||Implemented the background color setting for the button in the GUI.       |                    |
3  |  1211103194| NUR FARAHIYA AIDA BINTI ABDUL RAZAK     | Implemented the logic to restore the game state in GUI when resuming a game from a file or database.  |29 %
  |            |      |                |Implemented the functionality for a player to draw cards from the deck until a playable card is available if they cannot follow suit or rank.
  |            |      |                    |Implemented the logic to finish a round of the game correctly, including calculating and displaying the score of each player.
  |            |      |                 |Implemented the getValidCardFromPlayer() method to handle user input for playing a card, drawing a card, passing a turn, saving the game, or exiting the game.
  |            |      |                 |Implemented the loadGame() method to load the game state, including restore player hands, center cards, and scores.   
  |            |      |    |Collaborated with other developers to ensure consistency between the GUI and console output and resolved any issues or conflicts that arose.                
4  |   1211103373         |   MUHAMMAD ALIF BIN KHABALI   |Implemented the reset game functionality after first round is completed               | 13%
 |            |      ||Modified the logic of saving the game     |
|             |       ||Attempted to create a GUI playing mode using FXML.     |      
|             |       ||Fixed some minor bugs    | 
   

## B. Part 1 Feature Completion (Latest)

Mark Y for Complete, P for Partial done, N for Not implemented.

No | Feature                                                                         | Completed (Y/P/N)
-- | ------------------------------------------------------------------------------- | -----------------
1  | All cards should be faced up to facilitate checking.                            |Y
2  | Start a new game with randomized 52 cards.                                      |Y
3  | The first card in the deck is the first lead card and is placed at the center.  |Y
4  | The first lead card determines the first player.                                |Y
5  | Deal 7 cards to each of the 4 players.                                          |Y
6  | All players must follow the suit or rank of the lead card.                      |Y
7  | The highest-rank card with the same suit as the lead card wins the trick.       |Y
8  | The winner of a trick leads the next card.                                      |Y


## C. Part 2 Feature Completion

Mark Y for Complete, P for Partial done, N for Not implemented.

No | Feature                                                                          | Completed (Y/P/N)
-- | -------------------------------------------------------------------------------- | -----------------
1  | If a player cannot follow suit or rank, the player must draw from the deck until a card can be played.      |Y
2  | When the remaining deck is exhausted and the player cannot play, the player does not play in the trick.                |Y
3  | Finish a round of game correctly. Display the score of each player.              |Y
4  | Can exit and save the game (use file or database).                               |Y
5  | Can resume the game. The state of the game is restored when resuming a game (use file or database).     |Y
6  | Reset the game. All scores become zero. Round and trick number restart from 1.   |Y
7  | Support GUI playing mode (cards should be faced up or down as in the real game).The GUI can be in JavaFX, Swing, Spring, or Android.  |P
8  | Keep the console output to facilitate checking.The data in console output and the GUI must tally.                                |Y



## D. Link to Part 2 GitHub Repo

(https://github.com/AidaRazak/Javers)
