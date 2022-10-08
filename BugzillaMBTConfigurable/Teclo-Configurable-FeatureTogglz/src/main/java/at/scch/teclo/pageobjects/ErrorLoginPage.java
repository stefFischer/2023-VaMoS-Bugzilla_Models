package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorLoginPage extends AbstractErrorPage {

	public ErrorLoginPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Invalid Login Or Password')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Invalid Login Or Password".equals(getTitle());
	}

}
