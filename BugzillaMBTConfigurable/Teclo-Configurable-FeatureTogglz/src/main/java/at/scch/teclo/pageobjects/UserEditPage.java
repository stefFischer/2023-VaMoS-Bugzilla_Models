package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserEditPage extends AbstractLoggedinBugzillaPage {

	public UserEditPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Edit user')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().matches("Edit user.*");
	}

}
