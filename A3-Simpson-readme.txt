
## Running the Cucumber Tests

Simply run the file CucumberTest.java as a JUnit test. From Eclipse Package Manager View: Right click on CucumberTest.java -> Run As... -> JUnit Test




## Requirements

- REQ-#1 User is able to join game
- REQ-#2 User is able to start game
- REQ-#3 Only one game is able to be played at a time
- REQ-#4 User is unable to join a game in progress
- REQ-#5 User is able to join a game after a game finishes
- REQ-#6 Players start out with two cards
- REQ-#7 User is able to Hit
- REQ-#8 User is able to Stay
- REQ-#9 User must wait for their turn
- REQ-#10 Players play in sequential order
- REQ-#11 Players go bust if their card count goes over 21
- REQ-#12 User is shown who the winner is at the end of the game
- REQ-#13 After choosing stay, player is unable to do anything until game end
- REQ-#14 Multiple players can play together

## Traceability Matrix

                                                                           REQ#
                                                                           1   2   3   4   5   6   7   8   9   10  11  12  13  14
TEST                                                                                                          
game-setup.feature
  A new player should be added to the game if the game isn't running       x                                                   x 
  A player shouldn't be allowed to join a game in progress                         x   x   x                                     
  A player should be able to start the game                                x   x                                                 
  Players start off with two cards each                                                        x                                 

single-player-gameplay.feature
  The player gets an extra card when they hit                                                      x                             
  The player shouldn't be able to hit or stay after staying                                            x                   x     

multiplayer-gameplay.feature
  Users play in sequential order                                                                               x       x         
  Player cannot hit or stay go when it is another player's turn                                            x                     
  A player goes again immediately when the other player has stayed/bust                                            x             
                                                                                                                                 