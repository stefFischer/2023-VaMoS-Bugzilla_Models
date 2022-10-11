package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class TabularReportGeneratePage extends AbstractLoggedinBugzillaPage {

	@FindBy(name = "x_axis_field")
	private WebElement horizontalAxisField;

	@FindBy(name = "y_axis_field")
	private WebElement verticalAxisField;

	@FindBy(id = "Generate_Report_top")
	private WebElement generateReportButton;

	
	public TabularReportGeneratePage(WebDriver driver) {
		super(driver, By.name("y_axis_field"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Generate Tabular Report".equals(getTitle());
	}
	
	
	public void setHorizontalAxis(String horizontalValue) {
		new Select(horizontalAxisField).selectByVisibleText(horizontalValue);
	}

	public void setVeritcalAxis(String verticalValue) {
		new Select(verticalAxisField).selectByVisibleText(verticalValue);
	}

	public TabularReportResultsPage generateReport() {
		generateReportButton.click();
		return PageFactory.initElements(driver, TabularReportResultsPage.class);
	}

	public ErrorNoAxesDefinedPage generateReportWithoutAxisSpecification() {
		generateReportButton.click();
		return PageFactory.initElements(driver, ErrorNoAxesDefinedPage.class);
	}

}
