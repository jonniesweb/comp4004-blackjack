package ca.jonsimpson.comp4004.blackjack.selenium;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class AbstractSeleniumTest {
	
	protected static WebDriver driver;
	protected static String baseUrl;
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
	protected void reset() {
		driver.get(baseUrl + "admin/reset");
	}
	
	protected String getButtonText(WebElement refreshButton) {
		return refreshButton.getAttribute("value");
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
	
	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	protected boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}
	
	protected String closeAlertAndGetItsText() {
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
