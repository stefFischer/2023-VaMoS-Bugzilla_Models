package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DeleteVersionPage extends AbstractBugzillaPage {
	
	@FindBy(xpath = "//table/tbody/tr[2]/td[2]")
	WebElement versionName;
	
//	@FindBy(id = "delete")
//	WebElement deleteVersionButton;

	@FindBy(xpath = "//table/tbody/tr[4]/td[2]/a")
	WebElement bugsLink;

	public DeleteVersionPage(WebDriver driver) {
		super(driver, By.xpath("//table/tbody/tr[2]/td[2]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return getTitle().matches("Delete Version of Product .*");
	}
	
	public String getVersionName(){
		return versionName.getText();
	}

	public int getNumberOfBugs(){
		try{
			driver.findElement(By.id("delete"));
			return 0;
		} catch (NoSuchElementException e){
			String text = bugsLink.getText();
			return Integer.parseInt(text);
		}
	}

	public SearchResultsPage gotoBugs(){
		bugsLink.click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}

	public void deleteVersion(){
		driver.findElement(By.id("delete")).click();
	}

}
