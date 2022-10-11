package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReportsBasePage extends AbstractLoggedinBugzillaPage {

	@FindBy(linkText = "Tabular reports")
	private WebElement tabularReportsLink;

	public ReportsBasePage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Reporting and Charting Kitchen')]"));
	}
	
	@Override
	protected boolean isMatchingPage() {
		return "Reporting and Charting Kitchen".equals(getTitle());
	}

	public TabularReportGeneratePage gotoGenerateTabularReportPage() {
		tabularReportsLink.click();
		return PageFactory.initElements(driver, TabularReportGeneratePage.class);
	}

}
