package ca.jonsimpson.comp4004.blackjack.selenium;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;
import org.openqa.selenium.By;

public class SinglePlayerGameTest extends AbstractSeleniumTest {
	
	/**
	 * Check that when the user plays a single player game that all of the
	 * important information is displayed for them. Also verify that they're
	 * able to play and end the game.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSinglePlayerGame() throws Exception {
		driver.get(baseUrl + "/");
		
		// start a single player game
		driver.findElement(By.id("start-game-button")).click();
		
		// check that one player is playing
		assertEquals("1", driver.findElement(By.id("numPlayers")).getText());
		
		// check that the player has some cards
		assertTrue(Pattern.compile(".[0-9]+")
				.matcher(driver.findElement(By.id("yourCardTotal")).getText()).find());
		
		// check that the player is given two cards
		assertTrue(isElementPresent(By.cssSelector("div.card")));
		assertTrue(isElementPresent(By.xpath("//div[@id='player-section']/div[2]/div")));
		
		// check that the rank is there
		assertTrue(Pattern.compile(".[a-zA-Z]+")
				.matcher(driver.findElement(By.cssSelector("span.rank")).getText()).find());
		
		// check that the suit is there
		assertTrue(Pattern.compile(".[a-zA-Z]+")
				.matcher(driver.findElement(By.cssSelector("span.suit")).getText()).find());
		
		// check that the hit button says Hit
		assertEquals("Hit", driver.findElement(By.id("hit-button")).getAttribute("value"));
		
		// check that the stay button says Stay
		assertEquals("Stay", driver.findElement(By.id("stay-button")).getAttribute("value"));
		
		// click on the stay button
		driver.findElement(By.id("stay-button")).click();
		
		// click on the new game button
		driver.findElement(By.id("new-game-button")).click();
		
	}
}
