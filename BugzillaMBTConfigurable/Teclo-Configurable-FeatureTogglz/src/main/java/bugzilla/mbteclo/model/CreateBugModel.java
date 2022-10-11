package bugzilla.mbteclo.model;

import bugzilla.mbteclo.adapter.Adapter;
import bugzilla.mbteclo.state.Bug;
import bugzilla.mbteclo.state.BugzillaState;
import bugzilla.mbteclo.state.ConfigurationOption;
import bugzilla.mbteclo.state.Product;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestStep;

public class CreateBugModel extends AbstractModel {

	private Product selectedProduct = null;

	public CreateBugModel(Adapter adapter, BugzillaState state) {
		super(adapter, state);
	}

	@TestStep("gotoFileABugOneProduct")
	public void gotoFileABugOneProduct() {
		adapter.gotoCreateABug();
		state.setPage(BugzillaState.Page.FileABug);
		this.selectedProduct = BugzillaState.DEFAULT_PRODUCT;
		state.setScenario(BugzillaState.Scenario.Create);
	}

	@Guard("gotoFileABugOneProduct")
	public boolean gotoFileABugOneProductConfigGuard() {
		return !ConfigurationOption.AddProduct.isActive();
	}

	@TestStep("gotoFileABugMultipleProducts")
	public void gotoFileABugMultipleProducts() {
		adapter.gotoCreateABug();
		state.setPage(BugzillaState.Page.SelectProduct);
		state.setScenario(BugzillaState.Scenario.Create);
	}

	@Guard("gotoFileABugMultipleProducts")
	public boolean gotoFileABugMultipleProductsConfigGuard() {
		return ConfigurationOption.AddProduct.isActive();
	}

	@Guard({"gotoFileABugOneProduct", "gotoFileABugMultipleProducts"})
	public boolean gotoFileABugGuard() {
		return state.getScenario() == null && state.getPage() != BugzillaState.Page.FileABug && state.getPage() != BugzillaState.Page.SelectProduct && adapter.isLoggedIn();
	}

	@TestStep("selectProduct")
	public void selectProduct() {
		Product randProduct = state.getRandomProduct();
		adapter.selectProduct(randProduct);
		state.setPage(BugzillaState.Page.FileABug);
		state.setScenario(BugzillaState.Scenario.Create);
		this.selectedProduct = randProduct;
	}

	@Guard("selectProduct")
	public boolean selectProductConfigGuard() {
		return ConfigurationOption.AddProduct.isActive();
	}

	@Guard("selectProduct")
	public boolean selectProductGuard() {
		return state.getPage() == BugzillaState.Page.SelectProduct && adapter.isLoggedIn();
	}

	@TestStep("toggleAdvancedFields")
	public void toggleAdvancedFields(){
		state.setScenario(BugzillaState.Scenario.Create);
		adapter.toggleAdvancedFields();
		if(adapter.areAdvancedFieldsShown()){
			state.setPage(BugzillaState.Page.FileABugAdvanced);
		} else {
			state.setPage(BugzillaState.Page.FileABug);
		}
	}

	@Guard("toggleAdvancedFields")
	public boolean toggleAdvancedFieldsGuard() {
		return state.getPage() == BugzillaState.Page.FileABug || state.getPage() == BugzillaState.Page.FileABugAdvanced;
	}

	@TestStep("createSimple")
	public void createSimple() {
		Bug newBug = bugFactory.getValidBug(this.selectedProduct);
		System.out.println("before createSimple: " + newBug);

		adapter.createSimple(newBug, state.getBugs());
		state.setPage(BugzillaState.Page.EditBug);
		state.setCurrentlyOpenBug(newBug);
		state.setScenario(null);
		state.addBug(newBug);
		this.selectedProduct.addBug(newBug);

		System.out.println("after createSimple:  " + newBug);
	}

	@Guard("createSimple")
	public boolean createSimpleGuard() {
		return state.getPage() == BugzillaState.Page.FileABug && this.selectedProduct != null;
	}

	@TestStep("createAdvanced")
	public void createAdvanced() {
		Bug newBug = bugFactory.getValidBugAdvanced(this.selectedProduct);
		System.out.println("before createAdvanced: " + newBug);

		adapter.createAdvanced(newBug, state.getBugs());
		state.setPage(BugzillaState.Page.EditBug);
		state.setCurrentlyOpenBug(newBug);
		state.setScenario(null);
		state.addBug(newBug);
		this.selectedProduct.addBug(newBug);

		System.out.println("after createAdvanced:  " + newBug);
	}

