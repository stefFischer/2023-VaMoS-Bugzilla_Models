package at.scch.mbteclo.model;

import at.scch.mbteclo.adapter.Adapter;
import at.scch.mbteclo.state.Bug;
import at.scch.mbteclo.state.BugzillaState;
import at.scch.mbteclo.state.BugzillaState.Page;
import at.scch.mbteclo.state.BugzillaState.Scenario;
import at.scch.mbteclo.state.Product;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class CreateBugModel extends AbstractModel {

	private Product selectedProduct = null;

	public CreateBugModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("gotoFileABug")
	public void gotoFileABug() {
		adapter.gotoCreateABug();
		/*#if ($AddProduct)*/
		state.setPage(Page.SelectProduct);
		/*#else*/
		state.setPage(Page.FileABug);
		this.selectedProduct = BugzillaState.DEFAULT_PRODUCT;
		/*#end*/
		state.setScenario(Scenario.Create);
	}

	@Guard("gotoFileABug")
	public boolean gotoFileABugGuard() {
		return state.getScenario() == null && state.getPage() != Page.FileABug && state.getPage() != Page.SelectProduct && adapter.isLoggedIn();
	}

	/*#if ($AddProduct)*/
	@TestStep("selectProduct")
	public void selectProduct() {
		Product randProduct = state.getRandomProduct();
		adapter.selectProduct(randProduct);
		state.setPage(Page.FileABug);
		state.setScenario(Scenario.Create);
		this.selectedProduct = randProduct;
	}

	@Guard("selectProduct")
	public boolean selectProductGuard() {
		return state.getPage() == Page.SelectProduct && adapter.isLoggedIn();
	}
	/*#end*/

	@TestStep("toggleAdvancedFields")
	public void toggleAdvancedFields(){
		state.setScenario(Scenario.Create);
		adapter.toggleAdvancedFields();
		if(adapter.areAdvancedFieldsShown()){
			state.setPage(Page.FileABugAdvanced);
		} else {
			state.setPage(Page.FileABug);
		}
	}

	@Guard("toggleAdvancedFields")
	public boolean toggleAdvancedFieldsGuard() {
		return state.getPage() == Page.FileABug || state.getPage() == Page.FileABugAdvanced;
	}

	@TestStep("createSimple")
	public void createSimple() {
		Bug newBug = bugFactory.getValidBug(this.selectedProduct);
		System.out.println("before createSimple: " + newBug);

		adapter.createSimple(newBug, state.getBugs());
		state.setPage(Page.EditBug);
		state.setCurrentlyOpenBug(newBug);
		state.setScenario(null);
		state.addBug(newBug);
		this.selectedProduct.addBug(newBug);

		System.out.println("after createSimple:  " + newBug);
	}

	@Guard("createSimple")
	public boolean createSimpleGuard() {
		return state.getPage() == Page.FileABug && this.selectedProduct != null;
	}

	@TestStep("createAdvanced")
	public void createAdvanced() {
		Bug newBug = bugFactory.getValidBugAdvanced(this.selectedProduct);
		System.out.println("before createAdvanced: " + newBug);

		adapter.createAdvanced(newBug, state.getBugs());
		state.setPage(Page.EditBug);
		state.setCurrentlyOpenBug(newBug);
		state.setScenario(null);
		state.addBug(newBug);
		this.selectedProduct.addBug(newBug);

		System.out.println("after createAdvanced:  " + newBug);
	}

	@Guard("createAdvanced")
	public boolean createAdvancedGuard() {
		return state.getPage() == Page.FileABugAdvanced && this.selectedProduct != null;
	}

	@TestStep("createInvalid")
	public void createInvalid() {
		Bug newBug = bugFactory.getErrorBug(this.selectedProduct);
		adapter.createInvalid(newBug, state.getBugs());
		state.setPage(Page.FileBugError);
		state.setScenario(Scenario.Create);
	}

	@Guard("createInvalid")
	public boolean createInvalidGuard() {
		return state.getPage() == Page.FileABug && this.selectedProduct != null;
	}

	@TestStep("createInvalidAdvanced")
	public void createInvalidAdvanced() {
		Bug newBug = bugFactory.getErrorBugAdvanced(this.selectedProduct);
		adapter.createInvalidAdvanced(newBug, state.getBugs());
		state.setPage(Page.FileBugError);
		state.setScenario(Scenario.Create);
	}

	@Guard("createInvalidAdvanced")
	public boolean createInvalidAdvancedGuard() {
		return state.getPage() == Page.FileABugAdvanced && this.selectedProduct != null;
	}

	/*#if ($CommentOnBugCreation && $CommentOnAllTransitions)*/
	/*#else*/
	/*#if ($CommentOnBugCreation || $CommentOnAllTransitions)*/
	@TestStep("createSimpleWithoutComment")
	public void createSimpleWithoutComment() {
		Bug newBug = bugFactory.getErrorNoCommentBug(this.selectedProduct);
		System.out.println("before createSimpleWithoutComment: " + newBug);

		adapter.createInvalidMissingComment(newBug, state.getBugs());
		state.setPage(Page.FileBugError);
		state.setScenario(Scenario.Create);

		System.out.println("after createSimpleWithoutComment:  " + newBug);
	}

	@Guard("createSimpleWithoutComment")
	public boolean createSimpleWithoutCommentGuard() {
		return state.getPage() == Page.FileABug && this.selectedProduct != null;
	}

	@TestStep("createAdvancedWithoutComment")
	public void createAdvancedWithoutComment() {
		Bug newBug = bugFactory.getErrorNoCommentBugAdvanced(this.selectedProduct);
		System.out.println("before createAdvancedWithoutComment: " + newBug);

		adapter.createInvalidAdvancedMissingComment(newBug, state.getBugs());
		state.setPage(Page.FileBugError);
		state.setScenario(Scenario.Create);

		System.out.println("after createAdvancedWithoutComment:  " + newBug);
	}

	@Guard("createAdvancedWithoutComment")
	public boolean createAdvancedWithoutCommentGuard() {
		return state.getPage() == Page.FileABugAdvanced && this.selectedProduct != null;
	}
	/*#end*/
	/*#end*/

	@TestStep("goBackToCreate")
	public void goBackToCreate() {
		adapter.goBackToCreate();
		if(adapter.areAdvancedFieldsShown()){
			state.setPage(Page.FileABugAdvanced);
		} else {
			state.setPage(Page.FileABug);
		}
	}

	@Guard("goBackToCreate")
	public boolean goBackToCreateGuard() {
		return state.getPage() == Page.FileBugError;
	}
}
