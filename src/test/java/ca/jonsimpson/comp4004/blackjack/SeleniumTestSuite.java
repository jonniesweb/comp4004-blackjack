package ca.jonsimpson.comp4004.blackjack;

import java.io.File;
import java.io.IOException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ca.jonsimpson.comp4004.blackjack.selenium.GameAlreadyInProgressTest;
import ca.jonsimpson.comp4004.blackjack.selenium.MultiplePlayerGameTest;
import ca.jonsimpson.comp4004.blackjack.selenium.PlayerJoinPageTest;
import ca.jonsimpson.comp4004.blackjack.selenium.SinglePlayerGameTest;

import com.google.common.io.Files;

/**
 * A test suite for the Selenium tests. This class generates a test result file
 * named ResultBJ.txt
 */
@RunWith(Suite.class)
@SuiteClasses({ GameAlreadyInProgressTest.class, MultiplePlayerGameTest.class,
		PlayerJoinPageTest.class, SinglePlayerGameTest.class })
public class SeleniumTestSuite {
	
	private static final Class<?>[] CLASSES = { GameAlreadyInProgressTest.class,
			MultiplePlayerGameTest.class, PlayerJoinPageTest.class, SinglePlayerGameTest.class };
	
	public static void main(String[] args) throws IOException {
		Result runClasses = JUnitCore.runClasses(CLASSES);
		
		StringBuilder builder = new StringBuilder();
		
		if (runClasses.getFailureCount() > 0) {
			
			for (Failure failure : runClasses.getFailures()) {
				builder.append(failure);
				builder.append("\n\n");
			}
			
			builder.append("Some tests failed");
		} else {
			builder.append("All tests passed");
		}
		
		File file = new File("ResultBJ.txt");
		Files.write(builder.toString().getBytes(), file);
	}
}
