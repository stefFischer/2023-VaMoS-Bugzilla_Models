package bugzilla.teclo.tests;

import bugzilla.teclo.AbstractBugzillaTestWithLogin;
import bugzilla.teclo.BugzillaSetup;
import bugzilla.teclo.pageobjects.CreateBugAdvancedPage;
import bugzilla.teclo.pageobjects.CreateBugPage;
import bugzilla.teclo.pageobjects.CreateBugSelectProductPage;
import bugzilla.teclo.pageobjects.EditBugPage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CreateNewBugTest extends AbstractBugzillaTestWithLogin {
	private static final Logger Logger = LoggerFactory.getLogger(CreateNewBugTest.class);
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

	@Test
	public void testCreateBugDefaultValues() throws Exception {
		String nowText = LocalDateTime.now().format(formatter);
		String summary = "ExampleBugStandard_" + nowText;
		/*#if ($CommentOnBugCreation || $CommentOnAllTransitions)*/
		String comment = "This is an example description for ExampleBugStandard created at " + nowText;
		/*#end*/

		// create bug
		CreateBugPage createBugPage;
		/*#if ($AddProduct)*/
		CreateBugSelectProductPage createBugSelectProductPage = (CreateBugSelectProductPage)startPage.gotoCreateBugPage();
		createBugPage = createBugSelectProductPage.setProduct("TestProduct");
		/*#else*/
		createBugPage = (CreateBugPage)startPage.gotoCreateBugPage();
		/*#end*/

		/*#if ($AddComponent)*/
		createBugPage.selectComponent("TestComponent");
		/*#end*/

		/*#if ($AddVersion)*/
		createBugPage.selectVersion("unspecified");
		/*#end*/

		createBugPage.setSummary(summary);

		/*#if ($CommentOnBugCreation || $CommentOnAllTransitions)*/
		createBugPage.setComment(comment);
		/*#end*/

		EditBugPage bugCreatedPage = createBugPage.commitBug();

		// Check if creating bug was successful
		int newBugId = bugCreatedPage.getBugId();

		Logger.info("Created new default bug with summary " + summary + " and ID " + newBugId);

		// verify changes including default values
		// don't verify default values for operating system and platform, which
		// are client values retrieved from browser by default

		EditBugPage editBugPage = bugCreatedPage;

		assertEquals(summary, editBugPage.getSummary());

		assertEquals("TestProduct", editBugPage.getProduct());
		assertEquals("TestComponent", editBugPage.getComponent());
		assertEquals("unspecified", editBugPage.getVersion());

		assertEquals("---", editBugPage.getPriority());
		assertEquals("enhancement", editBugPage.getSeverity());
		assertEquals("", editBugPage.getUrl());

		/*#if ($UseStatusWhiteboard)*/
		assertEquals("", editBugPage.getStatusWhiteboard());
		/*#end*/

		assertEquals("", editBugPage.getDependsOn());
		assertEquals("", editBugPage.getBlocks());

		assertEquals("0.0", editBugPage.getTimeEstimated());
		assertEquals("0.0", editBugPage.getTimeCurrentEstimation());
		assertEquals("0.0 +", editBugPage.getTimeWorkCompleted());
		assertEquals("0", editBugPage.getTimeWorked());
		assertEquals("0.0", editBugPage.getTimeHoursLeft());
		assertEquals("0", editBugPage.getTimeCompletedInPercent());
		assertEquals("0.0", editBugPage.getTimeGain());
		assertEquals("", editBugPage.getTimeDeadline());

		assertEquals("CONFIRMED", editBugPage.getBugStatus());

		assertEquals("", editBugPage.getComment());

		/*#if ($CommentOnBugCreation || $CommentOnAllTransitions)*/
		assertEquals(comment, editBugPage.getFirstComment());
		assertEquals(comment, editBugPage.getLastComment());
		/*#else*/
		assertEquals("", editBugPage.getFirstComment());
		assertEquals("", editBugPage.getLastComment());
		/*#end*/

		assertEquals(1, editBugPage.getNumberOfComments());
	}

	@Test
	public void testCreateEmptySummaryResultsInAlertPopup() throws Exception {
		// create bug
		CreateBugPage createBugPage;
		/*#if ($AddProduct)*/
		CreateBugSelectProductPage createBugSelectProductPage = (CreateBugSelectProductPage)startPage.gotoCreateBugPage();
		createBugPage = createBugSelectProductPage.setProduct("TestProduct");
		/*#else*/
		createBugPage = (CreateBugPage)startPage.gotoCreateBugPage();
		/*#end*/

		/*#if ($AddComponent)*/
		createBugPage.selectComponent("TestComponent");
		/*#end*/

		/*#if ($AddVersion)*/
		createBugPage.selectVersion("unspecified");
		/*#end*/

		createBugPage.setSummary("");

		assertFalse(createBugPage.commitBugWithEmptySummary());
		String alertMsg = createBugPage.getAlertText();
		assertEquals("You must enter a Summary for this bug.", alertMsg);
	}

	@Test
	public void testCreateBugStandardFields() throws Exception {
		String nowText = LocalDateTime.now().format(formatter);
		String summary = "ExampleBugStandard_" + nowText;
		String comment = "This is an example description for ExampleBugStandard created at " + nowText;

		// create bug
		CreateBugPage createBugPage;
		/*#if ($AddProduct)*/
		CreateBugSelectProductPage createBugSelectProductPage = (CreateBugSelectProductPage)startPage.gotoCreateBugPage();
		createBugPage = createBugSelectProductPage.setProduct("TestProduct");
		/*#else*/
		createBugPage = (CreateBugPage)startPage.gotoCreateBugPage();
		/*#end*/

		/*#if ($AddComponent)*/
		createBugPage.selectComponent("TestComponent");
		/*#end*/

		/*#if ($AddVersion)*/
		createBugPage.selectVersion("unspecified");
		/*#end*/

		createBugPage.setSummary(summary);
		createBugPage.setComment(comment);

		createBugPage.setPlatform("Other");
		createBugPage.setOpSys("Linux");
		createBugPage.setSeverity("major");

		EditBugPage bugCreatedPage = createBugPage.commitBug();

		// Check if creating bug was successful
		int newBugId = bugCreatedPage.getBugId();

		Logger.info("Created new standard bug with summary " + summary + " and ID " + newBugId);

		// verify values of set fields

		EditBugPage editBugPage = bugCreatedPage;

		assertEquals(summary, editBugPage.getSummary());
		assertEquals("Other", editBugPage.getPlatform());
		assertEquals("Linux", editBugPage.getOpSys());
		assertEquals("major", editBugPage.getSeverity());
	}

	@Test
	public void testCreateBugAdvancedFields() {
		String nowText = LocalDateTime.now().format(formatter);
		String summary = "ExampleBugAdvanced" + nowText;
		String comment = "This is an example description for ExampleBugAdvanced created at " + nowText;

		// create bug
		CreateBugPage createBugPage;
		/*#if ($AddProduct)*/
		CreateBugSelectProductPage createBugSelectProductPage = (CreateBugSelectProductPage)startPage.gotoCreateBugPage();
		createBugPage = createBugSelectProductPage.setProduct("TestProduct");
		/*#else*/
		createBugPage = (CreateBugPage)startPage.gotoCreateBugPage();
		/*#end*/

		/*#if ($AddComponent)*/
		createBugPage.selectComponent("TestComponent");
		/*#end*/

		/*#if ($AddVersion)*/
		createBugPage.selectVersion("unspecified");
		/*#end*/

		CreateBugAdvancedPage createBugAdvancedPage = createBugPage.gotoAdvanced();

		createBugAdvancedPage.setSeverity("blocker");
		createBugAdvancedPage.setPlatform("All");
		createBugAdvancedPage.setOpSys("All");

		/*#if (!$Letsubmitterchoosepriority)*/
		createBugAdvancedPage.setPriority("Highest");
		/*#end*/

		/*#if (!$SimpleBugWorkflow && !$UnconfirmedState)*/
		createBugAdvancedPage.setBugStatus("IN_PROGRESS");
		/*#end*/

		createBugAdvancedPage.setCc(BugzillaSetup.getUsername());

		createBugAdvancedPage.setTimeEstimated(100);
		createBugAdvancedPage.setTimeDeadline("2016-06-12");
		createBugAdvancedPage.setUrl("http://www.test-bugzilla.org");

		createBugAdvancedPage.setSummary(summary);
		createBugAdvancedPage.setComment(comment);

		EditBugPage bugCreatedPage = createBugAdvancedPage.commitBug();

		// Check if creating bug was successful
		int newBugId = bugCreatedPage.getBugId();

		Logger.info("Created new default bug with summary " + summary + " and ID " + newBugId);

		// verify values including default values

		EditBugPage editBugPage = bugCreatedPage;

		assertEquals(summary, editBugPage.getSummary());

		assertEquals("TestProduct", editBugPage.getProduct());
		assertEquals("TestComponent", editBugPage.getComponent());
		assertEquals("unspecified", editBugPage.getVersion());

		assertEquals("blocker", editBugPage.getSeverity());
		assertEquals("http://www.test-bugzilla.org", editBugPage.getUrl());
		assertEquals("", editBugPage.getDependsOn());
		assertEquals("", editBugPage.getBlocks());

		/*#if ($Letsubmitterchoosepriority)*/
		assertEquals("---", editBugPage.getPriority());
		/*#else*/
		assertEquals("Highest", editBugPage.getPriority());
		/*#end*/

		assertEquals("100.0", editBugPage.getTimeEstimated());
		assertEquals("100.0", editBugPage.getTimeCurrentEstimation());
		assertEquals("0.0 +", editBugPage.getTimeWorkCompleted());
		assertEquals("0", editBugPage.getTimeWorked());
		assertEquals("100.0", editBugPage.getTimeHoursLeft());
		assertEquals("0", editBugPage.getTimeCompletedInPercent());
		assertEquals("0.0", editBugPage.getTimeGain());
		assertEquals("2016-06-12", editBugPage.getTimeDeadline());

		assertEquals("", editBugPage.getComment());

		/*#if ($SimpleBugWorkflow || $UnconfirmedState)*/
		assertEquals("CONFIRMED", editBugPage.getBugStatus());
		/*#else*/
		assertEquals("IN_PROGRESS", editBugPage.getBugStatus());
		/*#end*/

		assertEquals(comment, editBugPage.getFirstComment());
		assertEquals(1, editBugPage.getNumberOfComments());
		assertEquals(comment, editBugPage.getLastComment());
	}

	/*#if ($CommentOnBugCreation && !$CommentOnAllTransitions && !$SimpleBugWorkflow && !$UnconfirmedState)*/
	@Test
	public void testCreateEmptyDescriptionResultsInError() {
		String nowText = LocalDateTime.now().format(formatter);
		String summary = "ExampleBugStandard_" + nowText;

		// create bug
		CreateBugPage createBugPage;
		/*#if ($AddProduct)*/
		CreateBugSelectProductPage createBugSelectProductPage = (CreateBugSelectProductPage)startPage.gotoCreateBugPage();
		createBugPage = createBugSelectProductPage.setProduct("TestProduct");
		/*#else*/
		createBugPage = (CreateBugPage)startPage.gotoCreateBugPage();
		/*#end*/

		/*#if ($AddComponent)*/
		createBugPage.selectComponent("TestComponent");
		/*#end*/

		/*#if ($AddVersion)*/
		createBugPage.selectVersion("unspecified");
		/*#end*/

		createBugPage.setSummary(summary);

		assertFalse(createBugPage.commitBugWithoutComment());
		String alertMsg = createBugPage.getAlertText();
		assertEquals("You must enter a Description for this bug.", alertMsg);
	}
	/*#end*/

}
