package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchBasePage extends AbstractBugzillaPage {

	@FindBy(css = "td.selected")
	private WebElement currentActiveTab;

	@FindBy(css = "td.clickable_area")
	private WebElement clickableTab;

	public SearchBasePage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and (contains(text(), 'Simple Search') or contains(text(), 'Search for bugs'))]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().matches("Simple Search|Search for bugs");
	}

	public SearchSpecificPage gotoSpecificSearchPage() {
		if (currentActiveTab.getText().equals("Advanced Search")) {
			clickableTab.click();
		}
		return PageFactory.initElements(driver, SearchSpecificPage.class);
	}

	public SearchAdvancedPage gotoAdvancedSearchPage() {
		if (currentActiveTab.getText().equals("Simple Search")) {
			clickableTab.click();
			driver.findElement(By.linkText("Detailed Bug Information")).click();
			driver.findElement(By.linkText("Search By People")).click();
			driver.findElement(By.linkText("Search By Change History")).click();
			driver.findElement(By.id("chart")).click();
		}
		return PageFactory.initElements(driver, SearchAdvancedPage.class);
	}
}
