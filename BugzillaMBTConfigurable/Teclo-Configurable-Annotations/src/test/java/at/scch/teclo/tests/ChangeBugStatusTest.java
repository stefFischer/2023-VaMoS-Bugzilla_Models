package at.scch.teclo.tests;

import at.scch.teclo.AbstractBugzillaTestWithLogin;
import at.scch.teclo.BugzillaSetup;
import at.scch.teclo.pageobjects.BugChangedPage;
import at.scch.teclo.pageobjects.EditBugPage;
import at.scch.teclo.pageobjects.ErrorCommentRequiredPage;
import at.scch.teclo.pageobjects.ErrorUnresolvedDependencyPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.*;

public class ChangeBugStatusTest extends AbstractBugzillaTestWithLogin {
	private int currentBugId;

	@Before
	public void setUp() throws Exception {
		currentBugId = BugzillaSetup.getExampleBugId();
	}

	@Test
	public void testNormalStatusCycle() {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		assertEquals("CONFIRMED", editBugPage.getBugStatus());

		BugChangedPage bugChangedPage;

		/*#if (!$SimpleBugWorkflow)*/
		editBugPage.setBugStatus("IN_PROGRESS");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set in progress");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("IN_PROGRESS", editBugPage.getBugStatus());
		/*#end*/

		editBugPage.setBugStatus("RESOLVED");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set resolved");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("RESOLVED", editBugPage.getBugStatus());
		assertEquals("FIXED", editBugPage.getBugResolution());

		/*#if (!$SimpleBugWorkflow)*/
		editBugPage.setBugStatus("VERIFIED");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set verified");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("VERIFIED", editBugPage.getBugStatus());
		assertEquals("FIXED", editBugPage.getBugResolution());
		/*#end*/

		/*#if (!$SimpleBugWorkflow && !$UnconfirmedState)*/
		editBugPage.setBugStatus("UNCONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set unconfirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("UNCONFIRMED", editBugPage.getBugStatus());
		
		editBugPage.setBugStatus("IN_PROGRESS");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set in progress");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("IN_PROGRESS", editBugPage.getBugStatus());
		/*#end*/

		editBugPage.setBugStatus("CONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set confirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("CONFIRMED", editBugPage.getBugStatus());
	}

	@Test
	public void testFastStatusCycle() {

		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		assertEquals("CONFIRMED", editBugPage.getBugStatus());

		editBugPage.setBugStatus("RESOLVED");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set resolved");
		/*#end*/
		BugChangedPage bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("RESOLVED", editBugPage.getBugStatus());
		assertEquals("FIXED", editBugPage.getBugResolution());

		/*#if (!$SimpleBugWorkflow)*/
		editBugPage.setBugStatus("VERIFIED");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set verified");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("VERIFIED", editBugPage.getBugStatus());
		assertEquals("FIXED", editBugPage.getBugResolution());
		/*#end*/

		/*#if (!$SimpleBugWorkflow && !$UnconfirmedState)*/
		editBugPage.setBugStatus("UNCONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set unconfirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("UNCONFIRMED", editBugPage.getBugStatus());
		/*#end*/

		editBugPage.setBugStatus("CONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set confirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("CONFIRMED", editBugPage.getBugStatus());
	}

	@Test
	public void testResolutions() {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		assertEquals("CONFIRMED", editBugPage.getBugStatus());

		editBugPage.setBugStatus("RESOLVED");
		editBugPage.setBugResolution("WORKSFORME");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set resolved");
		/*#end*/
		BugChangedPage bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("RESOLVED", editBugPage.getBugStatus());
		assertEquals("WORKSFORME", editBugPage.getBugResolution());

		editBugPage.setBugResolution("FIXED");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set fixed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("RESOLVED", editBugPage.getBugStatus());
		assertEquals("FIXED", editBugPage.getBugResolution());

		/*#if (!$SimpleBugWorkflow)*/
		editBugPage.setBugStatus("VERIFIED");
		/*#end*/
		editBugPage.setBugResolution("WONTFIX");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set verified");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		/*#if (!$SimpleBugWorkflow)*/
		assertEquals("VERIFIED", editBugPage.getBugStatus());
		/*#end*/
		assertEquals("WONTFIX", editBugPage.getBugResolution());	
		
		editBugPage.setBugResolution("INVALID");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set invalid");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		/*#if (!$SimpleBugWorkflow)*/
		assertEquals("VERIFIED", editBugPage.getBugStatus());
		/*#end*/
		assertEquals("INVALID", editBugPage.getBugResolution());

		/*#if (!$SimpleBugWorkflow && !$UnconfirmedState)*/
		editBugPage.setBugStatus("UNCONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set unconfirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("UNCONFIRMED", editBugPage.getBugStatus());
		/*#end*/

		editBugPage.setBugStatus("CONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set confirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("CONFIRMED", editBugPage.getBugStatus());
	}

	/*#if ($CommentOnChangeResolution)*/
	@Test
	public void testResolutionsWithoutComment() {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		assertEquals("CONFIRMED", editBugPage.getBugStatus());

		editBugPage.setBugStatus("RESOLVED");
		editBugPage.setBugResolution("WORKSFORME");
		editBugPage.setComment("Changed resolution to WORKSFORME");
		BugChangedPage bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("RESOLVED", editBugPage.getBugStatus());
		assertEquals("WORKSFORME", editBugPage.getBugResolution());

		editBugPage.setBugResolution("FIXED");
		ErrorCommentRequiredPage errorCommentRequiredPage = editBugPage.commitBugWithEmptyComment();
		assertEquals("You have to specify a comment when changing the Resolution of a bug from WORKSFORME to FIXED.",
				errorCommentRequiredPage.getErrorMsg());

		// set bug back to initial state NEW
		editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		/*#if (!$SimpleBugWorkflow && !$UnconfirmedState)*/
		editBugPage.setBugStatus("UNCONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set unconfirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("UNCONFIRMED", editBugPage.getBugStatus());
		/*#end*/

		editBugPage.setBugStatus("CONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set confirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("CONFIRMED", editBugPage.getBugStatus());
	}
	/*#end*/

	@Test
	public void testMarkAsDuplicate() {
		
		// create second bug to mark as duplicate
		int duplicateBugId = BugzillaSetup.createExampleBug();
		
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution || $CommentOnDuplicate)*/
		String comment = "Example comment for marking duplicate.";
		editBugPage.setComment(comment);
		/*#end*/

		// mark the bug as duplicate of current bug
		editBugPage.clickMarkAsDuplicate();
		editBugPage.setBugDuplicateOf(duplicateBugId);
		
		// commit
		BugChangedPage bugChangedPage = editBugPage.commitBug();
		assertEquals("Changes submitted for bug " + currentBugId, bugChangedPage.getSuccessMsg());
		editBugPage = bugChangedPage.gotoChangedBugPage();

		
		// verify on duplicated bug
		/*#if ($DuplicateOrMoveBugStatusVerified)*/
		assertEquals("VERIFIED", editBugPage.getBugStatus());
		/*#else*/
		assertEquals("RESOLVED", editBugPage.getBugStatus());
		/*#end*/

		assertEquals("DUPLICATE", editBugPage.getBugResolution());

		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution || $CommentOnDuplicate)*/
		String automaticallyInsertedLinebreaks = "  ";
		assertEquals(comment + automaticallyInsertedLinebreaks + "*** This bug has been marked as a duplicate of bug "+duplicateBugId+" ***", editBugPage.getLastComment());
		/*#else*/
		assertEquals("*** This bug has been marked as a duplicate of bug "+duplicateBugId+" ***", editBugPage.getLastComment());
		/*#end*/

		// go to original bug
		editBugPage = BugzillaSetup.gotoEditBugPage(duplicateBugId);
		
		// verify on current bug
		assertEquals("*** Bug "+currentBugId+" has been marked as a duplicate of this bug. ***", editBugPage.getLastComment());
	}

	/*#if ($CommentOnDuplicate)*/
	@Test
	public void testMarkAsDuplicateWithoutComment() {

		// create second bug to mark as duplicate
		int duplicateBugId = BugzillaSetup.createExampleBug();

		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		// mark the bug as duplicate of current bug
		editBugPage.clickMarkAsDuplicate();
		editBugPage.setBugDuplicateOf(duplicateBugId);

		// commit
		ErrorCommentRequiredPage errorCommentRequiredPage = editBugPage.commitBugWithEmptyComment();

		//error message changes depending on configuration, and it is difficult to predict when which message will come
		assertTrue(errorCommentRequiredPage.getErrorMsg().startsWith("You have to specify a comment "));
	}
	/*#end*/

	/*#if ($UnconfirmedState)*/
	@Test(expected = NoSuchElementException.class)
	public void testUnconfirmedBugStatus() {
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		assertEquals("CONFIRMED", editBugPage.getBugStatus());

		editBugPage.setBugStatus("RESOLVED");
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set resolved");
		/*#end*/
		BugChangedPage bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("RESOLVED", editBugPage.getBugStatus());
		assertEquals("FIXED", editBugPage.getBugResolution());

		editBugPage.setBugStatus("UNCONFIRMED");
		/*#if ($CommentOnAllTransitions)*/
		editBugPage.setComment("set unconfirmed");
		/*#end*/
		bugChangedPage = editBugPage.commitBug();
		editBugPage = bugChangedPage.gotoChangedBugPage();
		assertEquals("CONFIRMED", editBugPage.getBugStatus());
	}
	/*#end*/

	/*#if ($NoResolveOnOpenBlockers)*/
	@Test
	public void testNoResolveOnOpenBlockers(){
		int dependingOnBugId = BugzillaSetup.createExampleBug();
		EditBugPage editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);

		editBugPage.setDependsOn(dependingOnBugId);
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();
		assertEquals("edited page (currentBugId) should show dependsOnId as depending on",
				dependingOnBugId, Integer.parseInt(editBugPage.getDependsOn()));

		editBugPage.setBugStatus("RESOLVED");
		editBugPage.setBugResolution("FIXED");

		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		editBugPage.setComment("set resolved");
		/*#end*/

		// commit
		ErrorUnresolvedDependencyPage errorUnresolvedDependencyPage = editBugPage.commitBugWithUnresolvedDependency();
		assertEquals("Bug "+currentBugId+" has 1 unresolved dependency. They must either be resolved or removed from the \"Depends on\" field before you can resolve this bug as FIXED.",
				errorUnresolvedDependencyPage.getErrorMsg());

		// remove dependency & commit
		editBugPage = BugzillaSetup.gotoEditBugPage(currentBugId);
		editBugPage.setDependsOn(0);
		editBugPage = editBugPage.commitBug().gotoChangedBugPage();

		// verify
		assertEquals("edited page (currentBugId) should have cleared depending on",
				"", editBugPage.getDependsOn());
	}
	/*#end*/
}
