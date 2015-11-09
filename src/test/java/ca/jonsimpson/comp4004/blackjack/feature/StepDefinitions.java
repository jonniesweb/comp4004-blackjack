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
	Blackjack blackjack;
	
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
}
