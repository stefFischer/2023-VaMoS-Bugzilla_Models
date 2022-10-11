package bugzilla.teclo.tests;

import bugzilla.teclo.AbstractBugzillaTest;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LoginLogoutTest.class, CreateNewBugTest.class, EditBugTest.class, ChangeBugStatusTest.class,
		SearchQuickTest.class, SearchSpecificTest.class, SearchAdvancedTest.class, GenerateTabularReportTest.class })
public class AllTests extends AbstractBugzillaTest {
	
	public static void main(String[] args) {
		JUnitCore junitCore = new JUnitCore();
		junitCore.addListener(new TextListener(System.out));
		junitCore.run(AllTests.class);
	}
}
