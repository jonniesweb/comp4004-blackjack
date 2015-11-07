package ca.jonsimpson.comp4004.blackjack;

import static org.junit.Assert.*;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.Test;

public class SeleniumTest {
	
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		reset();
	}
	
	/**
	 * Call the game's internal reset. Sets the game back to a fresh state.
	 */
	private void reset() {
		driver.get(baseUrl + "admin/reset");
	}
	
	/**
	 * Check that the same information is on the page when refreshing
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
	
	private String getButtonText(WebElement refreshButton) {
		return refreshButton.getAttribute("value");
	}
	
	@Test
	public void testBeginGame() throws Exception {
		driver.get(baseUrl + "/");
		
		// start a single player game
		driver.findElement(By.id("new-game-button")).click();
		
		// check that one player is playing
		assertEquals("1", driver.findElement(By.id("numPlayers")).getText());
		
		// check that the player has some cards
		assertTrue(Pattern.compile(".[0-9]+")
				.matcher(driver.findElement(By.id("yourCardTotal")).getText()).find());
		
		// check that the player is given two cards
		assertTrue(isElementPresent(By.xpath("//div[@id='player-cards']/div")));
		assertTrue(isElementPresent(By.xpath("(//div[@id='player-cards']/div)[2]")));
		
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
	
	/**
	 * When a game is already in progress, show the game in progress screen,
	 * allowing the user to refresh the page to wait for the game to be over.
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
	    assertTrue(isElementPresent(By.cssSelector("input[type=\"button\"]")));
	    
	    // check that the refresh button has the text Refresh
	    assertEquals("Refresh", driver.findElement(By.cssSelector("input[type=\"button\"]")).getAttribute("value"));
	    
	    // refresh the page
	    driver.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    // check all of the above again
	    // check that the game in progress page is displaying
	    assertEquals("Game in progress", driver.findElement(By.cssSelector("h2")).getText());
	    
	    // check that the refresh button exists
	    assertTrue(isElementPresent(By.cssSelector("input[type=\"button\"]")));
	    
	    // check that the refresh button has the text Refresh
	    assertEquals("Refresh", driver.findElement(By.cssSelector("input[type=\"button\"]")).getAttribute("value"));
	    
	    // stop the game
	    reset();
	    
	    // refresh the page
	    driver.findElement(By.cssSelector("input[type=\"button\"]")).click();
	    
	    // check that the game is over by checking the start game button exists
	    assertTrue(isElementPresent(By.id("start-game-button")));

	    
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}
	
	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
