package bugzilla.teclo.pageobjects;

import bugzilla.teclo.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class EditBugPage extends AbstractLoggedinBugzillaPage {

	@FindBy(id = "title")
	private WebElement bugTitle;

	@FindBy(id = "short_desc_nonedit_display")
	private WebElement bugSummary;
	
	@FindBy(id = "summary_edit_action")
	private WebElement editBugSummaryLink;

	@FindBy(id = "product")
	private WebElement bugProduct;

	@FindBy(id = "component")
	private WebElement bugComponent;

	/*#if ($AddVersion)*/
	@FindBy(id = "version")
	private WebElement version;
	/*#else*/
	@FindBy(xpath = "//tr[@id='field_tablerow_version']/td")
	private WebElement bugVersion;
	/*#end*/

	@FindBy(id = "rep_platform")
	private WebElement bugPlatform;
	
	@FindBy(id = "op_sys")
	private WebElement bugOpSys;
	
	@FindBy(id = "priority")
	private WebElement bugPriority;
	
	@FindBy(id = "bug_severity")
	private WebElement bugSeverity;
	
	@FindBy(id = "bug_file_loc")
	private WebElement bugUrl;
	
	@FindBy(id = "bz_url_edit_action")
	private WebElement bugEditUrlLink;

	@FindBy(id = "dependson")
	private WebElement bugDependsOn;
	
	@FindBy(id = "dependson_edit_action")
	private WebElement bugEditDependsOnLink;	
	
	@FindBy(id = "blocked")
	private WebElement bugBlocked;
	
	@FindBy(id = "blocked_edit_action")
	private WebElement bugEditBlockedLink;

	@FindBy(id = "estimated_time")
	private WebElement timeEstimated;

	@FindBy(xpath = "//table[@id='bz_big_form_parts']//table[1]/tbody/tr[2]/td[2]")
	private WebElement timeCurrentEstimation;

	@FindBy(xpath = "//*[@id=\"bz_big_form_parts\"]/tbody/tr/td[1]/table[1]/tbody/tr[2]/td[3]")
	private WebElement timeWorkTimeCompleted;
	
	@FindBy(id = "work_time")
	private WebElement timeWork;

	@FindBy(id = "remaining_time")
	private WebElement timeRemaining;
	
	@FindBy(xpath = "//*[@id=\"bz_big_form_parts\"]/tbody/tr/td[1]/table[1]/tbody/tr[2]/td[5]")
	private WebElement timeCompletedInPercent;
	
	@FindBy(xpath = "//*[@id=\"bz_big_form_parts\"]/tbody/tr/td[1]/table[1]/tbody/tr[2]/td[6]")
	private WebElement timeGain;

	@FindBy(id = "deadline")
	private WebElement timeDeadline;

	
	@FindBy(id = "comment")
	private WebElement bugComment;
	
	@FindBy(id = "bug_status")
	private WebElement bugStatus;
	
	@FindBy(id = "resolution")
	private WebElement bugResolution;
	
	@FindBy(id = "dup_id_discoverable_action")
	private WebElement markAsDuplicateLink;
	
	@FindBy(id = "dup_id")
	private WebElement bugDuplicateId;

	
	@FindBy(id = "commit")
	private WebElement commitButton;

	
	@FindBy(id = "comment_text_0")
	private WebElement bugFirstComment;

	@FindBy(id = "status_whiteboard")
	private WebElement bugStatusWhiteboard;

	
	public EditBugPage(WebDriver driver) {
		super(driver, By.id("commit_top"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().matches("[0-9]* – [a-zA-Z0-9_]*.*");
	}
	
	
	public String getSummary() {
		return bugSummary.getText();
	}
	
	public void setSummary(String summary) {
		editBugSummaryLink.click();
		WebElement bugSummaryEdit = driver.findElement(By.id("short_desc")); 
		bugSummaryEdit.clear();
		bugSummaryEdit.sendKeys(summary);
	}

	
	public String getProduct() {
		return Helper.getSelectedOptionValue(bugProduct);
	}
	
	public void setProduct(String product) {
		new Select(bugProduct).selectByVisibleText(product);
	}
	
	public String getComponent() {
		return Helper.getSelectedOptionValue(bugComponent);
	}

	public void setComponent(String component){
		new Select(bugComponent).selectByVisibleText(component);
	}

	public String getVersion() {
		/*#if ($AddVersion)*/
		return Helper.getSelectedOptionValue(version);
		/*#else*/
		return bugVersion.getText();
		/*#end*/
	}

	/*#if ($AddVersion)*/
	public void setVersion(String testVersion){
		new Select(version).selectByVisibleText(testVersion);
	}
	/*#end*/

	public String getPlatform() {
		return Helper.getSelectedOptionValue(bugPlatform);
	}
	
	public void setPlatform(String platform) {
		new Select(bugPlatform).selectByVisibleText(platform);
	}

	public String getOpSys() {
		return Helper.getSelectedOptionValue(bugOpSys);
	}

	public void setOpSys(String opSys) {
		new Select(bugOpSys).selectByVisibleText(opSys);
	}

	public String getPriority() {
		return Helper.getSelectedOptionValue(bugPriority);
	}

	public void setPriority(String priority) {
		new Select(bugPriority).selectByVisibleText(priority);
	}

	public String getSeverity() {
		return Helper.getSelectedOptionValue(bugSeverity);
	}
	
	public void setSeverity(String severity) {
		new Select(bugSeverity).selectByVisibleText(severity);
	}

	public int getBugId() {
		return Integer.parseInt(bugTitle.getText().replaceAll("[^0-9]", ""));
	}
	
	public String getUrl() {
		return bugUrl.getAttribute("value");
	}
	
	public void setUrl(String url) {
		if (!bugUrl.isDisplayed()) {
			bugEditUrlLink.click();
		}
		bugUrl.clear();
		bugUrl.sendKeys(url);
	}

	
	public String getDependsOn() {
		return bugDependsOn.getAttribute("value");		
	}
	
	public void setDependsOn(int bugId) {
		if (!bugDependsOn.isDisplayed()) {
			bugEditDependsOnLink.click();
		}		
		bugDependsOn.clear();
		bugDependsOn.sendKeys(String.valueOf(bugId));
	}
	
	public String getBlocks() {
		return bugBlocked.getAttribute("value");
	}
	
	public void setBlocks(String bugId) {
		if (!bugBlocked.isDisplayed()) {
			bugEditBlockedLink.click();
		}		
		bugBlocked.clear();
		bugBlocked.sendKeys(String.valueOf(bugId));		
	}

	
	public String getTimeEstimated() {
		return timeEstimated.getAttribute("value");
	}
	
	public void setTimeEstimated(double estimatedTime) {
		timeEstimated.clear();
		timeEstimated.sendKeys(String.valueOf(estimatedTime));
	}
	
	public String getTimeCurrentEstimation() {
		return timeCurrentEstimation.getText();
	}

	public String getTimeWorkCompleted() {
		return timeWorkTimeCompleted.getText();
	}
	
	public String getTimeWorked() {
		return timeWork.getAttribute("value");
	}
	
	public void setTimeWorked(double workTime) {
		timeWork.clear();
		timeWork.sendKeys(String.valueOf(workTime));
	}

	public String getTimeHoursLeft() {
		return timeRemaining.getAttribute("value");
	}
	
	public void setTimeHoursLeft(double remainingTime) {
		timeRemaining.clear();
		timeRemaining.sendKeys(String.valueOf(remainingTime));
	}
	
	public String getTimeCompletedInPercent() {
		return timeCompletedInPercent.getText();
	}
	
	public String getTimeGain() {
		return timeGain.getText();
	}

	public String getTimeDeadline() {
		return timeDeadline.getAttribute("value");
	}	

	public void setTimeDeadline(String deadline) {
		timeDeadline.clear();
		timeDeadline.sendKeys(String.valueOf(deadline));
	}


	public String getComment() {
		return bugComment.getText();
	}
	
	public void setComment(String comment) {
		bugComment.clear();
		bugComment.sendKeys(comment);
	}

	public String getStatus() {
		return (new Select(bugStatus)).getFirstSelectedOption().getText();
	}
	
	public String getBugStatus() {
		try {
			return Helper.getSelectedOptionValue(bugStatus);
		} catch (NoSuchElementException e) {
			return driver.findElement(By.id("status")).getText().split("\\n")[0].trim();
		}
	}
	
	public void setBugStatus(String bugStatusName) {
		new Select(bugStatus).selectByVisibleText(bugStatusName);
	}

	public AbstractBugzillaPage editState(String status){
		setBugStatus(status);
		commitButton.click();
		/*#if ($CommentOnAllTransitions)*/
		return PageFactory.initElements(driver, ErrorCommentRequiredPage.class);
		/*#else*/
		/*#if ($CommentOnChangeResolution)*/
		if(status.equals("RESOLVED") || status.equals("VERIFIED")){
			return PageFactory.initElements(driver, ErrorCommentRequiredPage.class);
		}
		/*#end*/
		return PageFactory.initElements(driver, BugChangedPage.class);
		/*#end*/
	}

	public AbstractBugzillaPage editResolution(String resolution){
		setBugResolution(resolution);
		commitButton.click();
		/*#if ($CommentOnChangeResolution)*/
		return PageFactory.initElements(driver, ErrorCommentRequiredPage.class);
		/*#else*/
		return PageFactory.initElements(driver, BugChangedPage.class);
		/*#end*/
	}

	/*#if ($CommentOnAllTransitions || $CommentOnChangeResolution || $CommentOnDuplicate)*/
	public ErrorCommentRequiredPage markAsDuplicateWithoutRequiredComment(int originalId){
		clickMarkAsDuplicate();
		setBugDuplicateOf(originalId);
		commitButton.click();
		return PageFactory.initElements(driver, ErrorCommentRequiredPage.class);
	}
	/*#else*/
	public BugChangedPage markAsDuplicate(int originalId){
		clickMarkAsDuplicate();
		setBugDuplicateOf(originalId);
		commitButton.click();
		return PageFactory.initElements(driver, BugChangedPage.class);
	}


	/*#end*/

	public String getBugResolution() {
		return Helper.getSelectedOptionValue(bugResolution);
	}
	
	public void setBugResolution(String bugResolutionName) {
		new Select(bugResolution).selectByVisibleText(bugResolutionName);
	}

	public String getBugDuplicateOf(){
		return bugDuplicateId.getAttribute("value");
	}
	
	public void setBugDuplicateOf(int bugId){
		bugDuplicateId.clear();
		bugDuplicateId.sendKeys(String.valueOf(bugId));
	}

	public void clickMarkAsDuplicate(){
			markAsDuplicateLink.click();
	}

	
	public BugChangedPage commitBug() {
		commitButton.click();
		return PageFactory.initElements(driver, BugChangedPage.class);
	}

	public VerifyVersionComponentPage commitChangedProduct() {
		commitButton.click();
		return PageFactory.initElements(driver, VerifyVersionComponentPage.class);
	}

	public ErrorCircularDependencyPage commitBugWithCircularDependency() {
		commitButton.click();
		return PageFactory.initElements(driver, ErrorCircularDependencyPage.class);
	}

	public ErrorCircularDuplicatesPage commitBugWithCircularDuplicates() {
		commitButton.click();
		return PageFactory.initElements(driver, ErrorCircularDuplicatesPage.class);
	}

	public ErrorUnresolvedDependencyPage commitBugWithUnresolvedDependency() {
		commitButton.click();
		return PageFactory.initElements(driver, ErrorUnresolvedDependencyPage.class);
	}

	public ErrorSummaryNeededPage commitBugWithEmptySummary() {
		commitButton.click();
		return PageFactory.initElements(driver, ErrorSummaryNeededPage.class);
	}

	public ErrorCommentRequiredPage commitBugWithEmptyComment() {
		commitButton.click();
		return PageFactory.initElements(driver, ErrorCommentRequiredPage.class);
	}
	
	public String getFirstComment(){
		return bugFirstComment.getText().replace("\n", " ");
	}
	
	public int getNumberOfComments(){
		//one bz_comment has to be subtracted because of preview 
		return driver.findElements(By.xpath("//div[contains(concat(' ', @class, ' '), ' bz_comment ')]")).size() - 1;	
	}
	
	public String getLastComment(){
		int lastCommentId = getNumberOfComments() - 1;
		return driver.findElement(By.id("comment_text_"+lastCommentId)).getText().replace("\n", " ");
	}

	public String getHardware() {
		return Helper.getSelectedOptionValue(bugPlatform);
	}

	public String getStatusWhiteboard(){
		return bugStatusWhiteboard.getAttribute("value");
	}

	public void setStatusWhiteboard(String statusMessage){
		bugStatusWhiteboard.clear();
		bugStatusWhiteboard.sendKeys(statusMessage);
	}
}
