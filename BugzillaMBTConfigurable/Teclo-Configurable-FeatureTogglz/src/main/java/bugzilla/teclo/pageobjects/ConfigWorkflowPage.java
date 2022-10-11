package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfigWorkflowPage extends AbstractLoggedinBugzillaPage {

	//changes since 3.4
	//new -> confirmed
	//assigned -> inProgress
	//closed removed
	//reopened removed
	//default configuration is different, confirmed_resolved and confirmed_verified are clicked by default

	// column 1
	@FindBy(id = "w_0_1")
	private WebElement start_unconfirmed;
	@FindBy(id = "w_4_1")
	private WebElement resolved_unconfirmed;
	@FindBy(id = "w_5_1")
	private WebElement verified_unconfirmed;

	//column 2
	@FindBy(id = "w_1_2")
	private WebElement confirmed_unconfirmed;
	@FindBy(id = "w_3_2")
	private WebElement confirmed_inProgress;
	@FindBy(id = "w_5_2")
	private WebElement confirmed_verified;

	//column 3
	@FindBy(id = "w_0_3")
	private WebElement inProgress_start;
	@FindBy(id = "w_1_3")
	private WebElement inProgress_unconfirmed;
	@FindBy(id = "w_2_3")
	private WebElement inProgress_confirmed;
//	@FindBy(id = "w_4_3")
//	private WebElement assigned_reopened;
	
	//column 4
//	@FindBy(id = "w_5_4")
//	private WebElement reopened_resolved;
//	@FindBy(id = "w_6_4")
//	private WebElement reopened_verified;
//	@FindBy(id = "w_7_4")
//	private WebElement reopened_closed;
	
	//column 6
	@FindBy(id = "w_4_5")
	private WebElement verified_resolved;
	
	//column 7
//	@FindBy(id = "w_6_7")
//	private WebElement closed_verified;

	//update_workflow
	@FindBy(id = "update_workflow")
	private WebElement update_workflow;

	public ConfigWorkflowPage(WebDriver driver) {
		super(driver, By.id("update_workflow"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Edit Workflow".equals(getTitle());
	}

	
	public ConfigWorkflowPage setupSimpleBugWorkflow() {
		start_unconfirmed.click();
		resolved_unconfirmed.click();
		verified_unconfirmed.click();

		confirmed_unconfirmed.click();
		confirmed_inProgress.click();
		confirmed_verified.click();

		inProgress_start.click();
		inProgress_unconfirmed.click();
		inProgress_confirmed.click();

		verified_resolved.click();

		//commit
		update_workflow.click();
		
		return PageFactory.initElements(driver, ConfigWorkflowPage.class);
	}

	public ConfigWorkflowPage setupUnconfirmedstateBugWorkflow() {

		inProgress_start.click();

		//commit
		update_workflow.click();

		return PageFactory.initElements(driver, ConfigWorkflowPage.class);
	}
}
