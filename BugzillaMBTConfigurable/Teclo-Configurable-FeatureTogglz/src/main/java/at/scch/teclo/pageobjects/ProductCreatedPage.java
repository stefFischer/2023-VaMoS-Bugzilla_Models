package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ProductCreatedPage extends AbstractLoggedinBugzillaPage{

	public ProductCreatedPage(WebDriver driver) {
		super(driver, By.xpath("//*[@id='message' and contains(text(), 'The product')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().equals("Product Created");
	}
	
	public ProductUpdatedPage saveChanges() {
		driver.findElement(By.id("update-product"));
		return PageFactory.initElements(driver, ProductUpdatedPage.class);
	}
}
