package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddVersionPage extends AbstractBugzillaPage {
	
	@FindBy(id = "version")
	WebElement versionInputField;
	
	@FindBy(id = "create")
	WebElement createVersionButton;

	public AddVersionPage(WebDriver driver) {
		super(driver, By.id("create"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().contains("Add Version"); 
	}
	
	public void addVersion(String versionName){
		versionInputField.clear();
		versionInputField.sendKeys(versionName);
		createVersionButton.click();
	}

}
