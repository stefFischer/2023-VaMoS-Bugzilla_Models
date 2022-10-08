package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDeletedPage extends AbstractLoggedinBugzillaPage {

	public ProductDeletedPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id = 'title' and contains(text(), 'Product Deleted')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().equals("Product Deleted");
	}

}
