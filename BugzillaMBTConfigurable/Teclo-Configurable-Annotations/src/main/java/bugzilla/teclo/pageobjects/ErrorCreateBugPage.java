package bugzilla.teclo.pageobjects;

import bugzilla.teclo.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ErrorCreateBugPage extends AbstractErrorPage{

	public ErrorCreateBugPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and (contains(text(), 'Invalid') or contains(text(), 'Illegal') or contains(text(), 'Alias') or contains(text(), 'Loop'))] | //div[@class='validation_error_text' and contains(text(), 'Summary')] | //*[@class='warning' and contains(text(), 'match')]"));
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
		return Helper.isElementPresent(driver, By.xpath("//div[@class='validation_error_text' and contains(text(), 'Summary')]"));
	}

}