	@Guard("createAdvanced")
	public boolean createAdvancedGuard() {
		return state.getPage() == BugzillaState.Page.FileABugAdvanced && this.selectedProduct != null;
	}

	@TestStep("createInvalid")
	public void createInvalid() {
		Bug newBug = bugFactory.getErrorBug(this.selectedProduct);
		adapter.createInvalid(newBug, state.getBugs());
		state.setPage(BugzillaState.Page.FileBugError);
		state.setScenario(BugzillaState.Scenario.Create);
	}

	@Guard("createInvalid")
	public boolean createInvalidGuard() {
		return state.getPage() == BugzillaState.Page.FileABug && this.selectedProduct != null;
	}

	@TestStep("createInvalidAdvanced")
	public void createInvalidAdvanced() {
		Bug newBug = bugFactory.getErrorBugAdvanced(this.selectedProduct);
		adapter.createInvalidAdvanced(newBug, state.getBugs());
		state.setPage(BugzillaState.Page.FileBugError);
		state.setScenario(BugzillaState.Scenario.Create);
	}

	@Guard("createInvalidAdvanced")
	public boolean createInvalidAdvancedGuard() {
		return state.getPage() == BugzillaState.Page.FileABugAdvanced && this.selectedProduct != null;
	}

	@TestStep("createSimpleWithoutComment")
	public void createSimpleWithoutComment() {
		Bug newBug = bugFactory.getErrorNoCommentBug(this.selectedProduct);
		System.out.println("before createSimpleWithoutComment: " + newBug);

		adapter.createInvalidMissingComment(newBug, state.getBugs());
		state.setPage(BugzillaState.Page.FileBugError);
		state.setScenario(BugzillaState.Scenario.Create);

		System.out.println("after createSimpleWithoutComment:  " + newBug);
	}

	@Guard("createSimpleWithoutComment")
	public boolean createSimpleWithoutConfigCommentGuard() {
		return (ConfigurationOption.CommentOnBugCreation.isActive() && !ConfigurationOption.CommentOnAllTransitions.isActive())
				|| (!ConfigurationOption.CommentOnBugCreation.isActive() && ConfigurationOption.CommentOnAllTransitions.isActive());
	}

	@Guard("createSimpleWithoutComment")
	public boolean createSimpleWithoutCommentGuard() {
		return state.getPage() == BugzillaState.Page.FileABug && this.selectedProduct != null;
	}

	@TestStep("createAdvancedWithoutComment")
	public void createAdvancedWithoutComment() {
		Bug newBug = bugFactory.getErrorNoCommentBugAdvanced(this.selectedProduct);
		System.out.println("before createAdvancedWithoutComment: " + newBug);

		adapter.createInvalidAdvancedMissingComment(newBug, state.getBugs());
		state.setPage(BugzillaState.Page.FileBugError);
		state.setScenario(BugzillaState.Scenario.Create);

		System.out.println("after createAdvancedWithoutComment:  " + newBug);
	}

	@Guard("createAdvancedWithoutComment")
	public boolean createAdvancedWithoutCommentConfigCommentGuard() {
		return (ConfigurationOption.CommentOnBugCreation.isActive() && !ConfigurationOption.CommentOnAllTransitions.isActive())
				|| (!ConfigurationOption.CommentOnBugCreation.isActive() && ConfigurationOption.CommentOnAllTransitions.isActive());
	}

	@Guard("createAdvancedWithoutComment")
	public boolean createAdvancedWithoutCommentGuard() {
		return state.getPage() == BugzillaState.Page.FileABugAdvanced && this.selectedProduct != null;
	}

	@TestStep("goBackToCreate")
	public void goBackToCreate() {
		adapter.goBackToCreate();
		if(adapter.areAdvancedFieldsShown()){
			state.setPage(BugzillaState.Page.FileABugAdvanced);
		} else {
			state.setPage(BugzillaState.Page.FileABug);
		}
	}

	@Guard("goBackToCreate")
	public boolean goBackToCreateGuard() {
		return state.getPage() == BugzillaState.Page.FileBugError;
	}
}
