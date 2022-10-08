package at.scch.mbteclo.model;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.state.Bug;
import at.scch.mbteclo.state.BugzillaState;
import at.scch.mbteclo.state.BugzillaState.Page;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class BugLifecycleModel extends AbstractModel {

	public BugLifecycleModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("toConfirmed")
	public void toConfirmed() {
		/*#if ($CommentOnAllTransitions)*/
		adapter.editStateWithComment("CONFIRMED");
		/*#else*/
		adapter.editState("CONFIRMED");
		/*#end*/
		state.getCurrentlyOpenBug().setStatus("CONFIRMED");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toConfirmed")
	public boolean toConfirmedGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		if(state.getPage() != Page.EditBug){
			return false;
		}
		/*#if ($SimpleBugWorkflow)*/
		return "RESOLVED".equals(status);
		/*#else*/
		return "UNCONFIRMED".equals(status) || "IN_PROGRESS".equals(status) || "RESOLVED".equals(status) || "VERIFIED".equals(status);
		/*#end*/
	}

	/*#if (!$SimpleBugWorkflow && !$UnconfirmedState)*/
	@TestStep("toUnconfirmed")
	public void toUnconfirmed() {
		/*#if ($CommentOnAllTransitions)*/
		adapter.editStateWithComment("UNCONFIRMED");
		/*#else*/
		adapter.editState("UNCONFIRMED");
		/*#end*/
		state.getCurrentlyOpenBug().setStatus("UNCONFIRMED");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toUnconfirmed")
	public boolean toUnconfirmedGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		if(state.getPage() != Page.EditBug){
			return false;
		}
		String status = bug.getStatus();
		return "RESOLVED".equals(status) || "VERIFIED".equals(status);
	}
	/*#end*/

	/*#if (!$SimpleBugWorkflow)*/
	@TestStep("toInProgress")
	public void toInProgress() {
		/*#if ($CommentOnAllTransitions)*/
		adapter.editStateWithComment("IN_PROGRESS");
		/*#else*/
		adapter.editState("IN_PROGRESS");
		/*#end*/
		state.getCurrentlyOpenBug().setStatus("IN_PROGRESS");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInProgress")
	public boolean toInProgressGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		if(state.getPage() != Page.EditBug){
			return false;
		}
		String status = bug.getStatus();
		return "UNCONFIRMED".equals(status) || "CONFIRMED".equals(status);
	}
	/*#end*/

	@TestStep("toResolved")
	public void toResolved() {
		Bug bug = state.getCurrentlyOpenBug();
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		adapter.editStateWithComment("RESOLVED");
		/*#else*/
		adapter.editState("RESOLVED");
		/*#end*/
		bug.setStatus("RESOLVED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolved")
	public boolean toResolvedGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		if(state.getPage() != Page.EditBug){
			return false;
		}
		/*#if ($NoResolveOnOpenBlockers)*/
		if(!bug.getDependsOn().isEmpty()) {
			return false;
		}
		/*#end*/
		String status = bug.getStatus();
		return "UNCONFIRMED".equals(status) || "CONFIRMED".equals(status) || "IN_PROGRESS".equals(status);
	}

	/*#if ($NoResolveOnOpenBlockers)*/
	@TestStep("toResolvedOpenBlockers")
	public void toResolvedOpenBlockers() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editStateUnresolvedDependency("RESOLVED");
		state.setPage(Page.ErrorOpenBlockers);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolvedOpenBlockers")
	public boolean toResolvedOpenBlockersGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		if(!bug.hasOpenBlockers()) {
			return false;
		}
		String status = bug.getStatus();
		return state.getPage() == Page.EditBug && ("UNCONFIRMED".equals(status) || "CONFIRMED".equals(status) || "IN_PROGRESS".equals(status));
	}
	/*#end*/

	/*#if (!$SimpleBugWorkflow)*/
	@TestStep("toVerified")
	public void toVerified() {
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
		adapter.editStateWithComment("VERIFIED");
		/*#else*/
		adapter.editState("VERIFIED");
		/*#end*/
		state.getCurrentlyOpenBug().setStatus("VERIFIED");
		state.getCurrentlyOpenBug().setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toVerified")
	public boolean toVerifiedGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		if(state.getPage() != Page.EditBug){
			return false;
		}
		String status = bug.getStatus();
		return "RESOLVED".equals(status);
	}
	/*#end*/

	@Guard({"toFixedResolution", "toInvalidResolution", "toWontFixResolution", "toWorksForMeResolution"})
	public boolean resolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return ("RESOLVED".equals(status) || "VERIFIED".equals(status)) && state.getPage() == Page.EditBug;
	}

	@TestStep("toFixedResolution")
	public void toFixedResolution() {
		Bug bug = state.getCurrentlyOpenBug();
		/*#if ($CommentOnChangeResolution)*/
		adapter.editResolutionWithComment("FIXED");
		/*#else*/
		adapter.editResolution("FIXED");
		/*#end*/
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toFixedResolution")
	public boolean toFixedResolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		/*#if ($NoResolveOnOpenBlockers)*/
		if(!bug.getDependsOn().isEmpty()) {
			return false;
		}
		/*#end*/
		String resolution = bug.getResolution();
		return !"FIXED".equals(resolution);
	}

