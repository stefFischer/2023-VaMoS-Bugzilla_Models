package bugzilla.mbteclo.adapter;

import bugzilla.mbteclo.state.Bug;
import bugzilla.mbteclo.state.Product;
import bugzilla.mbteclo.state.Query;
import bugzilla.teclo.BugzillaSetup;
import bugzilla.teclo.pageobjects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Provides an adapter for the model that executes everything through the page
 * objects.
 */
public class Adapter {

	private AbstractBugzillaPage currentPage;
	private String username;
	private String password;

	public Adapter(AbstractBugzillaPage currentPage, String password, String username) {
		this.currentPage = currentPage;
		this.password = password;
		this.username = username;
	}

	public void gotoLogin() {
		currentPage = currentPage.gotoLoginPage();
	}

	public boolean isLoggedIn() {
		return currentPage.isLoggedIn();
	}

	public void login() {
		login(username, password);
	}

	public void login(String username, String password) {
		currentPage = ((LoginPage) currentPage).loginSuccessful(username, password);
		currentPage = currentPage.gotoStartPage();
		assertTrue(isLoggedIn());
		assertEquals(username, currentPage.getLoggedInUser());
	}

	public void loginError(String username, String password) {
		currentPage = ((LoginPage) currentPage).loginFailing(username, password);
		assertFalse(isLoggedIn());
		assertEquals(ErrorLoginPage.class, currentPage.getClass());
	}

	public void logout() {
		currentPage = currentPage.logout();
		assertFalse(currentPage.isLoggedIn());
		assertEquals(StartPage.class, currentPage.getClass());
	}

	public void gotoStart() {
		currentPage = currentPage.gotoStartPage();
	}

	public void gotoCreateABug() {
		currentPage = currentPage.gotoCreateBugPage();
	}

	public void createSimple(Bug newBug, Collection<Bug> bugs) {
		currentPage = ((CreateBugPage) currentPage).fileBasic(newBug, bugs);
		newBug.setId(((EditBugPage) currentPage).getBugId());

		assertEquals(newBug.getStatus(), ((EditBugPage) currentPage).getStatus());
		assertEquals(newBug.getSummary(), ((EditBugPage) currentPage).getSummary());
		/*#if ($UseStatusWhiteboard)*/
		assertEquals(newBug.getStatusWhiteboard(), ((EditBugPage) currentPage).getStatusWhiteboard());
		/*#end*/
	}

	public void createAdvanced(Bug newBug, Collection<Bug> bugs) {
		currentPage = ((CreateBugAdvancedPage) currentPage).fillAdvanced(newBug, bugs);
		newBug.setId(((EditBugPage) currentPage).getBugId());

		assertEquals(newBug.getStatus(), ((EditBugPage) currentPage).getStatus());
		assertEquals(newBug.getSummary(), ((EditBugPage) currentPage).getSummary());
		assertEquals(newBug.getStatus(), ((EditBugPage) currentPage).getStatus());
		/*#if ($UseStatusWhiteboard)*/
		assertEquals(newBug.getStatusWhiteboard(), ((EditBugPage) currentPage).getStatusWhiteboard());
		/*#end*/
	}

	public void createInvalid(Bug newBug, Collection<Bug> bugs) {
		currentPage = ((CreateBugPage) currentPage).fileBasic(newBug, bugs);
	}

	public void createInvalidAdvanced(Bug newBug, Collection<Bug> bugs) {
		currentPage = ((CreateBugAdvancedPage) currentPage).fillAdvanced(newBug, bugs);
	}

	/*#if ($CommentOnBugCreation || $CommentOnAllTransitions)*/
	public void createInvalidMissingComment(Bug newBug, Collection<Bug> bugs) {
		currentPage = ((CreateBugPage) currentPage).fileBasic(newBug, bugs);
	}


	public void createInvalidAdvancedMissingComment(Bug newBug, Collection<Bug> bugs) {
		currentPage = ((CreateBugAdvancedPage) currentPage).fillAdvanced(newBug, bugs);
	}
	/*#end*/

