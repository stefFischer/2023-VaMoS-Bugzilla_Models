package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteComponentPage extends AbstractBugzillaPage {
	
	@FindBy(xpath = "//tr[contains(., 'Component:')]/td[2]")
	WebElement componentName;
	
	@FindBy(id="delete")
	WebElement yesDeleteButton;
	
	public DeleteComponentPage(WebDriver driver){
		super(driver, By.id("delete"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().matches("Delete component '.*");
	}
	
	public String getComponentName() {
		return componentName.getText();
	}
	
	public void commitDeleteComponent() {
		yesDeleteButton.click();
	}
}
