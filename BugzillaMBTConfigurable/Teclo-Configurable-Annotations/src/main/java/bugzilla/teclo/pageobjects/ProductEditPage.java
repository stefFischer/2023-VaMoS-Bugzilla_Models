package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductEditPage extends AbstractLoggedinBugzillaPage {

	public ProductEditPage(WebDriver driver) {
		super(driver, By.xpath("//a[@title='Add a product']"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().equals("Select product");
	}

	public ProductCreatePage gotoAddProduct() {
		driver.findElement(By.linkText("Add")).click();
		return PageFactory.initElements(driver, ProductCreatePage.class);
	}

	public ProductDeletedPage deleteProduct(String name) {
		WebElement deleteLink = driver
				.findElement(By.xpath("//td/a[contains(text(), '" + name + "')]/../following-sibling::td[4]/a"));
		deleteLink.click();
		driver.findElement(By.id("delete")).click();
		return PageFactory.initElements(driver, ProductDeletedPage.class);
	}
}
