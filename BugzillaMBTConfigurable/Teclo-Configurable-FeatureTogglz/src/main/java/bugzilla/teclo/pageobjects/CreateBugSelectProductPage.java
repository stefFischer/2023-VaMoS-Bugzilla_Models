package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class CreateBugSelectProductPage extends AbstractLoggedinBugzillaPage {

	public CreateBugSelectProductPage(WebDriver driver) {
		super(driver, By.id("choose_product"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Enter Bug".matches(getTitle());
	}

	public CreateBugPage setProduct(String productName) {
		WebElement productLink = driver.findElement(By.linkText(productName));
		productLink.click();
		return PageFactory.initElements(driver, CreateBugPage.class);
	}
}
