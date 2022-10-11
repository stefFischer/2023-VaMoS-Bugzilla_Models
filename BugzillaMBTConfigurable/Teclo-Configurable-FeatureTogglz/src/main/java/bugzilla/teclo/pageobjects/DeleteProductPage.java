package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteProductPage extends AbstractBugzillaPage {
	
	@FindBy(xpath = "//tr[contains(., 'Product:')]/td/a")
	WebElement linkToProduct;
	
	@FindBy(id="delete")
	WebElement yesDeleteButton;

	public DeleteProductPage(WebDriver driver) {
		super(driver, By.id("delete"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().matches("Delete Product '.*'");
	}

	
	public String getProductName() {
		return linkToProduct.getText();
	}
	
	public void commitDeleteBug() {
		yesDeleteButton.click();
	}
	
}
