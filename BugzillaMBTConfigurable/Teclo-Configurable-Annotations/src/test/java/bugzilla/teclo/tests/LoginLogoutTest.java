package bugzilla.teclo.tests;

import bugzilla.teclo.AbstractBugzillaTest;
import bugzilla.teclo.BugzillaSetup;
import bugzilla.teclo.pageobjects.AbstractBugzillaPage;
import bugzilla.teclo.pageobjects.ErrorLoginPage;
import bugzilla.teclo.pageobjects.LoginPage;
import bugzilla.teclo.pageobjects.StartPage;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginLogoutTest extends AbstractBugzillaTest {

	@Test
	public void testLoginLogoutTopOfPage() {
		StartPage startPage = BugzillaSetup.gotoStartPage();
		assertFalse(startPage.isLoggedIn());
		
		assertTrue(startPage.login(BugzillaSetup.getUsername(), BugzillaSetup.getPassword()));
		
		assertTrue(startPage.isLoggedIn());
		assertEquals(BugzillaSetup.getUsername(), startPage.getLoggedInUser());
		
		startPage = startPage.logout();
		
		assertFalse(startPage.isLoggedIn());
	}
	
	@Test
	public void testLoginSuccessful() {
		StartPage startPage = BugzillaSetup.gotoStartPage();
		assertFalse(startPage.isLoggedIn());
		
		LoginPage loginPage = startPage.gotoLoginPage();
		AbstractBugzillaPage createBugPage = loginPage.loginSuccessful(BugzillaSetup.getUsername(), BugzillaSetup.getPassword());
		
		assertTrue(createBugPage.isLoggedIn());
		assertEquals(BugzillaSetup.getUsername(), createBugPage.getLoggedInUser());
		
		startPage = createBugPage.logout();
		
		assertFalse(startPage.isLoggedIn());
	}
	
	@Test
	public void testLoginFailing() {
		StartPage startPage = BugzillaSetup.gotoStartPage();
		assertFalse(startPage.isLoggedIn());
		
		LoginPage loginPage = startPage.gotoLoginPage();
		ErrorLoginPage errorLoginPage = loginPage.loginFailing("wrongUsername", "wrongPassword");
		
		assertEquals("The login or password you entered is not valid.", errorLoginPage.getErrorMsg());
		assertFalse(errorLoginPage.isLoggedIn());
	}
	
}