	public void goBackToCreate() {
		if(ErrorCommentRequiredOnBugCreationPage.class == currentPage.getClass()){
			currentPage = ((ErrorCommentRequiredOnBugCreationPage) currentPage).goBack();
		} else	if(ErrorCommentRequiredPage.class == currentPage.getClass()){
			BugzillaSetup.driver.navigate().back();
			try{
				BugzillaSetup.driver.findElement(By.xpath("//*[@id = 'expert_fields_controller' and contains(text(), 'Hide')]"));
				currentPage = PageFactory.initElements(BugzillaSetup.driver, CreateBugAdvancedPage.class);
			} catch (NoSuchElementException e){
				currentPage = PageFactory.initElements(BugzillaSetup.driver, CreateBugPage.class);
			}
		} else {
			currentPage = ((ErrorCreateBugPage) currentPage).goBack();
		}
		((CreateBugPage) currentPage).clearSeeAlso();
	}

	public boolean areAdvancedFieldsShown(){
		return CreateBugAdvancedPage.class == currentPage.getClass();
	}

	public void toggleAdvancedFields() {
		boolean areAdvancedFieldsShown = areAdvancedFieldsShown();
		if (!areAdvancedFieldsShown) {
			currentPage = ((CreateBugPage) currentPage).gotoAdvanced();
		} else {
			/*#if (!$SimpleBugWorkflow && !$UnconfirmedState)*/
			((CreateBugAdvancedPage) currentPage).setBugStatus("CONFIRMED");
			/*#end*/
			currentPage = ((CreateBugAdvancedPage) currentPage).gotoBasic();
		}
	}

	public void gotoBugEdit(int id) {
		currentPage = currentPage.findBug(id);
		assertEquals(id, ((EditBugPage)currentPage).getBugId());
	}

	public void editBugSummary(String newSummary, Bug bug) {
		((EditBugPage)currentPage).setSummary(newSummary);
		if(newSummary.isEmpty()){
			currentPage = ((EditBugPage)currentPage).commitBugWithEmptySummary();
			assertEquals("You must enter a summary for this bug.", ((ErrorSummaryNeededPage)currentPage).getErrorMsg());

		} else {
			currentPage = ((EditBugPage)currentPage).commitBug();
			assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
			goBackToEdit();
			//verify change
			assertEquals(newSummary, ((EditBugPage)currentPage).getSummary());

//			((EditBugPage)currentPage).setSummary(bug.getSummary()); //change back
			bug.setSummary(newSummary);

			//commit again so we are on the right page
			currentPage = ((EditBugPage)currentPage).commitBug();
			assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		}
	}

	/*#if ($UseStatusWhiteboard)*/
	public void editStatusWhiteboard(String newWhiteboard, Bug bug) {
		((EditBugPage)currentPage).setStatusWhiteboard(newWhiteboard);
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();
		//verify change
		assertEquals(newWhiteboard, ((EditBugPage)currentPage).getStatusWhiteboard());

		bug.setStatusWhiteboard(newWhiteboard);

		//commit again so we are on the right page
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}
	/*#end*/

	public void editBugFields(String hardware, String os, String severity, String priority, Bug bug){
		// edit bug
		((EditBugPage)currentPage).setPlatform(hardware);
		((EditBugPage)currentPage).setOpSys(os);
		((EditBugPage)currentPage).setPriority(priority);
		((EditBugPage)currentPage).setSeverity(severity);

		// commit bug
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();

		// verify changes
		assertEquals(hardware, ((EditBugPage)currentPage).getPlatform());
		assertEquals(os, ((EditBugPage)currentPage).getOpSys());
		assertEquals(priority, ((EditBugPage)currentPage).getPriority());
		assertEquals(severity, ((EditBugPage)currentPage).getSeverity());

		bug.setHardware(hardware);
		bug.setOs(os);
		bug.setPriority(priority);
		bug.setSeverity(severity);

		//commit again so we are on the right page
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}

