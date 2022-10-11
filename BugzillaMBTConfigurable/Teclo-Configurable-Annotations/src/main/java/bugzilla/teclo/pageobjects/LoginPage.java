package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractBugzillaPage {

	@FindBy(id = "Bugzilla_login")
	private WebElement bugzillaLogin;
	
	@FindBy(id = "Bugzilla_password")
	private WebElement bugzillaPassword;
	
	@FindBy(id = "log_in")
	private WebElement loginButton;

	
	public LoginPage(WebDriver driver) {
		super(driver, By.xpath("//span[@id='title' and contains(text(), 'Log in to Bugzilla')]"));
	}
	
	@Override
	protected boolean isMatchingPage() {
		return "Log in to Bugzilla".equals(getTitle());
	}

		
	public AbstractBugzillaPage loginSuccessful(String username, String password) {
		bugzillaLogin.clear();
		bugzillaLogin.sendKeys(username);
		bugzillaPassword.clear();
		bugzillaPassword.sendKeys(password);
		loginButton.click();
		
		/*#if ($AddProduct)*/
		try {
			return PageFactory.initElements(driver, CreateBugSelectProductPage.class);
		} catch (RuntimeException ex){
			//special treatment for setting up config AddProduct
			return PageFactory.initElements(driver, CreateBugPage.class);
		}
		/*#else*/
		return PageFactory.initElements(driver, CreateBugPage.class);
		/*#end*/
	}
	
	public ErrorLoginPage loginFailing(String username, String password) {
		bugzillaLogin.clear();
		bugzillaLogin.sendKeys(username);
		bugzillaPassword.clear();
		bugzillaPassword.sendKeys(password);
		loginButton.click();
		
		return PageFactory.initElements(driver, ErrorLoginPage.class); 
	}
}
