package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import driverfactory.DriverFactory;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class TestSetup {

	private static WebDriver driver;
	private static Properties props;


	@Before
	public void setUp() {
		driver = new DriverFactory().create();
		loadProperties();
		driver.navigate().to(readProperty("URL"));
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
	}

	private void loadProperties() {
		props = new Properties();
		try {
			InputStream inputStream = new FileInputStream("src/test/resources/config.properties");
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String readProperty(String key) {
		return props.getProperty(key);
	}


	@After
	public void tearDown() {

		if (driver != null)
			driver.quit();
	}

	public static WebDriver getDriverInstance() {
		return driver;
	}
}
