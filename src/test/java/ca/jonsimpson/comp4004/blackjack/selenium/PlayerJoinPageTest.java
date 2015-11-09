package ca.jonsimpson.comp4004.blackjack.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PlayerJoinPageTest extends AbstractSeleniumTest {

	/**
	 * Verify that the player join page has all of the important information, as
	 * well as testing that the refresh button displays the same information.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPlayerJoinPage() throws Exception {
		driver.get(baseUrl + "/");
		
		assertEquals("Blackjack", driver.getTitle());
		WebElement refreshButton = driver.findElement(By.cssSelector("input[type=\"button\"]"));
		
		assertEquals("Refresh", getButtonText(refreshButton));
		
		WebElement startGameButton = driver.findElement(By.cssSelector("input[type=\"submit\"]"));
		assertEquals("Start Game", getButtonText(startGameButton));
		
		// check # of players
		assertEquals("1", driver.findElement(By.cssSelector("span")).getText());
		
		// refresh page
		refreshButton.click();
		
		refreshButton = driver.findElement(By.cssSelector("input[type=\"button\"]"));
		assertEquals("Refresh", getButtonText(refreshButton));
		
		startGameButton = driver.findElement(By.cssSelector("input[type=\"submit\"]"));
		assertEquals("Start Game", getButtonText(startGameButton));
		
		assertEquals("1", driver.findElement(By.cssSelector("span")).getText());
		
	}
	
}
