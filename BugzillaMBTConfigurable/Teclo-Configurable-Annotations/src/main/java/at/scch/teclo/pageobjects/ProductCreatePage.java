package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ProductCreatePage extends AbstractLoggedinBugzillaPage{
	
	public ProductCreatePage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Add Product')]"));
	}
	
	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().equals("Add Product");
	}

	public ProductCreatedPage addProduct(String productName) {
		fillOutForm(productName);
		commit();
		return PageFactory.initElements(driver, ProductCreatedPage.class);
	}
	
	private void fillOutForm(String productName) {
		fillProductName(productName);
		fillDescription("This is another test product");
		fillCheckboxes();
		fillComponent();
	}
	
	private void commit() {
		driver.findElement(By.id("add-product")).click();
	}
	
	private void fillProductName(String prodcutName) {
		driver.findElement(By.name("product")).sendKeys(prodcutName);
	}
	
	private void fillDescription(String description) {
		driver.findElement(By.name("description")).sendKeys(description);
	}
	
	private void fillCheckboxes() {
		selectIfNecassary(driver.findElement(By.name("is_active")));
		selectIfNecassary(driver.findElement(By.name("allows_unconfirmed")));
		selectIfNecassary(driver.findElement(By.name("createseries")));
	}
		
	private void selectIfNecassary(WebElement checkbox) {
		if(!checkbox.isSelected()) {
			checkbox.click();
		}
	}
	
	private void fillComponent() {
		fillComponentName("TestComp");
		fillComponentDescription("This is a description");
		fillDefaultAssignee("admin");
	}
	
	private void fillDefaultAssignee(String assignee) {
		driver.findElement(By.name("initialowner")).sendKeys(assignee);
	}

	private void fillComponentDescription(String description) {
		driver.findElement(By.id("comp_desc")).sendKeys(description);
	}

	private void fillComponentName(String name) {
		driver.findElement(By.id("component")).sendKeys(name);
	}
	
}
