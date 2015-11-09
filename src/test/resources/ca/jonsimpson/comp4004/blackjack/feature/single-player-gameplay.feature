Feature: Single Player Gameplay

  Background:
    Given 1 player has joined the game
    And the game is started
    
  Scenario: The player gets an extra card when they hit
    When the player presses hit
    Then the player should get an extra card
    
  Scenario: The player shouldn't be able to hit or stay after staying
    When the player stays
    Then the player shouldn't be able to hit
    And the player shouldn't be able to stay
    
