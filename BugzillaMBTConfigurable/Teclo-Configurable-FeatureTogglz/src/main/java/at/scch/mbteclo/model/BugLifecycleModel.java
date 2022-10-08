package at.scch.mbteclo.model;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.state.Bug;
import at.scch.mbteclo.state.BugzillaState;
import at.scch.mbteclo.state.BugzillaState.Page;
import at.scch.mbteclo.state.ConfigurationOption;
import at.scch.teclo.configuration.CommentOnAllTransitions;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class BugLifecycleModel extends AbstractModel {

	public BugLifecycleModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("toConfirmedNoComment")
	public void toConfirmedNoComment() {
		adapter.editState("CONFIRMED");
		state.getCurrentlyOpenBug().setStatus("CONFIRMED");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toConfirmedNoComment")
	public boolean toConfirmedNoCommentConfigGuard() {
		return !ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@TestStep("toConfirmedComment")
	public void toConfirmedComment() {
		adapter.editStateWithComment("CONFIRMED");
		state.getCurrentlyOpenBug().setStatus("CONFIRMED");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toConfirmedComment")
	public boolean toConfirmedCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@Guard({"toConfirmedNoComment", "toConfirmedComment"})
	public boolean toConfirmedConfigGuard() {
		return !ConfigurationOption.SimpleBugWorkflow.isActive();
	}

	@Guard({"toConfirmedNoComment", "toConfirmedComment"})
	public boolean toConfirmedGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		if(state.getPage() != Page.EditBug){
			return false;
		}

		return "UNCONFIRMED".equals(status) || "IN_PROGRESS".equals(status) || "RESOLVED".equals(status) || "VERIFIED".equals(status);
	}

	@TestStep("toConfirmedNoCommentSimple")
	public void toConfirmedNoCommentSimple() {
		adapter.editState("CONFIRMED");
		state.getCurrentlyOpenBug().setStatus("CONFIRMED");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toConfirmedNoCommentSimple")
	public boolean toConfirmedNoCommenSimpletConfigGuard() {
		return !ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@TestStep("toConfirmedCommentSimple")
	public void toConfirmedCommentSimple() {
		adapter.editStateWithComment("CONFIRMED");
		state.getCurrentlyOpenBug().setStatus("CONFIRMED");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toConfirmedCommentSimple")
	public boolean toConfirmedCommentConfigSimpleGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@Guard({"toConfirmedNoCommentSimple", "toConfirmedCommentSimple"})
	public boolean toConfirmedSimpleConfigGuard() {
		return ConfigurationOption.SimpleBugWorkflow.isActive();
	}

	@Guard({"toConfirmedNoCommentSimple", "toConfirmedCommentSimple"})
	public boolean toConfirmedSimpleGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		if(state.getPage() != Page.EditBug){
			return false;
		}

		return "RESOLVED".equals(status);
	}

	@TestStep("toUnconfirmedNoComment")
	public void toUnconfirmedNoComment() {
		adapter.editState("UNCONFIRMED");
		state.getCurrentlyOpenBug().setStatus("UNCONFIRMED");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toUnconfirmedNoComment")
	public boolean toUnconfirmedNoCommentConfigGuard() {
		return !ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@TestStep("toUnconfirmedComment")
	public void toUnconfirmedComment() {
		adapter.editStateWithComment("UNCONFIRMED");
		state.getCurrentlyOpenBug().setStatus("UNCONFIRMED");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toUnconfirmedComment")
	public boolean toUnconfirmedCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@Guard({"toUnconfirmedNoComment", "toUnconfirmedComment"})
	public boolean toUnconfirmedConfigGuard() {
		return !ConfigurationOption.SimpleBugWorkflow.isActive() && !ConfigurationOption.UnconfirmedState.isActive();
	}

	@Guard({"toUnconfirmedNoComment", "toUnconfirmedComment"})
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

	@TestStep("toInProgressNoComment")
	public void toInProgressNoComment() {
		adapter.editState("IN_PROGRESS");
		state.getCurrentlyOpenBug().setStatus("IN_PROGRESS");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInProgressNoComment")
	public boolean toInProgressNoCommentConfigGuard() {
		return !ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@TestStep("toInProgressComment")
	public void toInProgressComment() {
		adapter.editStateWithComment("IN_PROGRESS");
		state.getCurrentlyOpenBug().setStatus("IN_PROGRESS");
		state.getCurrentlyOpenBug().setResolution("");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInProgressComment")
	public boolean toInProgressCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@Guard({"toInProgressNoComment", "toInProgressComment"})
	public boolean toInProgressConfigGuard() {
		return !ConfigurationOption.SimpleBugWorkflow.isActive();
	}

	@Guard({"toInProgressNoComment", "toInProgressComment"})
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

	@TestStep("toResolvedNoComment")
	public void toResolvedNoComment() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editState("RESOLVED");
		bug.setStatus("RESOLVED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolvedNoComment")
	public boolean toResolvedNoCommentConfigGuard() {
		return !(ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive());
	}

	@TestStep("toResolvedComment")
	public void toResolvedComment() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editStateWithComment("RESOLVED");
		bug.setStatus("RESOLVED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolvedComment")
	public boolean toResolvedCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@Guard({"toResolvedNoComment", "toResolvedComment"})
	public boolean toResolvedConfigGuard() {
		return !ConfigurationOption.NoResolveOnOpenBlockers.isActive();
	}

	@Guard({"toResolvedNoComment", "toResolvedComment"})
	public boolean toResolvedGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		if(state.getPage() != Page.EditBug){
			return false;
		}
		String status = bug.getStatus();
		return "UNCONFIRMED".equals(status) || "CONFIRMED".equals(status) || "IN_PROGRESS".equals(status);
	}

	@TestStep("toResolvedNoCommentBlockers")
	public void toResolvedNoCommentBlockers() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editState("RESOLVED");
		bug.setStatus("RESOLVED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolvedNoCommentBlockers")
	public boolean toResolvedNoCommentBlockersConfigGuard() {
		return !(ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive());
	}

	@TestStep("toResolvedCommentBlockers")
	public void toResolvedCommentBlockers() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editStateWithComment("RESOLVED");
		bug.setStatus("RESOLVED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolvedCommentBlockers")
	public boolean toResolvedCommentBlockersConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@Guard({"toResolvedNoCommentBlockers", "toResolvedCommentBlockers"})
	public boolean toResolvedBlockersConfigGuard() {
		return ConfigurationOption.NoResolveOnOpenBlockers.isActive();
	}

	@Guard({"toResolvedNoCommentBlockers", "toResolvedCommentBlockers"})
	public boolean toResolvedBlockersGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		if(state.getPage() != Page.EditBug){
			return false;
		}
		if(!bug.getDependsOn().isEmpty()) {
			return false;
		}
		String status = bug.getStatus();
		return "UNCONFIRMED".equals(status) || "CONFIRMED".equals(status) || "IN_PROGRESS".equals(status);
	}

	@TestStep("toResolvedOpenBlockers")
	public void toResolvedOpenBlockers() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editStateUnresolvedDependency("RESOLVED");
		state.setPage(Page.ErrorOpenBlockers);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolvedOpenBlockers")
	public boolean toResolvedOpenBlockersConfigGuard() {
		return ConfigurationOption.NoResolveOnOpenBlockers.isActive();
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

	@TestStep("toVerifiedNoComment")
	public void toVerifiedNoComment() {
		adapter.editState("VERIFIED");
		state.getCurrentlyOpenBug().setStatus("VERIFIED");
		state.getCurrentlyOpenBug().setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toVerifiedNoComment")
	public boolean toVerifiedNoCommentConfigGuard() {
		return !(ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive());
	}

	@TestStep("toVerifiedComment")
	public void toVerifiedComment() {
		adapter.editStateWithComment("VERIFIED");
		state.getCurrentlyOpenBug().setStatus("VERIFIED");
		state.getCurrentlyOpenBug().setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toVerifiedComment")
	public boolean toVerifiedCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@Guard({"toVerifiedNoComment", "toVerifiedComment"})
	public boolean toVerifiedConfigGuard() {
		return !ConfigurationOption.SimpleBugWorkflow.isActive();
	}

	@Guard({"toVerifiedNoComment", "toVerifiedComment"})
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

	@Guard({"toFixedResolutionNoComment", "toFixedResolutionComment", "toFixedResolutionCommentBlockers", "toFixedResolutionNoCommentBlockers", "toInvalidResolutionNoComment", "toInvalidResolutionComment", "toWontFixResolutionNoComment", "toWontFixResolutionComment", "toWorksForMeResolutionNoComment", "toWorksForMeResolutionComment"})
	public boolean resolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return ("RESOLVED".equals(status) || "VERIFIED".equals(status)) && state.getPage() == Page.EditBug;
	}

	@TestStep("toFixedResolutionNoComment")
	public void toFixedResolutionNoComment() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editResolution("FIXED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toFixedResolutionNoComment")
	public boolean toFixedResolutionNoCommentConfigGuard() {
		return !ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@TestStep("toFixedResolutionComment")
	public void toFixedResolutionComment() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editResolutionWithComment("FIXED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toFixedResolutionComment")
	public boolean toFixedResolutionCommentConfigGuard() {
		return ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@Guard({"toFixedResolutionComment", "toFixedResolutionNoComment"})
	public boolean toFixedResolutionConfigGuard() {
		return !ConfigurationOption.NoResolveOnOpenBlockers.isActive();
	}

	@Guard({"toFixedResolutionComment", "toFixedResolutionNoComment"})
	public boolean toFixedResolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"FIXED".equals(resolution);
	}

	@TestStep("toFixedResolutionNoCommentBlockers")
	public void toFixedResolutionNoCommentBlockers() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editResolution("FIXED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toFixedResolutionNoCommentBlockers")
	public boolean toFixedResolutionNoCommentBlockersConfigGuard() {
		return !ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@TestStep("toFixedResolutionCommentBlockers")
	public void toFixedResolutionCommentBlockers() {
		Bug bug = state.getCurrentlyOpenBug();
		adapter.editResolutionWithComment("FIXED");
		bug.setResolution("FIXED");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toFixedResolutionCommentBlockers")
	public boolean toFixedResolutionCommentBlockersConfigGuard() {
		return ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@Guard({"toFixedResolutionCommentBlockers", "toFixedResolutionNoCommentBlockers"})
	public boolean toFixedResolutionBlockersConfigGuard() {
		return ConfigurationOption.NoResolveOnOpenBlockers.isActive();
	}

	@Guard({"toFixedResolutionCommentBlockers", "toFixedResolutionNoCommentBlockers"})
	public boolean toFixedResolutionBlockersGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		if(!bug.getDependsOn().isEmpty()) {
			return false;
		}
		String resolution = bug.getResolution();
		return !"FIXED".equals(resolution);
	}

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

	@TestStep("toInvalidResolutionNoComment")
	public void toInvalidResolutionNoComment() {
		adapter.editResolution("INVALID");
		state.getCurrentlyOpenBug().setResolution("INVALID");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInvalidResolutionNoComment")
	public boolean toInvalidResolutionNoCommentConfigGuard() {
		return !ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@TestStep("toInvalidResolutionComment")
	public void toInvalidResolutionComment() {
		adapter.editResolutionWithComment("INVALID");
		state.getCurrentlyOpenBug().setResolution("INVALID");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInvalidResolutionComment")
	public boolean toInvalidResolutionCommentConfigGuard() {
		return ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@Guard({"toInvalidResolutionNoComment", "toInvalidResolutionComment"})
	public boolean toInvalidResolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"INVALID".equals(resolution);
	}

	@TestStep("toWontFixResolutionNoComment")
	public void toWontFixResolutionNoComment() {
		adapter.editResolution("WONTFIX");
		state.getCurrentlyOpenBug().setResolution("WONTFIX");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toWontFixResolutionNoComment")
	public boolean toWontFixResolutionNoCommentConfigGuard() {
		return !ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@TestStep("toWontFixResolutionComment")
	public void toWontFixResolutionComment() {
		adapter.editResolutionWithComment("WONTFIX");
		state.getCurrentlyOpenBug().setResolution("WONTFIX");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toWontFixResolutionComment")
	public boolean toWontFixResolutionCommentConfigGuard() {
		return ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@Guard({"toWontFixResolutionNoComment", "toWontFixResolutionComment"})
	public boolean toWontFixResolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"WONTFIX".equals(resolution);
	}

	@TestStep("toWorksForMeResolutionNoComment")
	public void toWorksForMeResolutionNoComment() {
		adapter.editResolution("WORKSFORME");
		state.getCurrentlyOpenBug().setResolution("WORKSFORME");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toWorksForMeResolutionNoComment")
	public boolean toWorksForMeResolutionNoCommentConfigGuard() {
		return !ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@TestStep("toWorksForMeResolutionComment")
	public void toWorksForMeResolutionComment() {
		adapter.editResolutionWithComment("WORKSFORME");
		state.getCurrentlyOpenBug().setResolution("WORKSFORME");
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toWorksForMeResolutionComment")
	public boolean toWorksForMeResolutionCommentConfigGuard() {
		return ConfigurationOption.CommentOnChangeResolution.isActive();
	}

	@Guard({"toWorksForMeResolutionNoComment", "toWorksForMeResolutionComment"})
	public boolean toWorksForMeResolutionGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String resolution = bug.getResolution();
		return !"WORKSFORME".equals(resolution);
	}

	@TestStep("markAsDuplicateVerifiedNoComment")
	public void markAsDuplicateVerifiedNoComment() {
		Bug bug = state.getCurrentlyOpenBug();
		Bug duplicateOf = state.getRandomBug(bug);
		boolean circle = bug.duplicateWouldLeadToCircle(duplicateOf);
		if(circle){
			adapter.markAsDuplicateWithCircle(duplicateOf);
			state.setPage(Page.ErrorCircularDuplicates);
		} else {
			adapter.markAsDuplicate(duplicateOf);
			bug.setStatus("VERIFIED");
			bug.setResolution("DUPLICATE");
			bug.setDuplicateOf(duplicateOf);
			state.setPage(Page.BugChanged);
		}
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("markAsDuplicateVerifiedNoComment")
	public boolean markAsDuplicateVerifiedNoCommentConfigGuard() {
		return !(ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive() || ConfigurationOption.CommentOnDuplicate.isActive());
	}

	@TestStep("markAsDuplicateVerifiedComment")
	public void markAsDuplicateVerifiedComment() {
		Bug bug = state.getCurrentlyOpenBug();
		Bug duplicateOf = state.getRandomBug(bug);
		boolean circle = bug.duplicateWouldLeadToCircle(duplicateOf);
		if(circle){
			adapter.markAsDuplicateWithCircle(duplicateOf);
			state.setPage(Page.ErrorCircularDuplicates);
		} else {
			adapter.markAsDuplicateWithComment(duplicateOf);
			bug.setStatus("VERIFIED");
			bug.setResolution("DUPLICATE");
			bug.setDuplicateOf(duplicateOf);
			state.setPage(Page.BugChanged);
		}
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("markAsDuplicateVerifiedComment")
	public boolean markAsDuplicateVerifiedCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive() || ConfigurationOption.CommentOnDuplicate.isActive();
	}

	@Guard({"markAsDuplicateVerifiedNoComment", "markAsDuplicateVerifiedComment"})
	public boolean markAsDuplicateVerifiedConfigGuard() {
		return ConfigurationOption.DuplicateOrMoveBugStatusVerified.isActive();
	}

	@TestStep("markAsDuplicateResolvedNoComment")
	public void markAsDuplicateResolvedNoComment() {
		Bug bug = state.getCurrentlyOpenBug();
		Bug duplicateOf = state.getRandomBug(bug);
		boolean circle = bug.duplicateWouldLeadToCircle(duplicateOf);
		if(circle){
			adapter.markAsDuplicateWithCircle(duplicateOf);
			state.setPage(Page.ErrorCircularDuplicates);
		} else {
			adapter.markAsDuplicate(duplicateOf);
			bug.setStatus("VERIFIED");
			bug.setResolution("DUPLICATE");
			bug.setDuplicateOf(duplicateOf);
			state.setPage(Page.BugChanged);
		}
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("markAsDuplicateResolvedNoComment")
	public boolean markAsDuplicateResolvedNoCommentConfigGuard() {
		return !(ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive() || ConfigurationOption.CommentOnDuplicate.isActive());
	}

	@TestStep("markAsDuplicateResolvedComment")
	public void markAsDuplicateResolvedComment() {
		Bug bug = state.getCurrentlyOpenBug();
		Bug duplicateOf = state.getRandomBug(bug);
		boolean circle = bug.duplicateWouldLeadToCircle(duplicateOf);
		if(circle){
			adapter.markAsDuplicateWithCircle(duplicateOf);
			state.setPage(Page.ErrorCircularDuplicates);
		} else {
			adapter.markAsDuplicateWithComment(duplicateOf);
			bug.setStatus("VERIFIED");
			bug.setResolution("DUPLICATE");
			bug.setDuplicateOf(duplicateOf);
			state.setPage(Page.BugChanged);
		}
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("markAsDuplicateResolvedComment")
	public boolean markAsDuplicateResolvedCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive() || ConfigurationOption.CommentOnDuplicate.isActive();
	}

	@Guard({"markAsDuplicateResolvedNoComment", "markAsDuplicateResolvedComment"})
	public boolean markAsDuplicateResolvedConfigGuard() {
		return !ConfigurationOption.DuplicateOrMoveBugStatusVerified.isActive();
	}

	@Guard({"markAsDuplicateVerifiedNoComment", "markAsDuplicateVerifiedComment", "markAsDuplicateResolvedNoComment", "markAsDuplicateResolvedComment"})
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

	@Guard({"toConfirmedWithoutComment", "toConfirmedWithoutCommentSimple", "toUnconfirmedWithoutComment", "toInProgressWithoutComment", "toVerifiedWithoutComment"})
	public boolean changeStateWithoutCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive();
	}

	@Guard({"toConfirmedWithoutComment", "toConfirmedWithoutCommentSimple"})
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
	public boolean toConfirmedWithoutCommentConfigGuard() {
		return !ConfigurationOption.SimpleBugWorkflow.isActive();
	}

	@Guard("toConfirmedWithoutComment")
	public boolean toConfirmedWithoutCommentGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return "UNCONFIRMED".equals(status) || "IN_PROGRESS".equals(status) || "RESOLVED".equals(status) || "VERIFIED".equals(status);
	}

	@TestStep("toConfirmedWithoutCommentSimple")
	public void toConfirmedWithoutCommentSimple() {
		adapter.editState("CONFIRMED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toConfirmedWithoutCommentSimple")
	public boolean toConfirmedWithoutCommentSimpleConfigGuard() {
		return ConfigurationOption.SimpleBugWorkflow.isActive();
	}

	@Guard("toConfirmedWithoutCommentSimple")
	public boolean toConfirmedWithoutCommentSimpleGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		return "RESOLVED".equals(status);
	}

	@TestStep("toUnconfirmedWithoutComment")
	public void toUnconfirmedWithoutComment() {
		adapter.editState("UNCONFIRMED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toUnconfirmedWithoutComment")
	public boolean toUnconfirmedConfigGuardWithoutComment() {
		return !ConfigurationOption.SimpleBugWorkflow.isActive() && !ConfigurationOption.UnconfirmedState.isActive();
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

	@TestStep("toInProgressWithoutComment")
	public void toInProgressWithoutComment() {
		adapter.editState("IN_PROGRESS");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toInProgressWithoutComment")
	public boolean toInProgressConfigGuardWithoutComment() {
		return !ConfigurationOption.SimpleBugWorkflow.isActive();
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

	@TestStep("toResolvedWithoutComment")
	public void toResolvedWithoutComment() {
		adapter.editState("RESOLVED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toResolvedWithoutComment")
	public boolean toResolvedWithoutCommentConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive() || ConfigurationOption.CommentOnChangeResolution.isActive();
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

	@TestStep("toVerifiedWithoutComment")
	public void toVerifiedWithoutComment() {
		adapter.editState("VERIFIED");
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("toVerifiedWithoutComment")
	public boolean toVerifiedWithoutCommentConfigGuard() {
		return !ConfigurationOption.SimpleBugWorkflow.isActive();
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

	@Guard({"toFixedResolutionWithoutComment", "toInvalidResolutionWithoutComment", "toWontFixResolutionWithoutComment", "toWorksForMeResolutionWithoutComment"})
	public boolean resolutionWithoutCommentConfigGuard() {
		return ConfigurationOption.CommentOnChangeResolution.isActive();
	}

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

	@TestStep("markAsDuplicateWithoutComment")
	public void markAsDuplicateWithoutComment() {
		Bug bug = state.getCurrentlyOpenBug();
		Bug duplicateOf = state.getRandomBug(bug);
		adapter.markAsDuplicate(duplicateOf);
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("markAsDuplicateWithoutComment")
	public boolean markAsDuplicateWithoutCommentConfigGuard() {
		return !ConfigurationOption.CommentOnAllTransitions.isActive() &&
				(ConfigurationOption.CommentOnChangeResolution.isActive() || ConfigurationOption.CommentOnDuplicate.isActive())
				&& !ConfigurationOption.DuplicateOrMoveBugStatusVerified.isActive();
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
		guardResult = guardResult || "RESOLVED".equals(status);
		return guardResult && state.getPage() == Page.EditBug && state.getBugs().size() >= 2 && !"DUPLICATE".equals(resolution);
	}

	@TestStep("markAsDuplicateWithoutCommentAll")
	public void markAsDuplicateWithoutCommentAll() {
		Bug bug = state.getCurrentlyOpenBug();
		Bug duplicateOf = state.getRandomBug(bug);
		adapter.markAsDuplicate(duplicateOf);
		state.setPage(Page.ErrorCommentRequired);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);
	}

	@Guard("markAsDuplicateWithoutCommentAll")
	public boolean markAsDuplicateWithoutCommentAllConfigGuard() {
		return ConfigurationOption.CommentOnAllTransitions.isActive() && !ConfigurationOption.DuplicateOrMoveBugStatusVerified.isActive();
	}

	@Guard("markAsDuplicateWithoutCommentAll")
	public boolean markAsDuplicateWithoutCommentAllGuard() {
		Bug bug = state.getCurrentlyOpenBug();
		if(bug == null){
			return false;
		}
		String status = bug.getStatus();
		String resolution = bug.getResolution();
		boolean guardResult = "UNCONFIRMED".equals(status);
		guardResult = guardResult || "CONFIRMED".equals(status);
		guardResult = guardResult || "IN_PROGRESS".equals(status);
		return guardResult && state.getPage() == Page.EditBug && state.getBugs().size() >= 2 && !"DUPLICATE".equals(resolution);
	}
}