	public void addComment(String comment, Bug bug){
		int currentAmountOfComments = ((EditBugPage)currentPage).getNumberOfComments();

		((EditBugPage)currentPage).setComment(comment);

		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();

		assertEquals(currentAmountOfComments+1, ((EditBugPage)currentPage).getNumberOfComments());
		assertEquals(comment, ((EditBugPage)currentPage).getLastComment());

		//commit again so we are on the right page
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}

	public void editTimes(Bug bug){
		// set time
		((EditBugPage)currentPage).setTimeEstimated(100);
		((EditBugPage)currentPage).setTimeHoursLeft(100);

		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();

		double timeWorked = Double.parseDouble(((EditBugPage)currentPage).getTimeWorkCompleted().replaceAll("\\+", "").trim());
		double timeCurrentEstimation = timeWorked + 100;

		System.out.println("timeWorked: " + timeWorked);
		System.out.println("timeCurrentEstimation: " + timeCurrentEstimation);

		// verify
		assertEquals("100.0", ((EditBugPage)currentPage).getTimeEstimated());
		assertEquals(String.valueOf(timeCurrentEstimation), ((EditBugPage)currentPage).getTimeCurrentEstimation());
		assertEquals("100.0", ((EditBugPage)currentPage).getTimeHoursLeft());

		// change time
		((EditBugPage)currentPage).setTimeWorked(50.0);
		((EditBugPage)currentPage).setComment("Edited hours left to 50.0!");

		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();

		double hoursWorked = timeWorked + 50;
		int percentage = (int)((hoursWorked / timeCurrentEstimation) * 100);

		System.out.println("hoursWorked: " + hoursWorked);
		System.out.println("percentage: " + percentage);

		// verify
		assertEquals(hoursWorked + " +", ((EditBugPage)currentPage).getTimeWorkCompleted());
		assertEquals("50.0", ((EditBugPage)currentPage).getTimeHoursLeft());
		assertEquals(String.valueOf(percentage), ((EditBugPage)currentPage).getTimeCompletedInPercent());

		// change time
		((EditBugPage)currentPage).setTimeHoursLeft(40.0);

		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();

		timeCurrentEstimation = hoursWorked + 40;
		percentage = (int)((hoursWorked / timeCurrentEstimation) * 100);
		double timeGain = 100 - (hoursWorked + 40);

		System.out.println("timeCurrentEstimation: " + timeCurrentEstimation);
		System.out.println("percentage: " + percentage);
		System.out.println("timeGain: " + timeGain);

		// verify
		assertEquals(String.valueOf(timeCurrentEstimation), ((EditBugPage)currentPage).getTimeCurrentEstimation());
		assertEquals("40.0", ((EditBugPage)currentPage).getTimeHoursLeft());
		assertEquals(String.valueOf(percentage), ((EditBugPage)currentPage).getTimeCompletedInPercent());
		assertEquals(String.valueOf(timeGain), ((EditBugPage)currentPage).getTimeGain());

		//commit again so we are on the right page
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}

	public void editDeadline(String deadline, Bug bug){
		((EditBugPage)currentPage).setTimeDeadline(deadline);

		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();

		// verify
		assertEquals(deadline, ((EditBugPage)currentPage).getTimeDeadline());

		bug.setDeadline(deadline);

		//commit again so we are on the right page
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}

	public void editUrl(String url, Bug bug){
		((EditBugPage)currentPage).setUrl(url);

		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();

		// verify
		assertEquals(url, ((EditBugPage)currentPage).getUrl());

		bug.setUrl(url);

		//commit again so we are on the right page
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}

	public void editDependsOn(Bug dependsOn, Bug bug) {
		((EditBugPage)currentPage).setDependsOn(dependsOn.getId());
		currentPage = ((EditBugPage)currentPage).commitBug();
		bug.addDependsOn(dependsOn);
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}

