package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfigProductPage extends AbstractBugzillaPage {

	//allows_unconfirmed
	@FindBy(id = "allows_unconfirmed")
	private WebElement allows_unconfirmed;

	//update-product
	@FindBy(id = "update-product")
	private WebElement update_product;

	public ConfigProductPage(WebDriver driver) {
		super(driver, By.id("update-product"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().contains("Edit Product");
	}
	
	public void changeAllowUnconfirmed() {
		allows_unconfirmed.click();
		update_product.click();
	}

}
