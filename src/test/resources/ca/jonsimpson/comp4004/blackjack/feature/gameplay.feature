Feature: Gameplay



  Scenario Outline: Add a new player should be added to the game if the game isn't running
    Given the game is not running
    When <numPlayers> player joins the game
    Then the number of players should be <numPlayers>
    
    Examples:
    | numPlayers |
    | 1          |
    | 2          |
    | 4          |

  Scenario: A player shouldn't be allowed to join a game in progress
    Given a game is in progress
    When a player attempts to join the game
    Then the player is unable to join the game
    
  Scenario: A player should be able to start the game
    Given a player has joined the game
    When the player starts the game
    Then the game should start
    
  