	public void editDependsOnWithCircle(Bug dependsOn, Bug bug) {
		((EditBugPage)currentPage).setDependsOn(dependsOn.getId());
		currentPage = ((EditBugPage)currentPage).commitBugWithCircularDependency();
	}

	public void editProduct(Product newProduct, String newComponent, String newVersion, Bug bug) {
		((EditBugPage)currentPage).setProduct(newProduct.getName());
		currentPage = ((EditBugPage)currentPage).commitChangedProduct();
		//verify component and version
		((VerifyVersionComponentPage)currentPage).setComponent(newComponent);
		((VerifyVersionComponentPage)currentPage).setVersion(newVersion);
		currentPage = ((VerifyVersionComponentPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();
		//verify change
		assertEquals(newProduct.getName(), ((EditBugPage)currentPage).getProduct());
		assertEquals(newComponent, ((EditBugPage)currentPage).getComponent());
//		assertEquals(newVersion, ((EditBugPage)currentPage).getVersion());

		bug.setProduct(newProduct.getName());
		bug.setComponent(newComponent);
		bug.setVersion(newVersion);

		//commit again so we are on the right page
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}

    public void editComponent(String newComponent, Bug bug) {
        ((EditBugPage)currentPage).setComponent(newComponent);
        currentPage = ((EditBugPage)currentPage).commitBug();
        assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
        goBackToEdit();
        //verify change
        assertEquals(newComponent, ((EditBugPage)currentPage).getComponent());

        bug.setComponent(newComponent);

        //commit again so we are on the right page
        currentPage = ((EditBugPage)currentPage).commitBug();
        assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
    }

	/*#if ($AddVersion)*/
	public void editVersion(String newVersion, Bug bug) {
		((EditBugPage)currentPage).setVersion(newVersion);
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
		goBackToEdit();
		//verify change
		assertEquals(newVersion, ((EditBugPage)currentPage).getVersion());

		bug.setVersion(newVersion);

		//commit again so we are on the right page
		currentPage = ((EditBugPage)currentPage).commitBug();
		assertEquals("Changes submitted for bug " + bug.getId(), ((BugChangedPage)currentPage).getSuccessMsg());
	}
	/*#end*/

	public void editState(String status) {
		currentPage = ((EditBugPage) currentPage).editState(status);
	}

	public void editStateWithComment(String status) {
		((EditBugPage) currentPage).setBugStatus(status);
		((EditBugPage) currentPage).setComment("set status to " + status);
		currentPage = ((EditBugPage) currentPage).commitBug();
	}

	public void editStateUnresolvedDependency(String status) {
		((EditBugPage) currentPage).setBugStatus(status);
		/*#if ($CommentOnAllTransitions)*/
		((EditBugPage) currentPage).setComment("set status to " + status);
		/*#end*/
		currentPage = ((EditBugPage) currentPage).commitBugWithUnresolvedDependency();
	}

	public void editResolution(String status) {
		currentPage = ((EditBugPage) currentPage).editResolution(status);
	}

	public void editResolutionWithComment(String resolution) {
		((EditBugPage) currentPage).setBugResolution(resolution);
		((EditBugPage) currentPage).setComment("set resolution to " + resolution);
		currentPage = ((EditBugPage) currentPage).commitBug();
	}

	/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution || $CommentOnDuplicate)*/
	public void markAsDuplicateWithoutRequiredComment(Bug duplicateOf) {
		currentPage = ((EditBugPage) currentPage).markAsDuplicateWithoutRequiredComment(duplicateOf.getId());
	}
	/*#else*/
	public void markAsDuplicate(Bug duplicateOf) {
		currentPage = ((EditBugPage) currentPage).markAsDuplicate(duplicateOf.getId());
	}
	/*#end*/

	public void markAsDuplicateWithComment(Bug duplicateOf) {
		((EditBugPage) currentPage).clickMarkAsDuplicate();
		((EditBugPage) currentPage).setBugDuplicateOf(duplicateOf.getId());
		((EditBugPage) currentPage).setComment("Marked as duplicate of " + duplicateOf.getId());
		currentPage = ((EditBugPage) currentPage).commitBug();
	}

	public void markAsDuplicateWithCircle(Bug duplicateOf) {
		((EditBugPage) currentPage).clickMarkAsDuplicate();
		/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution || $CommentOnDuplicate)*/
		((EditBugPage) currentPage).setComment("Marked as duplicate of " + duplicateOf.getId());
		/*#end*/
		((EditBugPage) currentPage).setBugDuplicateOf(duplicateOf.getId());
		currentPage = ((EditBugPage) currentPage).commitBugWithCircularDuplicates();
	}

