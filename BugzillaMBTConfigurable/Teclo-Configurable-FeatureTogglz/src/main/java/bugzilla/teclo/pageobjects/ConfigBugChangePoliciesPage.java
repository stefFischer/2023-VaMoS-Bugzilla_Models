package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ConfigBugChangePoliciesPage extends AbstractBugzillaPage {
	
	@FindBy(id = "letsubmitterchoosepriority-on")
	private WebElement letsubmitterchoosepriorityOnRadiobutton;

	@FindBy(id = "letsubmitterchoosepriority-off")
	private WebElement letsubmitterchoosepriorityOffRadiobutton;

	@FindBy(id = "commentonchange_resolution-on")
	private WebElement commentOnChangeResolutionOnRadiobutton;

	@FindBy(id = "commentonchange_resolution-off")
	private WebElement commentOnChangeResolutionOffRadiobutton;

	@FindBy(id = "commentonduplicate-on")
	private WebElement commentOnDuplicateOnRadiobutton;

	@FindBy(id = "commentonduplicate-off")
	private WebElement commentOnDuplicateOffRadiobutton;

	@FindBy(id = "resolution_forbidden_with_open_blockers")
	private WebElement bugResolution;

	@FindBy(id = "duplicate_or_move_bug_status")
	private WebElement duplicateOrMoveBugStatus;

	@FindBy(id = "save-params")
	private WebElement saveChangesButton;
	
	public ConfigBugChangePoliciesPage(WebDriver driver){
		super(driver, By.id("save-params"));
	}

	@Override
	protected boolean isMatchingPage() {
		return ("Configuration: Bug Change Policies".equals(getTitle()) 
				|| "Parameters Updated".equals(getTitle())); 
	}
	
	public ConfigBugChangePoliciesPage setLetSubmitterChoosePriority(boolean letsubmitterchoosepriority){
		if (letsubmitterchoosepriority) {
			letsubmitterchoosepriorityOnRadiobutton.click();
		} else {
			letsubmitterchoosepriorityOffRadiobutton.click();
		}
		saveChangesButton.click();		
		return PageFactory.initElements(driver, ConfigBugChangePoliciesPage.class);
	}

	public ConfigBugChangePoliciesPage setCommentOnChangeResolution(boolean commentOnChangeResolution){
		if (commentOnChangeResolution) {
			commentOnChangeResolutionOnRadiobutton.click();
		} else {
			commentOnChangeResolutionOffRadiobutton.click();
		}
		saveChangesButton.click();
		return PageFactory.initElements(driver, ConfigBugChangePoliciesPage.class);
	}

	public ConfigBugChangePoliciesPage setCommentOnDuplicate(boolean commentOnDuplicate){
		if (commentOnDuplicate) {
			commentOnDuplicateOnRadiobutton.click();
		} else {
			commentOnDuplicateOffRadiobutton.click();
		}
		saveChangesButton.click();
		return PageFactory.initElements(driver, ConfigBugChangePoliciesPage.class);
	}

	public ConfigBugChangePoliciesPage setNoResolveOnOpenBlockers(boolean noResoloveonOpenBlockers){
		if (noResoloveonOpenBlockers) {
			setBugResolution("FIXED");
		} else {
			setBugResolution("");
		}
		saveChangesButton.click();
		return PageFactory.initElements(driver, ConfigBugChangePoliciesPage.class);
	}

	public void setBugResolution(String bugResolutionName) {
		new Select(bugResolution).selectByVisibleText(bugResolutionName);
	}

	public ConfigBugChangePoliciesPage setDuplicateOrMoveBugStatus(String bugStatusName) {
		new Select(duplicateOrMoveBugStatus).selectByVisibleText(bugStatusName);

		saveChangesButton.click();
		return PageFactory.initElements(driver, ConfigBugChangePoliciesPage.class);
	}
}
