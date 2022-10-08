package at.scch.mbteclo.model;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.factories.BugFactory;
import at.scch.mbteclo.state.Bug;
import at.scch.mbteclo.state.BugzillaState;
import at.scch.mbteclo.state.BugzillaState.Page;
import at.scch.mbteclo.state.BugzillaState.Scenario;
import at.scch.mbteclo.state.Product;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditBugModel extends AbstractModel {

	public EditBugModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("gotoEditBug")
	public void gotoEditBug() {
		Bug bug = state.getRandomBug();
		adapter.gotoBugEdit(bug.getId());
		state.setPage(Page.EditBug);
		state.setScenario(Scenario.Edit);
		state.setCurrentlyOpenBug(bug);

		System.out.println("gotoEditBug: " + bug);
	}

	@Guard("gotoEditBug")
	public boolean gotoEditBugGuard() {
		return state.getScenario() == null && !state.getBugs().isEmpty() && state.getPage() != Page.EditBug && adapter.isLoggedIn();
	}

	@TestStep("editSummary")
	public void editSummary() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before editSummary: " + bug);

		adapter.editBugSummary("Bug_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")), bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editSummary:  " + bug);
	}

	@TestStep("deleteSummary")
	public void deleteSummary() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before deleteSummary: " + bug);

		adapter.editBugSummary("", bug);
		state.setPage(Page.ErrorSummaryNeeded);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after deleteSummary:  " + bug);
	}

	@TestStep("editBugFields")
	public void editBugFields() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before editBugFields: " + bug);

		String hardware = BugFactory.getRandomHardWare();
		String os = BugFactory.getRandomOS();
		String severity = BugFactory.getRandomSeverity();
		String priority = BugFactory.getRandomPriority();
		//edit hardware, OS, severity, priority
		adapter.editBugFields(hardware, os, severity, priority, bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editBugFields:  " + bug);
	}

	@TestStep("addComment")
	public void addComment() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before addComment: " + bug);

		String comment = "This is an example comment for testAddComment created at " + BugzillaState.id;
		//addComment
		adapter.addComment(comment, bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after addComment:  " + bug);
	}

	@TestStep("editTimes")
	public void editTimes() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before editTimes: " + bug);

		adapter.editTimes(bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editTimes:  " + bug);
	}

	@TestStep("editDeadline")
	public void editDeadline() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before editDeadline: " + bug);

		String randomDeadline = BugFactory.getRandomDate();
		adapter.editDeadline(randomDeadline, bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editDeadline:  " + bug);
	}

	@TestStep("editUrl")
	public void editUrl() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before editUrl: " + bug);

		String url = "http://www.test-bugzilla.org";
		adapter.editUrl(url, bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editUrl:  " + bug);
	}

	@Guard({"editSummary", "deleteSummary", "editBugFields", "addComment", "editTimes", "editDeadline", "editUrl"})
	public boolean editSummaryGuard() {
		return state.getPage() == Page.EditBug && state.getCurrentlyOpenBug() != null;
	}

	//EditDependsOn
	@TestStep("editDependsOn")
	public void editDependsOn() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before editDependsOn: " + bug);

		Bug dependsOn = state.getRandomBug(bug);

		boolean circle = bug.dependencyWouldLeadToCircle(dependsOn);

		System.out.println("dependsOn: " + dependsOn);
		System.out.println("circle: " + circle);

		if(circle){
			adapter.editDependsOnWithCircle(dependsOn, bug);
			state.setPage(Page.ErrorCircularDependency);
		} else {
			adapter.editDependsOn(dependsOn, bug);
			state.setPage(Page.BugChanged);
		}
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editDependsOn:  " + bug);
	}

	@Guard("editDependsOn")
	public boolean editDependsOnGuard() {
		return state.getPage() == Page.EditBug && state.getCurrentlyOpenBug() != null && state.getBugs().size() >= 2;
	}

	/*#if ($UseStatusWhiteboard)*/
	@TestStep("editStatusWhiteboard")
	public void editStatusWhiteboard() {
		Bug bug = state.getCurrentlyOpenBug();
		System.out.println("before editStatusWhiteboard: " + bug);

		adapter.editStatusWhiteboard("Status Bug_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")), bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editStatusWhiteboard:  " + bug);
	}

	@Guard("editStatusWhiteboard")
	public boolean editStatusWhiteboardGuard() {
		return state.getPage() == Page.EditBug && state.getCurrentlyOpenBug() != null;
	}
	/*#end*/

	/*#if ($AddProduct)*/
	@TestStep("editProduct")
	public void editProduct() {
		Bug bug = state.getCurrentlyOpenBug();
		Product product = state.getRandomProduct(bug.getProduct());
		String component = product.getRandomComponent();
		String version = product.getRandomVersion();

		System.out.println("before editProduct: " + bug);

		adapter.editProduct(product, component, version, bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editProduct:  " + bug);
	}

	@Guard("editProduct")
	public boolean editProductGuard() {
		return state.getPage() == Page.EditBug && state.getCurrentlyOpenBug() != null && state.products.size() > 1;
	}
	/*#end*/

	/*#if ($AddComponent)*/
	@TestStep("editComponent")
	public void editComponent() {
		Bug bug = state.getCurrentlyOpenBug();
		Product product = state.getProduct(bug.getProduct());
		String component = product.getRandomComponent(bug.getComponent());

		System.out.println("before editComponent: " + bug);

		adapter.editComponent(component, bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editComponent:  " + bug);
	}

	@Guard("editComponent")
	public boolean editComponentGuard() {
		if(state.getCurrentlyOpenBug() == null){
			return false;
		}
		Product product = state.getProduct(state.getCurrentlyOpenBug().getProduct());
		if(product == null){
			return false;
		}
		return state.getPage() == Page.EditBug && product.getComponents().size() > 1;
	}
	/*#end*/

	/*#if ($AddVersion)*/
	@TestStep("editVersion")
	public void editVersion() {
		Bug bug = state.getCurrentlyOpenBug();
		Product product = state.getProduct(bug.getProduct());
		String version = product.getRandomVersion(bug.getVersion());

		System.out.println("before editVersion: " + bug);

		adapter.editVersion(version, bug);
		state.setPage(Page.BugChanged);
		state.setScenario(null);
		state.setCurrentlyOpenBug(null);

		System.out.println("after editVersion:  " + bug);
	}

	@Guard("editVersion")
	public boolean editVersionGuard() {
		if(state.getCurrentlyOpenBug() == null){
			return false;
		}
		Product product = state.getProduct(state.getCurrentlyOpenBug().getProduct());
		if(product == null){
			return false;
		}
		return state.getPage() == Page.EditBug && product.getVersions().size() > 1;
	}
	/*#end*/

	@TestStep("goBackToEdit")
	public void goBackToEdit() {
		Bug lastBug = state.getLastOpenBug();
		adapter.gotoBugEdit(lastBug.getId());
		state.setPage(Page.EditBug);
		state.setScenario(Scenario.Edit);
		System.out.println("before goBackToEdit: " + lastBug);
		state.setCurrentlyOpenBug(lastBug);
		System.out.println("after goBackToEdit:  " + state.getCurrentlyOpenBug());
	}

	@Guard("goBackToEdit")
	public boolean goBackToEditGuard() {
		return state.getLastOpenBug() != null &&
				(state.getPage() == Page.ErrorSummaryNeeded ||
				state.getPage() == Page.ErrorCommentRequired ||
				state.getPage() == Page.ErrorOpenBlockers ||
				state.getPage() == Page.ErrorCircularDependency ||
				state.getPage() == Page.ErrorCircularDuplicates ||
				state.getPage() == Page.BugChanged);
	}
}
