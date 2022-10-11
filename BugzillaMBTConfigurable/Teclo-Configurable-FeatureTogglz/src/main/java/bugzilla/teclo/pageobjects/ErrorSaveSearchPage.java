package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorSaveSearchPage extends AbstractErrorPage{
	
	public ErrorSaveSearchPage(WebDriver driver){
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'No Search Name Specified')]"));
	}
	
 	@Override
    protected boolean isMatchingPage() {
		 return "No Search Name Specified".equals(getTitle());
	}
}
