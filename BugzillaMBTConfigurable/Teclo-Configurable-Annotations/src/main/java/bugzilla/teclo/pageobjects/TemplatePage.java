package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TemplatePage extends AbstractLoggedinBugzillaPage {

	@FindBy(linkText = "this link to the Bug entry form")
	private WebElement link;

	public TemplatePage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Template constructed')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().matches("Template For Bug Entry");
	}

	@Override
	public CreateBugPage gotoCreateBugPage() {
		link.click();
		return PageFactory.initElements(driver, CreateBugPage.class);
	}
}