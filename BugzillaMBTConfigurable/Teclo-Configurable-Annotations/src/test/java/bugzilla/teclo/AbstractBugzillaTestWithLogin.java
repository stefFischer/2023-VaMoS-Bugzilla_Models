package bugzilla.teclo;

import bugzilla.teclo.pageobjects.StartPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractBugzillaTestWithLogin extends AbstractBugzillaTest {

	protected static StartPage startPage;

	// we have to be very careful with naming!
	// if a subclass has the same static @BeforeClass method (which is possible
	// as static methods cannot be overridden), this method will be ignored!
	@BeforeClass
	public static void staticLogin() {
		startPage = BugzillaSetup.login();
	}

	// we have to be very careful with naming!
	// if a subclass has the same static @AfterClass method (which is possible
	// as static methods cannot be overridden), this method will be ignored!
	@AfterClass
	public static void staticLogout() {
		 BugzillaSetup.logout();
	}
}
