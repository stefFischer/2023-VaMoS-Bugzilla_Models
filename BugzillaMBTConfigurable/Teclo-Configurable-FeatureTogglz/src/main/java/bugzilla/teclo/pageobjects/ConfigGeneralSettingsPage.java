package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfigGeneralSettingsPage extends AbstractLoggedinBugzillaPage {

		@FindBy(id = "announcehtml")
		private WebElement announcehtmlField;
		
		@FindBy(id = "save-params")
		private WebElement saveChangesButton;
		
		public ConfigGeneralSettingsPage(WebDriver driver){
			super(driver, By.id("save-params"));
		}

		@Override
		protected boolean isMatchingPage() {
			return ("Configuration: General".equals(getTitle()) 
					|| "Parameters Updated".equals(getTitle())); 
		}	
		
		
		public ConfigGeneralSettingsPage setAnnounceHtml(String announcement){
			announcehtmlField.clear();
			announcehtmlField.sendKeys(announcement);
			saveChangesButton.click();
			return PageFactory.initElements(driver, ConfigGeneralSettingsPage.class);
		}
}
