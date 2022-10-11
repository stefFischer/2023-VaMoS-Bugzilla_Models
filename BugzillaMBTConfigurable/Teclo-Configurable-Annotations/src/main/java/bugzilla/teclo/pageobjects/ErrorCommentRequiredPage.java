package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorCommentRequiredPage extends AbstractErrorPage {

	public ErrorCommentRequiredPage(WebDriver driver) {
		super(driver, By.id("error_msg"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Comment Required".equals(getTitle());
	}

}
