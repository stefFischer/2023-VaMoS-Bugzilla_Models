package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserAddPage extends AbstractLoggedinBugzillaPage {
	@FindBy(id = "login")
	WebElement loginName;

	@FindBy(id = "email")
	WebElement email;

	@FindBy(id = "name")
	WebElement name;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "disabledtext")
	WebElement disabledText;

	@FindBy(id = "add")
	WebElement addButton;

	public UserAddPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Add user')]"));
	}

	@Override
	protected boolean isMatchingPage() {
		return driver.getTitle().equals("Add user");
	}

	public UserEditPage fillUser(String name, String password, String email, boolean disabled) {
		loginName.sendKeys(name);
		this.email.sendKeys(email);
		this.name.sendKeys(name);
		this.password.sendKeys(password);

		if (disabled) {
			disabledText.sendKeys("you shall not pass");
		}

		addButton.click();
		return PageFactory.initElements(driver, UserEditPage.class);
	}
}
