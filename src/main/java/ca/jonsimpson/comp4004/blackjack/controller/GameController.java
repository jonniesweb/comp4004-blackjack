package ca.jonsimpson.comp4004.blackjack.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/game")
public class GameController {
	

	@RequestMapping("hit")
	public String hit(@RequestBody String id) {
		
		return null;
	}
	
	@RequestMapping("stay")
	public String stay(@RequestBody String id) {
		
		return null;
	}
	
	@RequestMapping(method=GET)
	public String getStatus() {
		
		return null;
	}
}
