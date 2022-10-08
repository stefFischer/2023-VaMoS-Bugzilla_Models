package at.scch.teclo.pageobjects;

import at.scch.teclo.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.LinkedList;
import java.util.List;

public class SearchResultsPage extends AbstractBugzillaPage {

	@FindBy(css = "span.bz_result_count")
	private WebElement amountOfBugs;

	@FindBy(xpath = "//table[@class='bz_buglist']/tbody/tr[2]/td[1]/a")
	private WebElement firstBugId;

	@FindBy(xpath = "//table[@class='bz_buglist']/tbody/tr[2]/td[7]/a")
	private WebElement firstBugSummary;

	@FindBy(xpath = "//table[@class='bz_buglist']//tr[2]/td[5]/span")
	private WebElement firstBugStatus;

	@FindBy(id = "save_newqueryname")
	private WebElement saveSearchField;

	@FindBy(id = "remember")
	private WebElement saveSearchButton;

	@FindBy(linkText = "Home")
	private WebElement homeLink;

	public SearchResultsPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and (contains(text(), 'Bug List') or contains(text(), 'Search created') or contains(text(), 'Search is gone'))]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().matches("Bug List.*|Search created|Search is gone");
	}
	
	
	public EditBugPage gotoEditBugPage(int currentBugId) {
		driver.findElement(By.linkText(String.valueOf(currentBugId))).click();
		return PageFactory.initElements(driver, EditBugPage.class);
	}

	public int getAmountOfBugs() {
		// Special Case if 0 or 1 bug is found
		if (amountOfBugs.getText().contentEquals("Zarro Boogs found.")) {
			return 0;
		} else if (amountOfBugs.getText().contentEquals("One bug found.")) {
			return 1;
		}

		return Integer.parseInt(amountOfBugs.getText().replaceAll("[^0-9]", ""));
	}

	public String getAmountOfBugsText() {
		return amountOfBugs.getText();
	}

	public int getIdOfFirstBug() {
		return Integer.parseInt(firstBugId.getText());
	}

	public String getSummaryOfFirstBug() {
		return firstBugSummary.getText();
	}

	public String getStatusOfFirstBug() {
		return firstBugStatus.getText();
	}

	public String getPriorityOfFirstBug() {
		EditBugPage bugPage = gotoEditBugPage(Integer.parseInt(firstBugId.getText()));
		String priority = bugPage.getPriority();
		
		driver.findElement(By.linkText("Show last search results")).click();
		
		return priority;
	}

	public SearchResultsPage rememberSavedSearch(String nameOfSearch) {
		saveSearchField.clear();
		saveSearchField.sendKeys(nameOfSearch);

		saveSearchButton.click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}

	public SearchResultsPage forgetSavedSearch(String savedSearchName) {
		performSavedSearch(savedSearchName);
		driver.findElement(By.id("forget_search")).click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}
	
	public AbstractBugzillaPage saveSearch(String name) {
		saveSearchField.clear();
		saveSearchField.sendKeys(name);
		saveSearchButton.click();
		if(name.equals("")) {
			return PageFactory.initElements(driver, ErrorSaveSearchPage.class);	
		} else {
			return PageFactory.initElements(driver, SearchSavedPage.class);
		}
		
	}
	
	public boolean resultContainsBug(int bugId) {
		return Helper.isElementPresent(driver, By.linkText(String.valueOf(bugId)));
	}

	public AbstractBugzillaPage openBugWithId(int id) {
		driver.findElement(By.linkText(String.valueOf(id))).click();
		return PageFactory.initElements(driver, EditBugPage.class);
	}

	public List<Integer> getBugIDs(){
		List<Integer> bugIDs = new LinkedList<>();
		List<WebElement> elements = driver.findElements(By.className("first-child"));
		for (WebElement element : elements) {
			if(!element.getText().equals("ID")){
				bugIDs.add(Integer.parseInt(element.getText()));
			}
		}
		return bugIDs;
	}
}
