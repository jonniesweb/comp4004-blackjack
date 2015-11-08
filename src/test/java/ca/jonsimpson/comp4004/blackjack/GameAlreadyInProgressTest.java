package ca.jonsimpson.comp4004.blackjack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

public class GameAlreadyInProgressTest extends AbstractSeleniumTest {
	
	/**
	 * When a game is already in progress, show the game in progress screen,
	 * allowing the user to refresh the page to wait for the game to be over.
	 * End the game and then verify that the user is able to join the new game.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGameAlreadyInProgress() throws Exception {
		driver.get(baseUrl + "/");
		
		// start a new game
		driver.findElement(By.id("start-game-button")).click();
		
		// go back to the home page
		driver.get(baseUrl + "/");
		
		// check that the game in progress page is displaying
		assertEquals("Game in progress", driver.findElement(By.cssSelector("h2")).getText());
		
		// check that the refresh button exists
		assertTrue(isElementPresent(By.id("refresh-button")));
		
		// check that the refresh button has the text Refresh
		assertEquals("Refresh", driver.findElement(By.id("refresh-button")).getAttribute("value"));
		
		// refresh the page
		driver.findElement(By.id("refresh-button")).click();
		
		// check all of the above again
		// check that the game in progress page is displaying
		assertEquals("Game in progress", driver.findElement(By.cssSelector("h2")).getText());
		
		// check that the refresh button exists
		assertTrue(isElementPresent(By.id("refresh-button")));
		
		// check that the refresh button has the text Refresh
		assertEquals("Refresh", driver.findElement(By.id("refresh-button")).getAttribute("value"));
		
		// stop the game
		reset();
		
		driver.get(baseUrl + "/");
		
		// refresh the page
		driver.findElement(By.id("refresh-button")).click();
		
		// check that the game is over and the player can join the game by
		// checking the start game button exists
		assertTrue(isElementPresent(By.id("start-game-button")));
		
	}
}
