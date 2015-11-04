package ca.jonsimpson.comp4004.blackjack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ca.jonsimpson.comp4004.blackjack.Blackjack;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private Blackjack blackjack;
	
	@RequestMapping()
	public String doAdminPage() {
		return "admin";
	}
	
	@RequestMapping("start")
	public String startGame() {
		blackjack.startGame();
		
		return doRedirect();
	}
	
	@RequestMapping("end")
	public String playerJoin() {
		blackjack.newGame();
		return doRedirect();
	}
	
	@RequestMapping("reset")
	public String resetGame() {
		blackjack.reset();
		
		return doRedirect();
	}
	
	private String doRedirect() {
		return "redirect:";
	}
}
