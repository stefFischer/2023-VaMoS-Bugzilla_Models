package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StartPage extends AbstractBugzillaPage {
	
	@FindBy(id = "quicksearch_main")
	private WebElement quickSearchText;

	@FindBy(id = "find")
	private WebElement quickSearchButton;
	
	
	public StartPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Main Page')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		String expectedPageTitleRegex = ".*"; 
		
		return driver.getTitle().matches(expectedPageTitleRegex);
	}
	

	public SearchResultsPage searchFor(String searchTerm) {
		quickSearchText.clear();
		quickSearchText.sendKeys(searchTerm);
		quickSearchButton.click();

		return PageFactory.initElements(driver, SearchResultsPage.class);
	}

	public EditBugPage searchFor(int bugId) {
		quickSearchText.clear();
		quickSearchText.sendKeys(String.valueOf(bugId));
		quickSearchButton.click();

		return PageFactory.initElements(driver, EditBugPage.class);
	}
}
