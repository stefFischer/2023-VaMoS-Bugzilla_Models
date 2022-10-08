package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddComponentPage extends AbstractBugzillaPage {
	
	@FindBy(name = "component")
	private WebElement componentInputField;

	@FindBy(name = "description")
	private WebElement componentDescriptionTextArea;
	
	@FindBy(id = "initialowner")
	private WebElement componentDefaultAssigneeInputField;
	
	@FindBy(id = "create")
	private WebElement addComponentButton;

	public AddComponentPage(WebDriver driver) {
		super(driver, By.id("create"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().contains("Add component to the"); 
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
		
		addComponentButton.click();
	}

}
