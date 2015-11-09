
## Running the Selenium Tests

From Eclipse Package View: right click on SeleniumTestSuite.java -> Run as... -> Junit Test.
Requires Firefox.


## Requirements

- REQ-#1 User is able to join game
- REQ-#2 User is able to start game
- REQ-#3 User is able to see the number of current players
- REQ-#4 Only one game is able to be played at a time
- REQ-#5 User is unable to join a game in progress
- REQ-#6 User is able to join a game after a game finishes
- REQ-#7 User is shown the sum of their cards
- REQ-#8 User is shown the rank of their cards
- REQ-#9 User is shown the suit of their cards
- REQ-#10 Players start out with two cards
- REQ-#11 User is able to Hit
- REQ-#12 User is able to Stay
- REQ-#13 User must wait for their turn
- REQ-#14 Players play in sequential order
- REQ-#15 Players are able to see all but one of their opponents cards
- REQ-#16 Players go bust if their card count goes over 21
- REQ-#17 User is shown who the winner is at the end of the game
- REQ-#18 Players are dealt cards from a 52 card deck
- REQ-#19 After choosing stay, player is unable to do anything until game end
- REQ-#20 Multiple players can play at the same time

## Traceability Matrix

                                REQ#
                                1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20
TEST                                                                                                          
PlayerJoinPageTest              x   x   x                                                                     
GameAlreadyInProgressTest                   x   x   x                                                         
SinglePlayerGameTest                                    x   x   x   x   x   x                   x   x         
MultiplePlayerGameTest                                  x   x   x   x   x   x   x   x   x   x   x   x   x   x 

