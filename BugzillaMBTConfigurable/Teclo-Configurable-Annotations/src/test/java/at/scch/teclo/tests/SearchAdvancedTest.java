package at.scch.teclo.tests;

import at.scch.teclo.AbstractBugzillaTestWithLogin;
import at.scch.teclo.BugzillaSetup;
import at.scch.teclo.pageobjects.EditBugPage;
import at.scch.teclo.pageobjects.SearchAdvancedPage;
import at.scch.teclo.pageobjects.SearchBasePage;
import at.scch.teclo.pageobjects.SearchResultsPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchAdvancedTest extends AbstractBugzillaTestWithLogin {
	private int currentBugId;
	private String currentBugSummary;
	private SearchResultsPage searchResultsPage;

	private String currentBugStatus;
	private String currentBugPriority;

	/*#if ($UseStatusWhiteboard)*/
	private String currentWhiteboardStatus;
	private String whiteboardSearchTestMessage;
	/*#end*/

	@Before
	public void setUp() throws Exception {
		// precondition: bug inserted
		currentBugId = BugzillaSetup.getExampleBugId();
		currentBugSummary = BugzillaSetup.getExampleBugSummary();

		// precondition: bug changed
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		currentBugStatus = editBugPage.getBugStatus();

		/*#if ($SimpleBugWorkflow)*/
		editBugPage.setBugStatus("RESOLVED");
		/*#else*/
		editBugPage.setBugStatus("IN_PROGRESS");
		/*#end*/

		currentBugPriority = editBugPage.getPriority();
		editBugPage.setPriority("Normal");

		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set priority");
		/*#end*/

		/*#if ($UseStatusWhiteboard)*/
		currentWhiteboardStatus = editBugPage.getStatusWhiteboard();
		whiteboardSearchTestMessage = "status whiteboard message set at " + System.currentTimeMillis();
		editBugPage.setStatusWhiteboard(whiteboardSearchTestMessage);
		/*#end*/

		editBugPage.commitBug();
	}

	@Test
	public void testFindBugZarro() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchAdvancedPage searchAdvancedPage = searchBasePage.gotoAdvancedSearchPage();

		searchAdvancedPage.setBugStatus("RESOLVED");
		searchAdvancedPage.setSummary(currentBugSummary.replace("_", "-"));
		searchResultsPage = searchAdvancedPage.submitSearch();

		assertEquals("More than 0 bugs found!", 0, searchResultsPage.getAmountOfBugs());
	}

	@Test
	public void testFindBugSingle() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchAdvancedPage searchAdvancedPage = searchBasePage.gotoAdvancedSearchPage();

		/*#if ($SimpleBugWorkflow)*/
		searchAdvancedPage.setBugStatus("RESOLVED");
		searchAdvancedPage.setResolution("FIXED");
		/*#else*/
		searchAdvancedPage.setBugStatus("IN_PROGRESS");
		/*#end*/

		searchAdvancedPage.setSummary(currentBugSummary);
		searchResultsPage = searchAdvancedPage.submitSearch();

		assertEquals("Not exactly one bug found!", 1, searchResultsPage.getAmountOfBugs());

		/*#if ($SimpleBugWorkflow)*/
		assertEquals("RESO", searchResultsPage.getStatusOfFirstBug());
		/*#else*/
		assertEquals("IN_P", searchResultsPage.getStatusOfFirstBug());
		/*#end*/

		assertEquals(currentBugSummary, searchResultsPage.getSummaryOfFirstBug());
	}

	@Test
	public void testFindBugAll() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchAdvancedPage searchAdvancedPage = searchBasePage.gotoAdvancedSearchPage();

		searchAdvancedPage.setSummarySearchType("matches regular expression");
		searchAdvancedPage.setSummary(".*");

		searchResultsPage = searchAdvancedPage.submitSearch();

		assertTrue("No bugs found", searchResultsPage.getAmountOfBugs() >= 1);
	}

	@Test
	public void testBooleanChart() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchAdvancedPage searchAdvancedPage = searchBasePage.gotoAdvancedSearchPage();

		/*#if ($SimpleBugWorkflow)*/
		searchAdvancedPage.unsetBugStatus("CONFIRMED");
		searchAdvancedPage.unsetBugStatus("IN_PROGRESS");
		searchAdvancedPage.setBugStatus("RESOLVED");
		searchAdvancedPage.setResolution("FIXED");
		/*#end*/

		searchAdvancedPage.setBooleanChart("Priority", "is equal to", "Normal");
		searchAdvancedPage.setSummary(currentBugSummary);
		SearchResultsPage searchResultsPage = searchAdvancedPage.submitSearch();

		assertEquals("No bug found!", 1, searchResultsPage.getAmountOfBugs());
		assertEquals("Normal", searchResultsPage.getPriorityOfFirstBug());
		assertEquals(currentBugSummary, searchResultsPage.getSummaryOfFirstBug());
	}

	/*#if ($UseStatusWhiteboard)*/
	@Test
	public void testSearchStatusWhiteboard(){
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchAdvancedPage searchAdvancedPage = searchBasePage.gotoAdvancedSearchPage();

		searchAdvancedPage.setStatusWhiteboard(whiteboardSearchTestMessage);
		/*#if ($SimpleBugWorkflow)*/
		searchAdvancedPage.setResolution("FIXED");
		/*#end*/
		SearchResultsPage searchResultsPage = searchAdvancedPage.submitSearch();

		assertEquals(1, searchResultsPage.getAmountOfBugs());
		assertEquals(currentBugId, searchResultsPage.getIdOfFirstBug());

		EditBugPage editBugPage = searchResultsPage.gotoEditBugPage(currentBugId);
		assertEquals(whiteboardSearchTestMessage, editBugPage.getStatusWhiteboard());
	}
	/*#end*/

	/*#if ($AddComponent)*/
	@Test
	public void testFindBugInNewComponentReturnsZarro() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchAdvancedPage searchAdvancedPage = searchBasePage.gotoAdvancedSearchPage();

		searchAdvancedPage.setComponent("AddedComponent");
		SearchResultsPage searchResultsPage = searchAdvancedPage.submitSearch();

		assertEquals("More than 0 bugs found!", 0, searchResultsPage.getAmountOfBugs());
	}
	/*#end*/

	/*#if ($AddVersion)*/
	@Test
	public void testFindBugInNewVersionReturnsZarro() throws Exception {
		SearchBasePage searchBasePage = startPage.gotoSearchBasePage();
		SearchAdvancedPage searchAdvancedPage = searchBasePage.gotoAdvancedSearchPage();

		searchAdvancedPage.setVersion("AddedVersion");
		SearchResultsPage searchResultsPage = searchAdvancedPage.submitSearch();

		assertEquals("More than 0 bugs found!", 0, searchResultsPage.getAmountOfBugs());
	}
	/*#end*/

	@After
	public void tearDown() throws Exception {
		// postcondition: change bug back
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		editBugPage.setBugStatus(currentBugStatus);
		editBugPage.setPriority(currentBugPriority);

		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("priority reset");
		/*#end*/

		/*#if ($UseStatusWhiteboard)*/
		editBugPage.setStatusWhiteboard(currentWhiteboardStatus);
		/*#end*/

		editBugPage.commitBug();
	}

}
