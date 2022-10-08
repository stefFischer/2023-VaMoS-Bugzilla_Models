package at.scch.teclo.pageobjects;

import at.scch.mbteclo.state.Query;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class SearchAdvancedPage extends SearchBasePage {

	@FindBy(id = "bug_status")
	private WebElement bugStatus;

	@FindBy(name = "version")
	private WebElement bugVersion;

	@FindBy(name = "short_desc_type")
	private WebElement summarySearchType;

	@FindBy(id = "short_desc")
	private WebElement summaryText;

	@FindBy(id = "longdesc")
	private WebElement commentText;

	@FindBy(id = "Search")
	private WebElement searchButton;

	@FindBy(name = "f1")
	private WebElement booleanChartField;

	@FindBy(name = "o1")
	private WebElement booleanChartType;

	@FindBy(name = "v1")
	private WebElement booleanChartValue;

	@FindBy(id = "resolution")
	private WebElement resolution;
	
	@FindBy(id = "component")
	private WebElement component;
	
	@FindBy(id = "product")
	private WebElement product;

	@FindBy(id = "status_whiteboard")
	private WebElement statusWhiteboardText;

	public SearchAdvancedPage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	protected boolean isMatchingPage() {
		return "Search for bugs".equals(driver.getTitle());
	}

	public SearchResultsPage searchFor(String summaryString, String commentString) {
		summaryText.clear();
		summaryText.sendKeys(summaryString);
		commentText.clear();
		commentText.sendKeys(commentString);
		return submitSearch();
	}

	public void setSummary(String summaryString) {
		summaryText.clear();
		summaryText.sendKeys(summaryString);
	}

	public void setComment(String commentString) {
		commentText.clear();
		commentText.sendKeys(commentString);
	}

	public void setSummarySearchType(String searchType) {
		new Select(summarySearchType).selectByVisibleText(searchType);
	}

	public void unsetBugStatus(String bugStatusString) {
		new Select(bugStatus).deselectByVisibleText(bugStatusString);
	}

	public void setBugStatus(String bugStatusString) {
		new Select(bugStatus).selectByVisibleText(bugStatusString);
	}

	public void setBooleanChart(String chartField, String chartType, String chartValue) {
		new Select(booleanChartField).selectByVisibleText(chartField);
		new Select(booleanChartType).selectByVisibleText(chartType);
		booleanChartValue.clear();
		booleanChartValue.sendKeys(chartValue);
	}

	public SearchResultsPage submitSearch() {
		searchButton.click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	public void setProduct(String product) {
		new Select(this.product).selectByVisibleText(product);
	}
	
	public void unsetProduct(String product) {
		new Select(this.product).deselectByVisibleText(product);
	}
	
	public void setComponent(String component){
		new Select(this.component).selectByVisibleText(component);
	}

	public void setVersion(String versionName){
		if(!bugVersion.isDisplayed()){ //make sure version select is visible
			driver.findElement(By.linkText("Detailed Bug Information")).click();
		}
		new Select(bugVersion).selectByVisibleText(versionName);
	}

	public void unsetComponent(String component) {
		new Select(this.component).deselectByVisibleText(component);
	}
	
	public void setResolution(String resolution) {
		new Select(this.resolution).selectByVisibleText(resolution);
	}

	public SearchResultsPage search(Query query) {
		setBugStatus(query.getStatus());
		setProduct(query.getProduct());
		setComponent(query.getComponent());
		setVersion(query.getVersion());
		setResolution(query.getResolution());
		setSummary(query.getQuery());
		submitSearch();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}

	public void setStatusWhiteboard(String statusMessage){
		statusWhiteboardText.clear();
		statusWhiteboardText.sendKeys(statusMessage);
	}
}