	public void goBackToEdit() {
		currentPage = ((BugChangedPage) currentPage).gotoChangedBugPage();
	}

	public void gotoSearch() {
		currentPage = currentPage.gotoSearchBasePage();
	}

	public void gotoAdvancedSearch() {
		currentPage = ((SearchBasePage) currentPage).gotoAdvancedSearchPage();
	}

	public void gotoSimpleSearch() {
		currentPage = ((SearchBasePage) currentPage).gotoSpecificSearchPage();
	}

	public int searchAdvanced(Query query, Collection<Bug> bugs, Bug searchBug) {
		currentPage = ((SearchAdvancedPage) currentPage).search(query);
		try {
			assertTrue(resultPageContainsAllBugs(query, bugs));
			assertTrue(resultPageContainsBug(searchBug));
			assertEquals(getNumberOfBugsFittingQuery(bugs, query), ((SearchResultsPage) currentPage).getAmountOfBugs());
		} catch (AssertionError e){
			System.err.println("searchAdvanced did not produce the expected result");
			e.printStackTrace();
		}
		return ((SearchResultsPage) currentPage).getAmountOfBugs();
	}

	public int searchSimple(Query query, Collection<Bug> bugs, Bug searchBug) {
		currentPage = ((SearchSpecificPage) currentPage).searchFor(query);
		try {
			assertTrue(resultPageContainsAllBugs(query, bugs));
			assertTrue(resultPageContainsBug(searchBug));
			assertEquals(getNumberOfBugsFittingQuery(bugs, query), ((SearchResultsPage) currentPage).getAmountOfBugs());
		} catch (AssertionError e){
			System.err.println("searchSimple did not produce the expected result");
			e.printStackTrace();
		}
		return ((SearchResultsPage) currentPage).getAmountOfBugs();
	}

	public void quickSearch(Query query, Bug bug) {
		currentPage = currentPage.findBug(query.getQuery());
		assertEquals(bug.getSummary(), ((EditBugPage) currentPage).getSummary());
		assertEquals(bug.getHardware(), ((EditBugPage) currentPage).getHardware());
		assertEquals(bug.getOs(), ((EditBugPage) currentPage).getOpSys());
	}

	private boolean resultPageContainsAllBugs(Query query, Collection<Bug> bugs) {
		return bugs.stream().filter(bug -> bug.fitsQuery(query))
				.allMatch(bug -> ((SearchResultsPage) currentPage).resultContainsBug(bug.getId()));
	}

	private boolean resultPageContainsBug(Bug bug) {
		return ((SearchResultsPage) currentPage).resultContainsBug(bug.getId());
	}

	private long getNumberOfBugsFittingQuery(Collection<Bug> bugs, Query query) {
		return bugs.stream().filter(bug -> bug.fitsQuery(query)).count();
	}

	public void openBug(Bug bug) {
		currentPage = ((SearchResultsPage) currentPage).openBugWithId(bug.getId());
		assertEquals(bug.getSummary(), ((EditBugPage) currentPage).getSummary());
		assertEquals(bug.getHardware(), ((EditBugPage) currentPage).getHardware());
		assertEquals(bug.getOs(), ((EditBugPage) currentPage).getOpSys());
	}

	/**
	 * ----- CONFIGURATION -----
	 */

