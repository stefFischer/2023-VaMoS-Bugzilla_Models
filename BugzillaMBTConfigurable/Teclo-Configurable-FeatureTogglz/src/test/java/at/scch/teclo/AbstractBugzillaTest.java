package at.scch.teclo;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractBugzillaTest {
	// we have to be very careful with naming!
	// if a subclass has the same static @BeforeClass method (which is possible
	// as static methods cannot be overridden), this method will be ignored!
	@BeforeClass
	public static void staticOpenDriver() {
		BugzillaSetup.openWebDriver();

		// Make sure that test setup has been performed
		BugzillaSetup.gotoStartPage();
	}

	// we have to be very careful with naming!
	// if a subclass has the same static @AfterClass method (which is possible
	// as static methods cannot be overridden), this method will be ignored!
	@AfterClass
	public static void staticCloseDriver() {
		BugzillaSetup.closeWebDriver();
	}
}
