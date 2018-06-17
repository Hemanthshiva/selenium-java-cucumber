package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */

/**
 * This class returns a instance of ChromeDriver based on the OS the test is run on
 */
public class DriverFactory {

	private static String OS = System.getProperty("os.name").toLowerCase();
	private String pathToDriver;

	public WebDriver create() {
		return driverInstance();
	}

	private WebDriver driverInstance() {
		if (OS.contains("win")) {
			pathToDriver = System.getProperty("user.dir") + "/src/test/resources/drivers/windows/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", pathToDriver);
		} else if (OS.contains("mac")) {
			pathToDriver = System.getProperty("user.dir") + "/src/test/resources/drivers/mac/chromedriver";
			System.setProperty("webdriver.chrome.driver", pathToDriver);
		}
		return new ChromeDriver();
	}


}
