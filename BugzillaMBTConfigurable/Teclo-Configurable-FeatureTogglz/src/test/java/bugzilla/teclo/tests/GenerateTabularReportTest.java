package bugzilla.teclo.tests;

import bugzilla.teclo.AbstractBugzillaTestWithLogin;
import bugzilla.teclo.BugzillaSetup;
import bugzilla.teclo.pageobjects.ReportsBasePage;
import bugzilla.teclo.pageobjects.TabularReportGeneratePage;
import bugzilla.teclo.pageobjects.TabularReportResultsPage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenerateTabularReportTest extends AbstractBugzillaTestWithLogin {
	@Before
	public void setUp() throws Exception {
		// precondition: bug inserted
		BugzillaSetup.getExampleBugId();
	}

	@Test
	public void testGenerateTabularReport() throws Exception {
		ReportsBasePage reportsBasePage = startPage.gotoReportsBasePage();
		TabularReportGeneratePage tabularReportGeneratePage = reportsBasePage.gotoGenerateTabularReportPage();
		tabularReportGeneratePage.setHorizontalAxis("Status");
		tabularReportGeneratePage.setVeritcalAxis("Assignee");

		TabularReportResultsPage tabularReportResultsPage = tabularReportGeneratePage.generateReport();

		assertEquals("Assignee", tabularReportResultsPage.getVerticalAxisLabel());
		assertEquals("Status", tabularReportResultsPage.getHorizontalAxisLabel());
	}
}
