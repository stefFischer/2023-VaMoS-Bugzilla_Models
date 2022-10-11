package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorUnresolvedDependencyPage extends AbstractErrorPage {

	public ErrorUnresolvedDependencyPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Bugzilla – Unresolved Dependencies')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Unresolved Dependencies".equals(getTitle());
	}

}