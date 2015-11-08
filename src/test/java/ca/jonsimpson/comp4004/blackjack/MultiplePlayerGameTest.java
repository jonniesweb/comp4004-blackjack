package ca.jonsimpson.comp4004.blackjack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

public class MultiplePlayerGameTest extends AbstractSeleniumTest {
	
	/**
	 * Test the expected behaviour and looks of two players playing a game of
	 * blackjack from joining the game, playing, and finishing the game.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMultiplePlayersPlaying() throws Exception {
		driver.get(baseUrl + "/");
		
		// get player 1 url
		String player1 = driver.getCurrentUrl();
		
		driver.get(baseUrl + "/");
		
		// get player 2 url
		String player2 = driver.getCurrentUrl();
		
		// start the game
		driver.findElement(By.id("start-game-button")).click();
		
		// check various page elements
		assertEquals("Waiting for another player to go", driver
				.findElement(By.xpath("//p[2]/span")).getText());
		assertEquals("2", driver.findElement(By.id("numPlayers")).getText());
		assertTrue(isElementPresent(By.cssSelector("div.player-info")));
		assertTrue(isElementPresent(By
				.cssSelector("div.player-info > div.player-cards > div.card > span.rank")));
		assertTrue(isElementPresent(By
				.cssSelector("div.player-info > div.player-cards > div.card > span.suit")));
		assertFalse(isElementPresent(By.id("hit-button")));
		assertFalse(isElementPresent(By.id("stay-button")));
		
		// switch to p1
		driver.get(player1);
		
		// check various page elements
		assertEquals("It is your turn", driver.findElement(By.xpath("//p[2]/span")).getText());
		assertTrue(isElementPresent(By.id("hit-button")));
		assertTrue(isElementPresent(By.id("stay-button")));
		assertTrue(isElementPresent(By.cssSelector("div.player-info")));
		assertTrue(isElementPresent(By
				.cssSelector("div.player-info > div.player-cards > div.card > span.rank")));
		assertTrue(isElementPresent(By
				.cssSelector("div.player-info > div.player-cards > div.card > span.suit")));
		assertEquals("2", driver.findElement(By.id("numPlayers")).getText());
		assertFalse(isElementPresent(By.xpath("//div[@class='player-info']/div[2]/div")));
		
		driver.findElement(By.id("stay-button")).click();
		
		// check various page elements
		assertEquals("Waiting for another player to go", driver
				.findElement(By.xpath("//p[2]/span")).getText());
		assertFalse(isElementPresent(By.id("hit-button")));
		assertFalse(isElementPresent(By.id("stay-button")));
		
		// switch to player 2
		driver.get(player2);
		
		// check various page elements
		assertEquals("It is your turn", driver.findElement(By.xpath("//p[2]/span")).getText());
		assertTrue(isElementPresent(By.id("hit-button")));
		assertTrue(isElementPresent(By.id("stay-button")));
		
		driver.findElement(By.id("stay-button")).click();
		
		// game is over
		// check various page elements
		assertTrue(isElementPresent(By.id("new-game-button")));
		assertTrue(isElementPresent(By.xpath("//div[@id='player-section']/p/span[2]")));
		assertTrue(isElementPresent(By.xpath("//div/div/p/span[2]")));
		
		// switch to player 1
		driver.get(player1);
		
		// check various page elements
		assertTrue(isElementPresent(By.id("new-game-button")));
		assertTrue(isElementPresent(By.xpath("//div[@id='player-section']/p/span[2]")));
		assertTrue(isElementPresent(By.xpath("//div/div/p/span[2]")));
		
	}
}
