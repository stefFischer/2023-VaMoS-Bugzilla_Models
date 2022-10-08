package at.scch.teclo;

import at.scch.teclo.pageobjects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/*
 * The reading and the saving of all information in config.properties has to be in its own class.
 * Furthermore all methods that execute something on SUT do not belong in this class but also in its own.
 * WebDriver responsibility should also be in a different class.
 * */

public class BugzillaSetup {
	private static final Logger Logger = LoggerFactory.getLogger(BugzillaSetup.class);

	public static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

	private static String username;
	private static String password;

	private static String baseUrl = "";
	public static WebDriver driver;
	private static int driverUsageCounter;

	private static StartPage startPage;
	private static int currentbugId;
	private static String exampleBugSummary;

	private static String testConfigName = "";

	public static long TIMEOUT_SECONDS = 5;

	private static boolean runHeadless = false;

	static {

		String configFile = "config.properties";
		loadPropertyFile(configFile);

		// set the variable BASE_URL received from the props file
		baseUrl = System.getProperty("BASE_URL");
		if (!baseUrl.endsWith("/")) {
			baseUrl = baseUrl + "/";
		}

		// set test config name
		testConfigName = System.getProperty("TEST_CONFIG");

		username = System.getProperty("USERNAME");
		password = System.getProperty("PASSWORD");

		runHeadless = System.getProperty("HEADLESS").equalsIgnoreCase("true");

		TIMEOUT_SECONDS = Long.parseLong(System.getProperty("TIMEOUT_SECONDS"));

		String webDriver = System.getProperty(CHROME_DRIVER_PROPERTY);
		if (webDriver == null) {
			String os = System.getProperty("os.name");

			String currPath = new File(System.getProperty("user.dir")).getAbsolutePath();

			File chromedriver = new File(currPath + "/chromedriver");
			while(!chromedriver.exists()){
				currPath += "/..";
				chromedriver = new File(currPath + "/chromedriver");
			}

			try {
				currPath = chromedriver.getCanonicalPath();
				currPath = currPath.replaceAll("\\\\", "/");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (os.toLowerCase().contains("win")) {
				System.setProperty(CHROME_DRIVER_PROPERTY, currPath + "/windows/chromedriver_v2.35.exe");
			} else {
				// assuming OS is UNIX based (OSX, Linux, etc.) or at least is
				// able to execute shell scripts
				System.setProperty(CHROME_DRIVER_PROPERTY, currPath + "/unix/chromedriver_v2.35");
			}
		}

	}

	private BugzillaSetup() {
		/* empty */
	}

	public static void loadPropertyFile(String filename) {

		File configFile = new File(filename);
		if(!configFile.exists()){
			configFile = new File("../" + filename);
		}

		try (InputStream input = new FileInputStream(configFile)) {
//		try (InputStream input = BugzillaSetup.class.getClassLoader().getResourceAsStream(filename)) {
			if (input == null) {
				Logger.info("Unable to find {}.", filename);
				return;
			}

			// load a properties file from class path, inside static method
			Properties prop = new Properties();
			prop.load(input);
			prop.putAll(System.getProperties());

			System.setProperties(prop);
		} catch (IOException ex) {
			System.err.println(configFile.getAbsolutePath());
			Logger.error("Could not load config file.", ex);
		}
	}

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static WebDriver openWebDriver() {
		driverUsageCounter++;
		if (driver == null) {
			setUpNewDriver();
		}
		return driver;
	}

	private static void setUpNewDriver() {
		ChromeOptions options = new ChromeOptions();
		options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
		if(runHeadless){
			options.addArguments("headless");
		}
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(TIMEOUT_SECONDS, TimeUnit.SECONDS);
	}

	public static void closeWebDriver() {
		driverUsageCounter--;

		if (driverUsageCounter > 0) {
			return;
		}

		if (driver != null) {
			driver.close();
			driver.quit();
			driver = null;
		}
	}

	public static StartPage gotoStartPage() {
		checkDriver();

		driver.get(BugzillaSetup.getBaseUrl());
		return PageFactory.initElements(driver, StartPage.class);
	}

	public static EditBugPage gotoEditBugPage(int bugId) {
		checkDriver();
		driver.get(baseUrl + "show_bug.cgi?id=" + bugId);
		return PageFactory.initElements(driver, EditBugPage.class);
	}

	public static ConfigGeneralSettingsPage gotoConfigGeneralSettingsPage() {
		checkDriver();
		checkLogin();
		driver.get(baseUrl + "/editparams.cgi?section=general");
		return PageFactory.initElements(driver, ConfigGeneralSettingsPage.class);
	}

	public static StartPage login() {
		startPage = gotoStartPage();
		if (!startPage.isLoggedIn()) {
			startPage.login(getUsername(), getPassword());
		}
		return startPage;
	}

	public static StartPage logout() {
		startPage = gotoStartPage();
		return startPage.logout();
	}

	private static void checkLogin() {
		if (startPage == null) {
			throw new IllegalStateException("User not logged in!");
		}
	}

	private static void checkDriver() {
		if (driver == null) {
			throw new IllegalStateException("Driver not initialized!");
		}
	}

	public static String getTestConfigName() {
		return testConfigName;
	}

	public static boolean isTestSetup() {
		WebElement testConfigMessage = null;
		checkDriver();
		if (Helper.isElementPresent(driver, By.id("test_config"))) {
			testConfigMessage = driver.findElement(By.id("test_config"));
		}

		return testConfigMessage != null && testConfigMessage.getText().contains(getTestConfigName());
	}

	public static void initialize() {
		openWebDriver();
	}

	public static void close() {
		closeWebDriver();
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static int createExampleBug() {
		checkDriver();
		checkLogin();

		// set the bug summary
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
		exampleBugSummary = "Bug_" + LocalDateTime.now().format(formatter);

		// create bug
		CreateBugPage createBugPage;
		/*#if ($AddProduct)*/
		CreateBugSelectProductPage createBugSelectProductPage = (CreateBugSelectProductPage)startPage.gotoCreateBugPage();
		createBugPage = createBugSelectProductPage.setProduct("TestProduct");
		/*#else*/
		createBugPage = (CreateBugPage)startPage.gotoCreateBugPage();
		/*#end*/

		EditBugPage bugCreatedPage = createBugPage.createNewBug(exampleBugSummary, "test data");

		// save the id of the bug
		currentbugId = bugCreatedPage.getBugId();
		Logger.info("Created new example bug with summary {} and ID {}.", exampleBugSummary, currentbugId);

		return currentbugId;
	}

	public static int getExampleBugId() {
		// if there is no bug created yet do it now
		if (currentbugId == 0) {
			createExampleBug();
		}
		return currentbugId;
	}

	public static String getExampleBugSummary() {
		// if there is no bug created yet do it now
		if (currentbugId == 0) {
			createExampleBug();
		}
		return exampleBugSummary;
	}
}
