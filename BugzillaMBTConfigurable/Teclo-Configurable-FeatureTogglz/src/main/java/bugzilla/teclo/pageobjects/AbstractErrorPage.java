package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractErrorPage extends AbstractBugzillaPage {
	
	@FindBy(id = "error_msg")
	@CacheLookup
	private WebElement errorMsg;

	
	public AbstractErrorPage(WebDriver driver, By by) {
		super(driver, by);
	}
	
	public String getErrorMsg() {
		return errorMsg.getText();
	}
}
