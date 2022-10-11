package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchSavedPage extends AbstractLoggedinBugzillaPage{

	public SearchSavedPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Search created')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().equals("Search created");
	}

}
