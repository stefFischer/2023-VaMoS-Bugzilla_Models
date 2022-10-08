package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddProductPage extends AbstractBugzillaPage {
	
	@FindBy(name = "product")
	private WebElement productNameInputField;

	@FindBy(name = "description")
	private WebElement productDescriptionTextArea;

	@FindBy(name = "component")
	private WebElement componentInputField;

	@FindBy(name = "comp_desc")
	private WebElement componentDescriptionTextArea;

	@FindBy(id = "initialowner")
	private WebElement componentDefaultAssigneeInputField;

	@FindBy(id = "add-product")
	private WebElement addProductButton;


	public AddProductPage(WebDriver driver) {
		super(driver, By.id("add-product"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Add Product".equals(getTitle()); 
	}
	
	public void addProduct(String name, String description){
		productNameInputField.click();
		productNameInputField.clear();
		productNameInputField.sendKeys(name);
		
		productDescriptionTextArea.click();
		productDescriptionTextArea.clear();
		productDescriptionTextArea.sendKeys(description);
	}

	public void addComponent(String name, String description, String defaultAssignee){
		componentInputField.click();
		componentInputField.clear();
		componentInputField.sendKeys(name);

		componentDescriptionTextArea.click();
		componentDescriptionTextArea.clear();
		componentDescriptionTextArea.sendKeys(description);

		componentDefaultAssigneeInputField.click();
		componentDefaultAssigneeInputField.clear();
		componentDefaultAssigneeInputField.sendKeys(defaultAssignee);

		addProductButton.click();
	}

}
