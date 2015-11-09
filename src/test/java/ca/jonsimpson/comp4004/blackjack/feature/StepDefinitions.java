package ca.jonsimpson.comp4004.blackjack.feature;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.jonsimpson.comp4004.blackjack.Application;
import ca.jonsimpson.comp4004.blackjack.Blackjack;
import ca.jonsimpson.comp4004.blackjack.Card;
import ca.jonsimpson.comp4004.blackjack.Card.Suit;
import ca.jonsimpson.comp4004.blackjack.InvalidStateException;
import ca.jonsimpson.comp4004.blackjack.Player;
import ca.jonsimpson.comp4004.blackjack.Card.Rank;
import ca.jonsimpson.comp4004.blackjack.State.StateType;
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
	
	private Player player;
	
	private Exception exception;
	
	@Given("the game is not running")
	public void the_game_is_not_running() {
		assertTrue(blackjack.isPlayerJoinState());
	}
	
	@Given("^(\\d+) player has joined the game$")
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
	
	@Given("^the game is started$")
	@When("^the player starts the game$")
	public void the_player_starts_the_game() throws Throwable {
		blackjack.startGame();
	}
	
	@Then("^the game should start$")
	public void the_game_should_start() throws Throwable {
		assertTrue(blackjack.isGameInProgressState());
	}
	
	@Then("^the player should have (\\d+) cards$")
	public void the_player_should_have_cards(int cards) throws Throwable {
		assertThat(blackjack.getPlayerManager().getPlayerOrder().get(0).getCards().size(), is(2));
	}
	
	@Given("^(\\d+) players have joined the game$")
	public void players_have_joined_the_game(int players) throws Throwable {
		player_joins_the_game(players);
	}
	
	@Given("^player (\\d+) is the first player$")
	public void player_is_the_first_player(int arg1) throws Throwable {
		player = blackjack.getPlayerManager().getPlayerOrder().get(0);
	}
	
	@Given("^it is player (\\d+)'s turn$")
	public Player it_is_player_s_turn(int arg1) throws Throwable {
		Player player = getPlayer(arg1);
		blackjack.setCurrentPlayer(player);
		
		return player;
	}
	
	@When("^player (\\d+) finishes their turn$")
	public void player_finishes_their_turn(int arg1) throws Throwable {
		blackjack.nextPlayersTurn();
	}
	
	@Then("^it is player (\\d+)s turn$")
	public void it_is_player_s_turn_assert(int player) throws Throwable {
		assertThat(blackjack.getStatus(getPlayer(player)), is(Blackjack.STATUS_GO));
	}
	
	private Player getPlayer(int player) {
		return blackjack.getPlayerManager().getPlayerOrder().get(player - 1);
	}
	
	@When("^player (\\d+) tries to hit$")
	public void player_tries_to_hit(int player) throws Throwable {
		try {
			blackjack.hit(getPlayer(player));
		} catch (Exception e) {
			exception = e;
		}
	}
	
	@Then("^player (\\d+) shouldn't be able to")
	public void player_shouldn_t_be_able_to(int arg1) throws Throwable {
		assertEquals(exception.getClass(), InvalidStateException.class);
	}
	
	@When("^player (\\d+) tries to stay$")
	public void player_tries_to_stay(int player) throws Throwable {
		try {
			blackjack.stay(getPlayer(player));
		} catch (Exception e) {
			exception = e;
		}
	}
	
	@Given("^player (\\d+) has stay$")
	public void player_has_stay(int player) throws Throwable {
		blackjack.stay(getPlayer(player));
	}
	
	@When("^player (\\d+) hits$")
	@Given("^player (\\d+) has hit$")
	public void player_has_hit(int player) throws Throwable {
		blackjack.hit(getPlayer(player));
	}
	
	@Given("^player (\\d+) has a TWO and a THREE$")
	public void player_has_a_TWO_and_a_THREE(int player) throws Throwable {
	    List<Card> cards = getPlayer(player).getCards();
		cards.clear();
		cards.add(new Card(Rank.TWO, Suit.CLUB));
		cards.add(new Card(Rank.THREE, Suit.CLUB));
	}
	
	@Given("^player (\\d+) has bust$")
	public void player_has_bust(int player) throws Throwable {
		List<Card> cards = getPlayer(player).getCards();
		cards.clear();
		cards.add(new Card(Rank.ACE, Suit.CLUB));
		cards.add(new Card(Rank.QUEEN, Suit.CLUB));
		cards.add(new Card(Rank.QUEEN, Suit.HEART));
		blackjack.nextPlayersTurn();
	}
	
	@When("^the player presses hit$")
	public void the_player_presses_hit() throws Throwable {
		blackjack.hit(getPlayer(1));
	}
	
	@Then("^the player should get an extra card$")
	public void the_player_should_get_an_extra_card() throws Throwable {
		assertThat(getPlayer(1).getCards().size(), is(3));
	}
	
	@When("^the player stays$")
	public void the_player_stays() throws Throwable {
		blackjack.stay(getPlayer(1));
	}
	
	@Then("^the player shouldn't be able to hit$")
	public void the_player_shouldn_t_be_able_to_hit() throws Throwable {
		player_tries_to_hit(1);
		player_shouldn_t_be_able_to(1);
	}

	@Then("^the player shouldn't be able to stay$")
	public void the_player_shouldn_t_be_able_to_stay() throws Throwable {
		player_tries_to_stay(1);
		player_shouldn_t_be_able_to(1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
