package at.scch.teclo.pageobjects;

import at.scch.mbteclo.state.Bug;
import at.scch.teclo.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Collection;
import java.util.NoSuchElementException;

public class CreateBugPage extends AbstractLoggedinBugzillaPage implements PageWithProduct{
	
	@FindBy(id = "expert_fields_controller")
	private WebElement linkToggleAdvancedFields;
	
	@FindBy(id = "field_container_product")
	private WebElement product;
	
	@FindBy(id = "component")
	private WebElement component;
	
	@FindBy(id = "version")
	private WebElement version;
	
	@FindBy(name = "short_desc")
	private WebElement bugSummary;
	
	@FindBy(id = "comment")
	private WebElement bugComment;

	/**
	 * TODO priority can not be submitted in C02
	 */
	@FindBy(id = "priority")
	private WebElement bugPriority;

	@FindBy(id = "bug_severity")
	private WebElement bugSeverity;
	
	@FindBy(id = "rep_platform")
	private WebElement bugPlatform;

	@FindBy(id = "op_sys")
	private WebElement bugOpSys;

	@FindBy(id = "commit")
	private WebElement commitButton;
	
	private String alertText = null;
	
	public CreateBugPage(WebDriver driver) {
		this(driver, By.xpath("//span[@id='title' and contains(text(), 'Enter Bug')]"));
	}
	
	public CreateBugPage(WebDriver driver, By by) {
		super(driver, by);
	}
	
	@Override
	protected boolean isMatchingPage() {
		return getTitle().matches("Enter Bug: .*");
	}
	
	protected void clickLinkToggleAdvancedFields() {
		linkToggleAdvancedFields.click();
	}
	
	public String getProduct() {
		return product.getText();
	}
	
	public CreateBugAdvancedPage gotoAdvanced(){
		if(!areAdvancedFieldsPresent()) {
			linkToggleAdvancedFields.click();
		}
		return PageFactory.initElements(driver, CreateBugAdvancedPage.class);
	}
	
	public boolean areAdvancedFieldsPresent() {
		return Helper.isElementPresent(driver, By.xpath("//*[@id = 'expert_fields_controller' and contains(text(), 'Hide')]"));
	}

	public void setSummary(String summary) {
		bugSummary.clear();
		bugSummary.sendKeys(summary);
	}

	public void setHardware(String hardware) {
		Select hardw = new Select(this.bugPlatform);
		hardw.selectByVisibleText(hardware);
	}

	public void setPlatform(String platform) {
		new Select(bugPlatform).selectByVisibleText(platform);
	}

	public void setOpSys(String opSys) {
		new Select(bugOpSys).selectByVisibleText(opSys);
	}

	public void setSeverity(String severity) {
		new Select(bugSeverity).selectByVisibleText(severity);
	}

	/**
	 * TODO priority can not be submitted in C02
	 */
	public void setPriority(String priority) {
		new Select(bugPriority).selectByVisibleText(priority);
	}

	public void setComment(String comment) {
		bugComment.clear();
		bugComment.sendKeys(comment);
	}

	public void selectComponent(String componentName){
		new Select(component).selectByVisibleText(componentName);
	}

	public void selectVersion(String versionName){
		new Select(version).selectByVisibleText(versionName);
	}

	public EditBugPage commitBug() {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", commitButton);
		return PageFactory.initElements(driver, EditBugPage.class);
	}
	
	public boolean commitBugWithEmptySummary() {
		bugSummary.clear();
		commitButton.click();
		
		By by = By.xpath("//*[@id=\"Create\"]/table/tbody[5]/tr[1]/td/div");
		boolean isPresent = Helper.isElementPresent(driver, by);
		if(isPresent) {
			alertText = driver.findElement(by).getText();
		}
		
		return !isPresent;
	}

	/*#if ($CommentOnBugCreation)*/
	public boolean commitBugWithoutComment() {
		bugComment.clear();
		commitButton.click();

		By by = By.xpath("//*[@id=\"Create\"]/table/tbody[5]/tr[3]/td/div[2]");
		boolean isPresent = Helper.isElementPresent(driver, by);
		if(isPresent) {
			alertText = driver.findElement(by).getText();
		}

		return !isPresent;
	}
	/*#end*/

	public String getAlertText() {
		return alertText;
	}
	
	public EditBugPage createNewBug(String summary, String description) {
		setSummary(summary);
		setComment(description);

		/*#if ($AddComponent)*/
		selectComponent("TestComponent");
		/*#end*/

		return commitBug();
	}

	public AbstractBugzillaPage fileBasic(Bug bug, Collection<Bug> bugsUpToNow) {
		setSeverity(bug.getSeverity());
		setSummary(bug.getSummary());
		setHardware(bug.getHardware());
		setOpSys(bug.getOs());
		selectComponent(bug.getComponent());
		selectVersion(bug.getVersion());
		setComment(bug.getDescription());
		clickCommitButton();
		if(bug.isAValidBug(bugsUpToNow)) {
			return PageFactory.initElements(driver, EditBugPage.class);
		} else {
			/*#if ($CommentOnBugCreation || $CommentOnAllTransitions)*/
			if(bug.getDescription().isEmpty()){
				try{
					return PageFactory.initElements(driver, ErrorCommentRequiredPage.class);
				} catch (Exception e){
					return PageFactory.initElements(driver, ErrorCommentRequiredOnBugCreationPage.class);
				}
			}
			/*#end*/
			return PageFactory.initElements(driver, ErrorCreateBugPage.class);
		}
	}

	public void clickCommitButton() {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", commitButton);
	}

	public void clearSeeAlso() {
		if(areAdvancedFieldsPresent()) {
			(PageFactory.initElements(driver, CreateBugAdvancedPage.class)).setSeeAlso("");
		} else {
			clickLinkToggleAdvancedFields();
			(PageFactory.initElements(driver, CreateBugAdvancedPage.class)).setSeeAlso("");
			clickLinkToggleAdvancedFields();
		}
	}

	@Override
	public String getProductName() {
		return driver.findElement(By.id("field_container_product")).getText();
	}
}