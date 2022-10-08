package at.scch.teclo.tests;

import at.scch.teclo.AbstractBugzillaTestWithLogin;
import at.scch.teclo.BugzillaSetup;
import at.scch.teclo.pageobjects.BugChangedPage;
import at.scch.teclo.pageobjects.EditBugPage;
import at.scch.teclo.pageobjects.ErrorSummaryNeededPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class EditBugTest extends AbstractBugzillaTestWithLogin {
	private int currentBugId;
	private String currentBugSummary;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

	@Before
	public void setUp() throws Exception {
		// precondition: bug inserted
		currentBugId = BugzillaSetup.getExampleBugId();
		currentBugSummary = BugzillaSetup.getExampleBugSummary();
	}

	@Test
	public void testEditSummary() throws Exception {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		editBugPage.setSummary("Test Summary !\"�$%&/(=?\\#*1234567890\'.:;,");
		BugChangedPage bugChangedPage = editBugPage.commitBug();
		assertEquals("Changes submitted for bug " + currentBugId, bugChangedPage.getSuccessMsg());
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("Test Summary !\"�$%&/(=?\\#*1234567890\'.:;,", editBugPage.getSummary());

		editBugPage.setSummary(currentBugSummary);
		bugChangedPage = editBugPage.commitBug();
		assertEquals("Changes submitted for bug " + currentBugId, bugChangedPage.getSuccessMsg());
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals(currentBugSummary, editBugPage.getSummary());
	}

	@Test
	public void testEditEmptySummary() throws Exception {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		editBugPage.setSummary("");
		ErrorSummaryNeededPage errorSummaryNeededPage = editBugPage.commitBugWithEmptySummary();
		assertEquals("You must enter a summary for this bug.", errorSummaryNeededPage.getErrorMsg());

		// verify no changes were made
		editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		assertEquals(currentBugSummary, editBugPage.getSummary());
	}

	@Test
	public void testEditBugFields() throws Exception {
		// browse to the current bug
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		// edit bug
		editBugPage.setPlatform("Other");
		editBugPage.setOpSys("Linux");
		editBugPage.setPriority("Lowest");
		editBugPage.setSeverity("critical");

		// commit bug
		BugChangedPage bugChangedPage = editBugPage.commitBug();
		assertEquals("Changes submitted for bug " + currentBugId, bugChangedPage.getSuccessMsg());
		editBugPage = bugChangedPage.gotoChangedBugPage();

		// verify changes
		assertEquals("Other", editBugPage.getPlatform());
		assertEquals("Linux", editBugPage.getOpSys());
		assertEquals("Lowest", editBugPage.getPriority());
		assertEquals("critical", editBugPage.getSeverity());
	}

	@Test
	public void testAddComment() {
		String nowText = LocalDateTime.now().format(formatter);
		String comment = "This is an example comment for testAddComment created at " + nowText;
				
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		int currentAmountOfComments = editBugPage.getNumberOfComments();
		
		editBugPage.setComment(comment);
		
		BugChangedPage bugChangedPage = editBugPage.commitBug();
		assertEquals("Changes submitted for bug " + currentBugId, bugChangedPage.getSuccessMsg());
		editBugPage = bugChangedPage.gotoChangedBugPage();
		
		assertEquals(currentAmountOfComments+1, editBugPage.getNumberOfComments());
		assertEquals(comment, editBugPage.getLastComment());
	}

	@Test
	public void testEditTimes() throws Exception {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		// set time
		editBugPage.setTimeEstimated(100);
		editBugPage.setTimeHoursLeft(100);
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		
		// verify
		assertEquals("100.0", editBugPage.getTimeEstimated());
		assertEquals("100.0", editBugPage.getTimeCurrentEstimation());
		assertEquals("100.0", editBugPage.getTimeHoursLeft());
		
		// change time
		editBugPage.setTimeWorked(50.0);
		editBugPage.setComment("Edited hours left to 50.0!");
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		
		// verify
		assertEquals("50.0 +", editBugPage.getTimeWorkCompleted());
		assertEquals("50.0", editBugPage.getTimeHoursLeft());
		assertEquals("50", editBugPage.getTimeCompletedInPercent());
		
		// change time
		editBugPage.setTimeHoursLeft(40.0);
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		
		// verify
		assertEquals("90.0", editBugPage.getTimeCurrentEstimation());
		assertEquals("40.0", editBugPage.getTimeHoursLeft());
		assertEquals("55", editBugPage.getTimeCompletedInPercent());
		assertEquals("10.0", editBugPage.getTimeGain());
	}

	@Test
	public void testEditDeadline() throws Exception {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		editBugPage.setTimeDeadline("1991-12-17");
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("1991-12-17", editBugPage.getTimeDeadline());

		editBugPage.setTimeDeadline("2038-01-16");
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("2038-01-16", editBugPage.getTimeDeadline());
	}

	@Test
	public void testEditUrl() {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		
		editBugPage.setUrl("http://www.test-bugzilla.org");
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("http://www.test-bugzilla.org", editBugPage.getUrl());
		
		editBugPage.setUrl("");
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("", editBugPage.getUrl());
	}

	@Test
	public void testEditDependsOn() {
		int dependingOnBugId = BugzillaSetup.createExampleBug();
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		
		editBugPage.setDependsOn(dependingOnBugId);
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("edited page (currentBugId) should show dependsOnId as depending on", 
				dependingOnBugId, Integer.parseInt(editBugPage.getDependsOn()));

		editBugPage = BugzillaSetup.gotoEditBugPage(dependingOnBugId);
		assertEquals("dependingOn page should show currentBugId (edited page) as blocks", 
				currentBugId, Integer.parseInt(editBugPage.getBlocks()));
		
		editBugPage.setBlocks("");
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("dependingOn page should have cleared blocks", 
				"", editBugPage.getBlocks());
		
		editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		assertEquals("edited page (currentBugId) should have cleared depending on", 
				"", editBugPage.getDependsOn());
	}

	/*#if ($UseStatusWhiteboard)*/
	@Test
	public void testEditStatusWhiteboard(){
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		editBugPage.setStatusWhiteboard("testing edit of status whiteboard field");
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("testing edit of status whiteboard field", editBugPage.getStatusWhiteboard());

		editBugPage.setStatusWhiteboard("");
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("", editBugPage.getStatusWhiteboard());
	}
	/*#end*/

	/*#if ($AddComponent)*/
	@Test
	public void testEditComponent() throws Exception{
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		assertEquals("TestComponent", editBugPage.getComponent());

		editBugPage.setComponent("AddedComponent");

		editBugPage.commitBug();

		editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		assertEquals("AddedComponent", editBugPage.getComponent());

		editBugPage.setComponent("TestComponent");

		editBugPage.commitBug();

		editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		assertEquals("TestComponent", editBugPage.getComponent());
	}
	/*#end*/

	/*#if ($AddVersion)*/
	@Test
	public void testEditVersion() throws Exception{
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		assertEquals("unspecified", editBugPage.getVersion());

		editBugPage.setVersion("AddedVersion");

		editBugPage.commitBug();

		editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		assertEquals("AddedVersion", editBugPage.getVersion());

		editBugPage.setVersion("unspecified");

		editBugPage.commitBug();

		editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		assertEquals("unspecified", editBugPage.getVersion());
	}
	/*#end*/

	@After
	public void tearDown() throws Exception {
		// postcondition: leave changes as they are as long as there is no interference
	}
}
