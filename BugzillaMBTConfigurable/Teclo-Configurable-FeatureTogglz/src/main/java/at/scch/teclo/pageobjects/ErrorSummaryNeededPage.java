package at.scch.teclo.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorSummaryNeededPage extends AbstractErrorPage {

	public ErrorSummaryNeededPage(WebDriver driver) {
		super(driver, By.xpath("//title"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Summary Needed".equals(getTitle());
	}

}