	public ConfigGeneralSettingsPage gotoConfigGeneralSettingsPage() {
		currentPage = currentPage.gotoConfigGeneralSettingsPage();
		return (ConfigGeneralSettingsPage) currentPage;
	}

	public ConfigBugFieldsPage gotoConfigBugFieldsPage(){
		currentPage = currentPage.gotoConfigBugFieldsPage();
		return (ConfigBugFieldsPage) currentPage;
	}

	/*#if ($UseStatusWhiteboard)*/
	public void configStatusWhiteboard(){
		currentPage = ((ConfigBugFieldsPage)currentPage).setUseStatusWhiteboard(true);
	}

	public void resetStatusWhiteboard(){
		currentPage = ((ConfigBugFieldsPage)currentPage).setUseStatusWhiteboard(false);
	}
	/*#end*/

	public ConfigBugChangePoliciesPage gotoConfigBugChangePoliciesPage(){
		currentPage = currentPage.gotoConfigBugChangePoliciesPage();
		return (ConfigBugChangePoliciesPage) currentPage;
	}

	/*#if ($Letsubmitterchoosepriority)*/
	public void configLetSubmitterChoosePriority(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setLetSubmitterChoosePriority(true);
	}

	public void resetLetSubmitterChoosePriority(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setLetSubmitterChoosePriority(false);
	}
	/*#end*/

	/*#if ($CommentOnChangeResolution)*/
	public void configCommentOnChangeResolution(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setCommentOnChangeResolution(true);
	}

	public void resetCommentOnChangeResolution(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setCommentOnChangeResolution(false);
	}
	/*#end*/

	/*#if ($CommentOnDuplicate)*/
	public void configCommentOnDuplicate(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setCommentOnDuplicate(true);
	}

	public void resetCommentOnDuplicate(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setCommentOnDuplicate(false);
	}
	/*#end*/

	/*#if ($NoResolveOnOpenBlockers)*/
	public void configNoResolveOnOpenBlockers(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setNoResolveOnOpenBlockers(true);
	}

	public void resetNoResolveOnOpenBlockers(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setNoResolveOnOpenBlockers(false);
	}
	/*#end*/

	/*#if ($DuplicateOrMoveBugStatusVerified)*/
	public void configDuplicateOrMoveBugStatusVerified(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setDuplicateOrMoveBugStatus("VERIFIED");
	}

	public void resetDuplicateOrMoveBugStatusVerified(){
		currentPage = ((ConfigBugChangePoliciesPage)currentPage).setDuplicateOrMoveBugStatus("RESOLVED");
	}
	/*#end*/

	/*#if ($AddProduct)*/
	public AddProductPage gotoAddProductPage(){
		currentPage = currentPage.gotoAddProductPage();
		return (AddProductPage) currentPage;
	}

	public void addAProduct(Product product, String component) {
		((AddProductPage)currentPage).addProduct(product.getName(), product.getDescription());
		((AddProductPage)currentPage).addComponent(component, component.toLowerCase(), username);
	}

	public void selectProduct(Product product){
		currentPage = ((CreateBugSelectProductPage)currentPage).setProduct(product.getName());
	}

	/**
	 * NOTE: For this to work you need to set a configuration option.
	 * On page /editparams.cgi?section=admin set "allowbugdeletion" to "On".
	 * So that you can delete a product and all its related bugs, other wise a product with bugs can not be deleted.
	 * @param product
	 */
	public void deleteProduct(Product product) {
		currentPage = currentPage.gotoDeleteProductPage(product.getName());
		((DeleteProductPage)currentPage).commitDeleteBug();
	}
	/*#end*/

	/*#if ($AddComponent)*/
	public AddComponentPage gotoAddComponentPage(Product product){
		currentPage = currentPage.gotoAddComponentPage(product.getName());
		return (AddComponentPage) currentPage;
	}

	public void addAComponent(Product product, String component) {
		product.addComponent(component);
		((AddComponentPage)currentPage).addComponent(component, component.toLowerCase(), username);
	}

