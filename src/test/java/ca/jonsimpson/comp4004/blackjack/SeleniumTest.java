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
	
	private static WebDriver driver;
	private static String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	
	@BeforeClass
	public static void beforeClass() {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
	}
	
	/**
	 * Setup the Firefox driver
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		reset();
	}
	
	/**
	 * Call the game's internal reset. Sets the game back to a fresh state.
	 */
	private void reset() {
		driver.get(baseUrl + "admin/reset");
	}
	
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
	
	private String getButtonText(WebElement refreshButton) {
		return refreshButton.getAttribute("value");
	}
	
	/**
	 * Check that when the user plays a single player game that all of the
	 * important information is displayed for them. Also verify that they're
	 * able to play and end the game.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBeginGame() throws Exception {
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
	
	@Test
	public void testMultiplePlayersPlaying() throws Exception {
		driver.get(baseUrl + "/");
		
		String player1 = driver.getCurrentUrl();
		
		driver.get(baseUrl + "/");
		
		String player2 = driver.getCurrentUrl();
		
		
		driver.findElement(By.id("start-game-button")).click();
	    assertEquals("Waiting for another player to go", driver.findElement(By.xpath("//p[2]/span")).getText());
	    assertEquals("2", driver.findElement(By.id("numPlayers")).getText());
	    assertTrue(isElementPresent(By.cssSelector("div.player-info")));
	    assertTrue(isElementPresent(By.cssSelector("div.player-info > div.player-cards > div.card > span.rank")));
	    assertTrue(isElementPresent(By.cssSelector("div.player-info > div.player-cards > div.card > span.suit")));
	    assertFalse(isElementPresent(By.id("hit-button")));
	    assertFalse(isElementPresent(By.id("stay-button")));
	    
	    // switch to p1
	    driver.get(player1);
	    
	    assertEquals("It is your turn", driver.findElement(By.xpath("//p[2]/span")).getText());
	    assertTrue(isElementPresent(By.id("hit-button")));
	    assertTrue(isElementPresent(By.id("stay-button")));
	    assertTrue(isElementPresent(By.cssSelector("div.player-info")));
	    assertTrue(isElementPresent(By.cssSelector("div.player-info > div.player-cards > div.card > span.rank")));
	    assertTrue(isElementPresent(By.cssSelector("div.player-info > div.player-cards > div.card > span.suit")));
	    assertEquals("2", driver.findElement(By.id("numPlayers")).getText());
	    assertFalse(isElementPresent(By.xpath("//div[@class='player-info']/div[2]/div")));
	    
	    driver.findElement(By.id("stay-button")).click();
	    
	    assertEquals("Waiting for another player to go", driver.findElement(By.xpath("//p[2]/span")).getText());
	    assertFalse(isElementPresent(By.id("hit-button")));
	    assertFalse(isElementPresent(By.id("stay-button")));
	    
	    // switch to player 2
	    driver.get(player2);
	    
	    assertEquals("It is your turn", driver.findElement(By.xpath("//p[2]/span")).getText());
	    assertTrue(isElementPresent(By.id("hit-button")));
	    assertTrue(isElementPresent(By.id("stay-button")));
	    
	    driver.findElement(By.id("stay-button")).click();
	    
	    assertTrue(isElementPresent(By.id("new-game-button")));
	    assertTrue(isElementPresent(By.xpath("//div[@id='player-section']/p/span[2]")));
	    assertTrue(isElementPresent(By.xpath("//div/div/p/span[2]")));
	    
	    // switch to player 1
	    driver.get(player1);
	    
	    assertTrue(isElementPresent(By.id("new-game-button")));
	    assertTrue(isElementPresent(By.xpath("//div[@id='player-section']/p/span[2]")));
	    assertTrue(isElementPresent(By.xpath("//div/div/p/span[2]")));

	}
	
	/**
	 * Tear down the Firefox driver, fail the test if any errors occur
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	@AfterClass
	public static void afterClass() {
		driver.quit();
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
