package at.scch.teclo.pageobjects;

import at.scch.teclo.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ErrorCommentRequiredOnBugCreationPage extends AbstractErrorPage {
	
	public ErrorCommentRequiredOnBugCreationPage(WebDriver driver) {
		super(driver, By.xpath("//div[@class='validation_error_text' and contains(text(), 'You must enter a Description for this bug.')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().matches("(Invalid|Illegal|Enter|Match|Alias|Dependency).*");
	}

	public CreateBugPage goBack() {
		if(!isOnCreateABugPage()) {
			driver.navigate().back();
		}
		try{
			driver.findElement(By.xpath("//*[@id = 'expert_fields_controller' and contains(text(), 'Hide')]"));
			return PageFactory.initElements(driver, CreateBugAdvancedPage.class);
		} catch (NoSuchElementException e){
			return PageFactory.initElements(driver, CreateBugPage.class);
		}
	}

	private boolean isOnCreateABugPage() {
		return Helper.isElementPresent(driver, By.xpath("//div[@class='validation_error_text' and contains(text(), 'You must enter a Description for this bug.')]"));
	}
}
