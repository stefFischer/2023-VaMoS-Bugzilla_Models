package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BugChangedPage extends AbstractLoggedinBugzillaPage {

	@FindBy(id = "title")
	private WebElement bugTitle;	
	
	public BugChangedPage(WebDriver driver) {
		super(driver, By.xpath("//*[(@id='title' and contains(text(), 'processed')) or (contains(text(), 'submited'))] | "
				+ "//*[contains(text(), 'submitted')]"));
	}

	public int getChangedBugId() {
		for (WebElement dt : driver.findElements(By.cssSelector("dt"))) {
			if(dt.getText().matches("Changes submitted for bug.*")){
				return Integer.parseInt(dt.findElement(By.tagName("a")).getText());
			}
		}
		throw new IllegalStateException("Could not find ChangedBugId!");
	}

	public EditBugPage gotoChangedBugPage() {
		driver.findElement(By.linkText(String.valueOf(getChangedBugId()))).click();
		return PageFactory.initElements(driver, EditBugPage.class);
	}

	public String getSuccessMsg() {
		return driver.findElement(By.cssSelector("dt")).getText();
	}
	
	
	@Override
	protected boolean isMatchingPage() {
		if(getTitle().matches("\\d+ processed")){
			return true;
		}
		for (WebElement dt : driver.findElements(By.cssSelector("dt"))) {
			if(dt.getText().matches("Changes submitted for bug.*")){
				return true;
			}
		}
		return false;
	}
}
