package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductUpdatedPage extends AbstractLoggedinBugzillaPage{

	public ProductUpdatedPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Updating Product')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().matches("Updating Product .*");
	}

}
