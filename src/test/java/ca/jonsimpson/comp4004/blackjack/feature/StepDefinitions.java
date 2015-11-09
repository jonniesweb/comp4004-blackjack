package ca.jonsimpson.comp4004.blackjack.feature;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.jonsimpson.comp4004.blackjack.Application;
import ca.jonsimpson.comp4004.blackjack.Blackjack;
import cucumber.api.java.en.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class StepDefinitions {
	
	@Autowired
	private Blackjack blackjack;
	
	private String gameInProgressPlayerId;
	
	@Given("the game is not running")
	public void the_game_is_not_running() {
		assertTrue(blackjack.isPlayerJoinState());
	}
	
	@When("^(\\d+) player joins the game$")
	public void player_joins_the_game(int players) throws Throwable {
		for (int i = 0; i < players; i++) {
			blackjack.newPlayer();
		}
	}
	
	@Then("^the number of players should be (\\d+)$")
	public void the_number_of_players_should_be(int players) throws Throwable {
		assertThat(blackjack.getPlayerManager().getSize(), is(players));
	}
	
	@Given("^a game is in progress$")
	public void a_game_is_in_progress() throws Throwable {
	    blackjack.newPlayer();
	    blackjack.startGame();
	}

	@When("^a player attempts to join the game$")
	public void a_player_attempts_to_join_the_game() throws Throwable {
		gameInProgressPlayerId = blackjack.newPlayer();
	}

	@Then("^the player is unable to join the game$")
	public void the_player_is_unable_to_join_the_game() throws Throwable {
		assertNull(gameInProgressPlayerId);
		assertThat(blackjack.getPlayerManager().getSize(), is(1));
	}

	@Given("^a player has joined the game$")
	public void a_player_has_joined_the_game() throws Throwable {
		blackjack.newPlayer();
	}

	@When("^the player starts the game$")
	public void the_player_starts_the_game() throws Throwable {
	    blackjack.startGame();
	}

	@Then("^the game should start$")
	public void the_game_should_start() throws Throwable {
		assertTrue(blackjack.isGameInProgressState());
	}
}