	public void deleteComponent(Product product, String componentName) {
		currentPage = currentPage.gotoDeleteComponentPage(product.getName(), componentName);
		((DeleteComponentPage)currentPage).commitDeleteComponent();
		product.removeComponent(componentName);
	}
	/*#end*/

	/*#if ($AddVersion)*/
	public AddVersionPage gotoAddVersionPage(Product product) {
		currentPage = currentPage.gotoAddVersionPage(product.getName());
		return (AddVersionPage) currentPage;
	}

	public void addAVersion(Product product, String version) {
		product.addVersion(version);
		((AddVersionPage)currentPage).addVersion(version);
	}

	public void deleteVersion(Product product, String version) {
		currentPage = currentPage.gotoDeleteVersionPage(product.getName(), version);
		//edit all Bugs of this version, to link them to different version
		//otherwise the version can not be deleted
		while(((DeleteVersionPage)currentPage).getNumberOfBugs() > 0) {
			currentPage = ((DeleteVersionPage) currentPage).gotoBugs();
			List<Integer> ids = ((SearchResultsPage) currentPage).getBugIDs();
			for (Integer id : ids) {
				gotoBugEdit(id);
				Bug bug = product.getBug(id);
				if (bug != null) {
					editVersion(product.getRandomVersion(version), bug);
				}
			}
			//go back to DeleteVersionPage
			currentPage = currentPage.gotoDeleteVersionPage(product.getName(), version);
		}
		((DeleteVersionPage)currentPage).deleteVersion();
		product.removeVersion(version);
	}
	/*#end*/

	public ConfigWorkflowPage gotoConfigWorkflowPage() {
		currentPage = currentPage.gotoConfigWorkflowPage();
		return (ConfigWorkflowPage) currentPage;
	}

	/*#if ($SimpleBugWorkflow)*/
	public void configSimplebugworkflow(){
		currentPage = ((ConfigWorkflowPage)currentPage).setupSimpleBugWorkflow();
	}

	public void resetSimplebugworkflow(){
		currentPage = ((ConfigWorkflowPage)currentPage).setupSimpleBugWorkflow();
	}
	/*#end*/

	/*#if ($UnconfirmedState)*/
	public void configUnconfirmedstatebugworkflow(Product product){
		currentPage = ((ConfigWorkflowPage)currentPage).setupUnconfirmedstateBugWorkflow();
		currentPage = currentPage.gotoConfigProductPage(product.getName());
		((ConfigProductPage)currentPage).changeAllowUnconfirmed();
	}

	public void resetUnconfirmedstatebugworkflow(Product product){
		currentPage = ((ConfigWorkflowPage)currentPage).setupUnconfirmedstateBugWorkflow();
		currentPage = currentPage.gotoConfigProductPage(product.getName());
		((ConfigProductPage)currentPage).changeAllowUnconfirmed();
	}
	/*#end*/

	public ConfigRequiredCommentsPage gotoConfigRequiredCommentsPage() {
		currentPage = currentPage.gotoConfigRequiredCommentsPage();
		return (ConfigRequiredCommentsPage) currentPage;
	}

	/*#if ($CommentOnBugCreation)*/
	public void configCommentonBugcreation() {
		currentPage = ((ConfigRequiredCommentsPage)currentPage).updateCommentsRequiredOnCreation();
	}

	public void resetCommentonBugcreation() {
		currentPage = ((ConfigRequiredCommentsPage)currentPage).updateCommentsRequiredOnCreation();
	}
	/*#end*/

	/*#if ($CommentOnAllTransitions)*/
	public void configCommentonAlltransitions() {
		currentPage = ((ConfigRequiredCommentsPage)currentPage).updateCommentsRequiredOnStatusTransitions();
	}

	public void resetCommentonAlltransitions() {
		currentPage = ((ConfigRequiredCommentsPage)currentPage).updateCommentsRequiredOnStatusTransitions();
	}
	/*#end*/
}
