package bugzilla.teclo.tests;

import bugzilla.teclo.AbstractBugzillaTestWithLogin;
import bugzilla.teclo.BugzillaSetup;
import bugzilla.teclo.pageobjects.SearchBasePage;
import bugzilla.teclo.pageobjects.SearchResultsPage;
import bugzilla.teclo.pageobjects.SearchSpecificPage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchSpecificTest extends AbstractBugzillaTestWithLogin {

	private String currentBugSummary;

	@Before
	public void setUp() throws Exception {
		// precondition: bug inserted
		currentBugSummary = BugzillaSetup.getExampleBugSummary();
	}

	@Test
	public void testFindBugZarro() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchSpecificPage searchSpecificPage = searchBasePage.gotoSpecificSearchPage();

		searchSpecificPage.setBugStatus("Closed");
		SearchResultsPage searchResultsPage = searchSpecificPage.searchFor(currentBugSummary.replace("_", "-"));

		assertEquals("More than 0 bugs found!", 0, searchResultsPage.getAmountOfBugs());
	}

	@Test
	public void testFindBugSingle() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchSpecificPage searchSpecificPage = searchBasePage.gotoSpecificSearchPage();

		searchSpecificPage.setBugStatus("All");

		/*#if ($AddProduct)*/
		searchSpecificPage.setProduct("TestProduct");
		/*#end*/

		SearchResultsPage searchResultsPage = searchSpecificPage.searchFor(currentBugSummary);

		assertEquals("Not exactly one bug found!", 1, searchResultsPage.getAmountOfBugs());
		assertEquals(BugzillaSetup.getExampleBugSummary(), searchResultsPage.getSummaryOfFirstBug());
	}

	@Test
	public void testFindBugMultiple() throws Exception {
		BugzillaSetup.createExampleBug();

		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchSpecificPage searchSpecificPage = searchBasePage.gotoSpecificSearchPage();

		searchSpecificPage.setBugStatus("All");

		/*#if ($AddProduct)*/
		searchSpecificPage.setProduct("All");
		/*#end*/

		SearchResultsPage searchResultsPage = searchSpecificPage.searchFor("Bug*");

		assertTrue("No multiple bugs found", 1 < searchResultsPage.getAmountOfBugs());
	}

	@Test
	public void testSearchEmptyResultsInMatchingAll() {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchSpecificPage searchSpecificPage = searchBasePage.gotoSpecificSearchPage();

		SearchResultsPage searchResultsPage = searchSpecificPage.searchForEmpty();

		assertTrue("No multiple bugs found", searchResultsPage.getAmountOfBugs() >= 1);
	}

	/*#if ($AddProduct)*/
	@Test
	public void testFindBugInNewProductReturnsZarro() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchSpecificPage searchSpecificPage = searchBasePage.gotoSpecificSearchPage();

		searchSpecificPage.setBugStatus("All");
		searchSpecificPage.setProduct("FooProduct");
		SearchResultsPage searchResultsPage = searchSpecificPage.searchFor(currentBugSummary);

		assertEquals("More than 0 bugs found!", 0, searchResultsPage.getAmountOfBugs());
	}
	/*#end*/
}
