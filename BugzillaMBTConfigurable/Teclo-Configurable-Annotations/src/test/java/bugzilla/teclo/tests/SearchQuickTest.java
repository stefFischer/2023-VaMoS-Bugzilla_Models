package bugzilla.teclo.tests;

import bugzilla.teclo.AbstractBugzillaTestWithLogin;
import bugzilla.teclo.BugzillaSetup;
import bugzilla.teclo.pageobjects.EditBugPage;
import bugzilla.teclo.pageobjects.SearchResultsPage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchQuickTest extends AbstractBugzillaTestWithLogin {
	private int currentBugId;
	private String currentBugSummary;

	@Before
	public void setUp() throws Exception {
		BugzillaSetup.createExampleBug();
		currentBugId = BugzillaSetup.getExampleBugId();
		currentBugSummary = BugzillaSetup.getExampleBugSummary();
		startPage = BugzillaSetup.gotoStartPage();
	}

	@Test
	public void testFindBugZarro() throws Exception {
		SearchResultsPage searchResultsPage = startPage.searchFor(currentBugSummary.replace("_", "-"));
		assertEquals("More than 0 bugs found!", 0, searchResultsPage.getAmountOfBugs());
	}

	@Test
	public void testFindBugSingleByName() throws Exception {
		SearchResultsPage searchResultsPage = startPage.searchFor(currentBugSummary);

		assertEquals("No bug found!", 1, searchResultsPage.getAmountOfBugs());
		assertEquals(currentBugSummary, searchResultsPage.getSummaryOfFirstBug());
	}

	@Test
	public void testFindBugSingleById() throws Exception {
		EditBugPage editBugPage = startPage.searchFor(currentBugId);
		assertEquals("Bug not found by ID", currentBugSummary, editBugPage.getSummary());
	}

	@Test
	public void testFindBugMultiple() throws Exception {
		// add one more bug to make sure there are at least 2 or more bugs in the DB
		BugzillaSetup.createExampleBug();
		startPage = BugzillaSetup.gotoStartPage();

		SearchResultsPage searchResultsPage = startPage.searchFor("Bug");
		assertTrue("No multiple bugs found", 1 < searchResultsPage.getAmountOfBugs());
	}

	@Test
	public void testSaveSearch() throws Exception {
		SearchResultsPage searchResultsPage = startPage.searchFor(currentBugSummary);

		int numberFoundBugs = searchResultsPage.getAmountOfBugs();
		String firstBugSummary = searchResultsPage.getSummaryOfFirstBug();

		searchResultsPage = searchResultsPage.rememberSavedSearch("SearchFor_" + currentBugSummary);
		searchResultsPage.gotoStartPage();
		searchResultsPage = startPage.performSavedSearch("SearchFor_" + currentBugSummary);

		assertEquals(numberFoundBugs, searchResultsPage.getAmountOfBugs());
		assertEquals(firstBugSummary, searchResultsPage.getSummaryOfFirstBug());

		// forget saved search
		searchResultsPage.forgetSavedSearch("SearchFor_" + currentBugSummary);
	}

}
