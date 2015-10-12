package ca.jonsimpson.comp4004.blackjack.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.jonsimpson.comp4004.blackjack.Blackjack;
import ca.jonsimpson.comp4004.blackjack.Player;
import ca.jonsimpson.comp4004.blackjack.PlayerDoesntExistException;
import ca.jonsimpson.comp4004.blackjack.PlayerManager;

@RequestMapping("/game")
public class GameController {
	
	@Autowired
	private Blackjack game;
	
	/**
	 * The player gets one more card. If the card causes the sum of their cards
	 * to go over 21 then the player is declared 'bust'.
	 * 
	 * @param id
	 * @return
	 * @throws PlayerDoesntExistException
	 */
	@RequestMapping("hit")
	public String hit(@RequestBody String id) throws PlayerDoesntExistException {
		getGame().hit(getPlayer(id));
		return null;
	}
	
	/**
	 * The player takes no more additional cards and waits for the game to
	 * finish and a winner to be announced.
	 * 
	 * @param id
	 * @return
	 * @throws PlayerDoesntExistException
	 */
	@RequestMapping("stay")
	public String stay(@RequestBody String id) throws PlayerDoesntExistException {
		getGame().stay(getPlayer(id));
		return null;
	}
	
	@RequestMapping(method = GET)
	public String getStatus() {
		getGame().getStatus();
		return null;
	}
	
	private Player getPlayer(String id) throws PlayerDoesntExistException {
		return PlayerManager.getPlayer(id);
	}
	
	public Blackjack getGame() {
		return game;
	}
	
}
