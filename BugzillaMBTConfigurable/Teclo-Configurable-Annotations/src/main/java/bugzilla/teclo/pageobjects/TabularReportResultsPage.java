package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TabularReportResultsPage extends AbstractLoggedinBugzillaPage {
	
	@FindBy(xpath = "//*[@id=\"tabular_report_1_wrapper\"]/div[2]/div[1]/div/table/thead/tr/th[1]/div")
	private WebElement axisLabel;

	
	public TabularReportResultsPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Report:')]"));
	}
	
	@Override
	protected boolean isMatchingPage() {
		return getTitle().matches("Report: .+");
	}
	

	public String getVerticalAxisLabel() {
		String verticalAxisText = axisLabel.getText().replaceAll("[^A-Za-z]", "");
		return verticalAxisText.split("(?=\\p{Upper})")[0];
	}

	public String getHorizontalAxisLabel() {
		String horizontalAxisText = axisLabel.getText().replaceAll("[^A-Za-z]", "");
		return horizontalAxisText.split("(?=\\p{Upper})")[1];
	}

}
