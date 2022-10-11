package bugzilla.teclo.pageobjects;

import bugzilla.teclo.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ChooseProductPage_ extends AbstractLoggedinBugzillaPage{

	public ChooseProductPage_(WebDriver driver) {
		super(driver, By.xpath("//h2[contains(text(), 'product')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return Helper.isElementPresent(driver, By.xpath("//h2[contains(text(), 'product')]"));
	}
	
	public AbstractLoggedinBugzillaPage chooseProduct(String product) {
		AbstractLoggedinBugzillaPage returnPage; 
		if(isCreateABug()) {
			driver.findElement(By.linkText(product)).click();
			returnPage = PageFactory.initElements(driver, CreateBugPage.class);
		} else if(isBrowse()) {
			driver.findElement(By.linkText(product)).click();
			returnPage = PageFactory.initElements(driver, ChooseComponentPage.class);
		} else {
			throw new IllegalStateException("Cannot choose a product");
		}
		return returnPage;
	}
	
	public boolean isCreateABug() {
		return Helper.isElementPresent(driver, By.xpath("//h2[contains(text(), 'you must pick a product on which to enter a bug:')]"));
	}
	
	public boolean isBrowse() {
		return Helper.isElementPresent(driver, By.xpath("//h2[contains(text(), 'Select a product category to browse:')]"));
	}
}
