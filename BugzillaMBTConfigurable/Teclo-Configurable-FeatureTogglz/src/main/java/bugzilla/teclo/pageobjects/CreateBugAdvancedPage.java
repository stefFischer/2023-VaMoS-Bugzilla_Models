package bugzilla.teclo.pageobjects;

import bugzilla.mbteclo.state.Bug;
import bugzilla.mbteclo.state.ConfigurationOption;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Collection;

public class CreateBugAdvancedPage extends CreateBugPage{
	
	@FindBy(id = "maketemplate")
	private WebElement makeTemplateButton;
	
	@FindBy(id = "bug_status")
	private WebElement status;
	
	@FindBy(name = "estimated_time")
	private WebElement fieldEstimatedTime;

	@FindBy(name = "deadline")
	private WebElement timeDeadline;
	
	@FindBy(name = "bug_file_loc")
	private WebElement bugUrl;
	
	@FindBy(id = "priority")
	private WebElement bugPriority;
	
	@FindBy(id = "cc")
	private WebElement cc;
	
	@FindBy(id = "assigned_to")
	private WebElement assignedTo;
	
	@FindBy(xpath = "//input[@name='alias' and @size='20']")
	private WebElement alias;
	
	@FindBy(xpath = "//input[@name='dependson' and @accesskey='d']")
	private WebElement dependsOn;
	
	@FindBy(xpath = "//input[@name= 'blocked' and @accesskey='b']")
	private WebElement blocks;
	
	@FindBy(id = "see_also")
	private WebElement seeAlso;
	
	public CreateBugAdvancedPage(WebDriver driver) {
		super(driver, By.xpath("//*[@id = 'expert_fields_controller' and contains(text(), 'Hide')]"));
	}
	
	public TemplatePage makeTemplate() {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", makeTemplateButton);
		return PageFactory.initElements(driver, TemplatePage.class);
	}

	public void setAssignee(String assignee) {
		assignedTo.clear();
		assignedTo.sendKeys(assignee);
	}
	
	public void setBugStatus(String bugStatusName) {
		new Select(status).selectByVisibleText(bugStatusName);
	}
	
	public void setTimeEstimated(double estimatedTime) {
		fieldEstimatedTime.clear();
		fieldEstimatedTime.sendKeys(String.valueOf(estimatedTime));
	}

	public void setTimeDeadline(String deadline) {
		timeDeadline.clear();
		timeDeadline.sendKeys(String.valueOf(deadline));
	}
	
	public void setUrl(String url) {
		bugUrl.clear();
		bugUrl.sendKeys(url);
	}
	
	public void setPriority(String priority) {
		new Select(bugPriority).selectByVisibleText(priority);
	}
	
	public void setCc(String s) {
		cc.clear();
		cc.sendKeys(s);
	}
	
	public void setSeeAlso(String url) {
		seeAlso.clear();
		seeAlso.sendKeys(url);
	}

	public CreateBugPage gotoBasic(){
		clickLinkToggleAdvancedFields();
		return PageFactory.initElements(driver, CreateBugPage.class);
	}

	public AbstractBugzillaPage fillAdvanced(Bug bug, Collection<Bug> bugsUpToNow) {
		setSeverity(bug.getSeverity());
		if(ConfigurationOption.Letsubmitterchoosepriority.isActive()){
			setPriority(bug.getPriority());
		}
		setSummary(bug.getSummary());
		setHardware(bug.getHardware());
		setOpSys(bug.getOs());
		setSeeAlso(bug.getSeeAlso());
		if(!ConfigurationOption.SimpleBugWorkflow.isActive() && !ConfigurationOption.UnconfirmedState.isActive()){
			setBugStatus(bug.getStatus());
		}
		selectComponent(bug.getComponent());
		selectVersion(bug.getVersion());
		setComment(bug.getDescription());
		clickCommitButton();
		if(bug.isAValidBug(bugsUpToNow)) {
			return PageFactory.initElements(driver, EditBugPage.class);
		} else {
			if(ConfigurationOption.CommentOnBugCreation.isActive() || ConfigurationOption.CommentOnAllTransitions.isActive()) {
				if (bug.getDescription().isEmpty()) {
					try {
						return PageFactory.initElements(driver, ErrorCommentRequiredPage.class);
					} catch (Exception e) {
						return PageFactory.initElements(driver, ErrorCommentRequiredOnBugCreationPage.class);
					}
				}
			}
			return PageFactory.initElements(driver, ErrorCreateBugPage.class);
		}
	}
}
