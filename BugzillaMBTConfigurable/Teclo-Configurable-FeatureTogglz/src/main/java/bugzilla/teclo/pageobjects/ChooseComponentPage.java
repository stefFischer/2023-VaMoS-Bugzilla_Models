package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ChooseComponentPage extends AbstractLoggedinBugzillaPage implements PageWithProduct{

	public ChooseComponentPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Components for TestProduct')]"));
	}
	
	@Override
	public boolean isMatchingPage() {
		return driver.getTitle().matches("Components for .*");
	}
	
	public SearchResultsPage chooseComponent(String component) {
		driver.findElement(By.linkText(component)).click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}

	@Override
	public String getProductName() {
		return driver.findElement(By.xpath("//h1")).getText();
	}
	

}
