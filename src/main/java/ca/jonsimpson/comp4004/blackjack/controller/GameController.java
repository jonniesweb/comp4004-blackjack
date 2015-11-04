package ca.jonsimpson.comp4004.blackjack.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.jonsimpson.comp4004.blackjack.Blackjack;
import ca.jonsimpson.comp4004.blackjack.InvalidStateException;
import ca.jonsimpson.comp4004.blackjack.Player;
import ca.jonsimpson.comp4004.blackjack.UnknownPlayerException;

@Controller
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
	 * @throws UnknownPlayerException
	 * @throws InvalidStateException 
	 */
	@RequestMapping(value = "hit", method = POST)
	public String hit(Model model, @RequestParam String id) throws UnknownPlayerException, InvalidStateException {
		try {
			getGame().hit(getPlayer(id));
		} catch (UnknownPlayerException e) {
			return getNewPlayerRedirect();
		}
		model.addAttribute("id", id);
		return doRedirect(id);
	}
	
	/**
	 * The player takes no more additional cards and waits for the game to
	 * finish and a winner to be announced.
	 * 
	 * @param id
	 * @return
	 * @throws UnknownPlayerException
	 * @throws InvalidStateException 
	 */
	@RequestMapping(value = "stay", method = POST)
	public String stay(Model model, @RequestParam String id) throws UnknownPlayerException, InvalidStateException {
		try {
			getGame().stay(getPlayer(id));
		} catch (UnknownPlayerException e) {
			return getNewPlayerRedirect();
		}
		model.addAttribute("id", id);
		return doRedirect(id);
	}

	private String getNewPlayerRedirect() {
		return "redirect:new";
	}
	
	@RequestMapping(method = GET)
	public String getStatus(Model model, @RequestParam(required=false) String id) throws UnknownPlayerException {
		Player player;
		try {
			player = getPlayer(id);
		} catch (UnknownPlayerException e) {
			return getNewPlayerRedirect();
		}
		
		model.addAttribute("id", id);
		model.addAttribute("status", getGame().getStatus(player));
		model.addAttribute("gameOver", getGame().isGameOver());
		model.addAttribute(game);
		model.addAttribute("player", player);
		
		return "status";
	}
	
	@RequestMapping("new")
	public String newPlayer(Model model) {
		String newId = game.newPlayer();
		
		// tell the user that they have to wait for a new game to start
		if (newId == null) {
			return "game-in-progress";
		}
		
		model.addAttribute("id", newId);
		
		return doRedirect(newId);
	}

	private static String doRedirect(String newId) {
		return "redirect:?id=" + newId;
	}
	
	
	private Player getPlayer(String id) throws UnknownPlayerException {
		return game.getPlayerManager().getPlayer(id);
	}
	
	public Blackjack getGame() {
		return game;
	}
	
}
