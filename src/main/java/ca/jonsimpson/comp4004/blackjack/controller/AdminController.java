package ca.jonsimpson.comp4004.blackjack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.jonsimpson.comp4004.blackjack.Blackjack;

@RestController
@RequestMapping("admin")
public class AdminController {
	
	@Autowired
	private Blackjack blackjack;
	
	
	@RequestMapping("new")
	public String newGame() {
		blackjack.newGame();
		
		return "success";
	}
}