//	/*#if ($NoResolveOnOpenBlockers)*/
//	@TestStep("toFixedResolutionBlockers")
//	public void toFixedResolutionBlockers() {
//		Bug bug = state.getCurrentlyOpenBug();
//		adapter.editStateUnresolvedDependency("RESOLVED");
//		state.setPage(Page.ErrorOpenBlockers);
//		state.setScenario(null);
//		state.setCurrentlyOpenBug(null);
//	}
//
//	@Guard("toFixedResolutionBlockers")
//	public boolean toFixedResolutionBlockersGuard() {
//		Bug bug = state.getCurrentlyOpenBug();
//		if(bug == null){
//			return false;
//		}
//		if(bug.getDependsOn().isEmpty()) {
//			return false;
//		}
//		String resolution = bug.getResolution();
//		String status = bug.getStatus();
//		return "RESOLVED".equals(status) && !"FIXED".equals(resolution) && state.getPage() == Page.EditBug ;
//	}
//	/*#end*/

	@TestStep("toInvalidResolution")
	public void toInvalidResolution() {
		/*#if ($CommentOnChangeResolution)*/
		adapter.editResolutionWithComment("INVALID");
		/*#else*/
		adapter.editResolution("INVALID");
		/*#end*/
		state.getCurrentlyOpenBug().setResolution("INVALID");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInvalidResolution")
	public boolean toInvalidResolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"INVALID".equals(resolution);
	}

	@TestStep("toWontFixResolution")
	public void toWontFixResolution() {
		/*#if ($CommentOnChangeResolution)*/
		adapter.editResolutionWithComment("WONTFIX");
		/*#else*/
		adapter.editResolution("WONTFIX");
		/*#end*/
		state.getCurrentlyOpenBug().setResolution("WONTFIX");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toWontFixResolution")
	public boolean toWontFixResolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"WONTFIX".equals(resolution);
	}

	@TestStep("toWorksForMeResolution")
	public void toWorksForMeResolution() {
		/*#if ($CommentOnChangeResolution)*/
		adapter.editResolutionWithComment("WORKSFORME");
		/*#else*/
		adapter.editResolution("WORKSFORME");
		/*#end*/
		state.getCurrentlyOpenBug().setResolution("WORKSFORME");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toWorksForMeResolution")
	public boolean toWorksForMeResolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"WORKSFORME".equals(resolution);
	}

	@TestStep("markAsDuplicate")
	public void markAsDuplicate() {
		Bug bug = state.getCurrentlyOpenBug();
		Bug duplicateOf = state.getRandomBug(bug);
		boolean circle = bug.duplicateWouldLeadToCircle(duplicateOf);
		if(circle){
			adapter.markAsDuplicateWithCircle(duplicateOf);
			state.setPage(Page.ErrorCircularDuplicates);
		} else {
			/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution || $CommentOnDuplicate)*/
			adapter.markAsDuplicateWithComment(duplicateOf);
			/*#else*/
			adapter.markAsDuplicate(duplicateOf);
			/*#end*/
			/*#if ($DuplicateOrMoveBugStatusVerified)*/
			bug.setStatus("VERIFIED");
			/*#else*/
			bug.setStatus("RESOLVED");
			/*#end*/
			bug.setResolution("DUPLICATE");
			bug.setDuplicateOf(duplicateOf);
			state.setPage(Page.BugChanged);
		}
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("markAsDuplicate")
	public boolean markAsDuplicateGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		String resolution = bug.getResolution();
		return ("UNCONFIRMED".equals(status) || "CONFIRMED".equals(status) || "IN_PROGRESS".equals(status) || "RESOLVED".equals(status)) &&
			state.getPage() == Page.EditBug && state.getBugs().size() >= 2 && !"DUPLICATE".equals(resolution);
	}

	/*#if ($CommentOnAllTransitions)*/
	@Guard("toConfirmedWithoutComment")
	public boolean guardWithoutComment() {
		return state.getPage() == Page.EditBug;
	}

	@TestStep("toConfirmedWithoutComment")
	public void toConfirmedWithoutComment() {
		adapter.editState("CONFIRMED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toConfirmedWithoutComment")
	public boolean toConfirmedWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		/*#if ($SimpleBugWorkflow)*/
		return "RESOLVED".equals(status);
		/*#else*/
		return "UNCONFIRMED".equals(status) || "IN_PROGRESS".equals(status) || "RESOLVED".equals(status) || "VERIFIED".equals(status);
		/*#end*/
	}

	/*#if (!$SimpleBugWorkflow && !$UnconfirmedState)*/
	@TestStep("toUnconfirmedWithoutComment")
	public void toUnconfirmedWithoutComment() {
		adapter.editState("UNCONFIRMED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toUnconfirmedWithoutComment")
	public boolean toUnconfirmedGuardWithoutComment() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return state.getPage() == Page.EditBug && ("RESOLVED".equals(status) || "VERIFIED".equals(status));
	}
	/*#end*/

	/*#if (!$SimpleBugWorkflow)*/
	@TestStep("toInProgressWithoutComment")
	public void toInProgressWithoutComment() {
		adapter.editState("IN_PROGRESS");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInProgressWithoutComment")
	public boolean toInProgressWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return state.getPage() == Page.EditBug && ("UNCONFIRMED".equals(status) || "CONFIRMED".equals(status));
	}
	/*#end*/
	/*#end*/

	/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution)*/
	@TestStep("toResolvedWithoutComment")
	public void toResolvedWithoutComment() {
		adapter.editState("RESOLVED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolvedWithoutComment")
	public boolean toResolvedWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return state.getPage() == Page.EditBug && ("UNCONFIRMED".equals(status) || "CONFIRMED".equals(status) || "IN_PROGRESS".equals(status));
	}
	/*#end*/

	/*#if ($CommentOnAllTransitions)*/
	/*#if (!$SimpleBugWorkflow)*/
	@TestStep("toVerifiedWithoutComment")
	public void toVerifiedWithoutComment() {
		adapter.editState("VERIFIED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toVerifiedWithoutComment")
	public boolean toVerifiedWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return state.getPage() == Page.EditBug && "RESOLVED".equals(status);
	}
	/*#end*/
	/*#end*/

	/*#if ($CommentOnChangeResolution)*/
	@Guard({"toFixedResolutionWithoutComment", "toInvalidResolutionWithoutComment", "toWontFixResolutionWithoutComment", "toWorksForMeResolutionWithoutComment"})
	public boolean resolutionWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return ("RESOLVED".equals(status) || "VERIFIED".equals(status)) && state.getPage() == Page.EditBug;
	}

	@TestStep("toFixedResolutionWithoutComment")
	public void toFixedResolutionWithoutComment() {
		adapter.editResolution("FIXED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toFixedResolutionWithoutComment")
	public boolean toFixedResolutionWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"FIXED".equals(resolution);
	}

	@TestStep("toInvalidResolutionWithoutComment")
	public void toInvalidResolutionWithoutComment() {
		adapter.editResolution("INVALID");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInvalidResolutionWithoutComment")
	public boolean toInvalidResolutionWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"INVALID".equals(resolution);
	}

	@TestStep("toWontFixResolutionWithoutComment")
	public void toWontFixResolutionWithoutComment() {
		adapter.editResolution("WONTFIX");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toWontFixResolutionWithoutComment")
	public boolean toWontFixResolutionWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"WONTFIX".equals(resolution);
	}

	@TestStep("toWorksForMeResolutionWithoutComment")
	public void toWorksForMeResolutionWithoutComment() {
		adapter.editResolution("WORKSFORME");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toWorksForMeResolutionWithoutComment")
	public boolean toWorksForMeResolutionWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"WORKSFORME".equals(resolution);
	}
	/*#end*/

	/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution || $CommentOnDuplicate)*/
	/*#if (!$DuplicateOrMoveBugStatusVerified)*/
	@TestStep("markAsDuplicateWithoutComment")
	public void markAsDuplicateWithoutComment() {
		Bug bug = state.getCurrentlyOpenBug();
		Bug duplicateOf = state.getRandomBug(bug);
		adapter.markAsDuplicateWithoutRequiredComment(duplicateOf);
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("markAsDuplicateWithoutComment")
	public boolean markAsDuplicateWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		String resolution = bug.getResolution();
		boolean guardResult = "UNCONFIRMED".equals(status);
		guardResult = guardResult || "CONFIRMED".equals(status);
		guardResult = guardResult || "IN_PROGRESS".equals(status);
		/*#if (!$CommentOnAllTransitions)*/
		guardResult = guardResult || "RESOLVED".equals(status);
		/*#end*/
		return guardResult && state.getPage() == Page.EditBug && state.getBugs().size() >= 2 && !"DUPLICATE".equals(resolution);
	}
	/*#end*/
	/*#end*/
}
