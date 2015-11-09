Feature: Multiplayer Gameplay

  Background:
    Given 2 players have joined the game
    And the game is started

  Scenario: Users play in sequential order
    Given player 1 is the first player
    When player 1 finishes their turn
    Then player 2 starts their turn
    
  Scenario Outline: Player cannot hit or stay go when it is another player's turn
    Given it is player 1's turn
    When player 2 tries to <action>
    Then player 2 shouldn't be able to <action>
    
    Examples:
      | action |
      | hit    |
      | stay   |
      
  Scenario Outline: A player goes again immediately when the other player has stayed
    Given player 1 has <action>
    And player 2 has a TWO and a THREE
    When player 2 hits
    Then it is player 2's turn
    
    Examples:
      | action |
      | stay   |
      | bust   |
    
    
    
    