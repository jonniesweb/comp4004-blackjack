package ca.jonsimpson.comp4004.blackjack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	private static final Log log = LogFactory.getLog(Config.class);
	
	@Bean
	public Blackjack getBlackjack() {
		log.debug("created blackjack bean");
		return new Blackjack();
	}
	
	@Bean
	public PlayerManager getPlayerManager() {
		log.debug("created player manager");
		return new PlayerManager();
	}
}
