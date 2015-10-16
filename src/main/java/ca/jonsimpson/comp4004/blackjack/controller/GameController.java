package ca.jonsimpson.comp4004.blackjack.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.jonsimpson.comp4004.blackjack.Blackjack;
import ca.jonsimpson.comp4004.blackjack.Player;
import ca.jonsimpson.comp4004.blackjack.PlayerDoesntExistException;

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
	 * @throws PlayerDoesntExistException
	 */
	@RequestMapping(value = "hit", method = POST)
	public String hit(Model model, @RequestParam String id) throws PlayerDoesntExistException {
		getGame().hit(getPlayer(id));
		model.addAttribute("id", id);
		return doRedirect(id);
	}
	
	/**
	 * The player takes no more additional cards and waits for the game to
	 * finish and a winner to be announced.
	 * 
	 * @param id
	 * @return
	 * @throws PlayerDoesntExistException
	 */
	@RequestMapping(value = "stay", method = POST)
	public String stay(Model model, @RequestParam String id) throws PlayerDoesntExistException {
		getGame().stay(getPlayer(id));
		model.addAttribute("id", id);
		return doRedirect(id);
	}
	
	@RequestMapping(method = GET)
	public String getStatus(Model model, @RequestParam String id) throws PlayerDoesntExistException {
		
		model.addAttribute("id", id);
		model.addAttribute("status", getGame().getStatus(getPlayer(id)));
		
		return "status";
	}
	
	@RequestMapping("new")
	public String newPlayer(Model model) {
		String newId = game.getPlayerManager().newPlayer();
		
		model.addAttribute("id", newId);
		
		return doRedirect(newId);
	}

	private static String doRedirect(String newId) {
		return "redirect:?id=" + newId;
	}
	
	
	private Player getPlayer(String id) throws PlayerDoesntExistException {
		return game.getPlayerManager().getPlayer(id);
	}
	
	public Blackjack getGame() {
		return game;
	}
	
}
