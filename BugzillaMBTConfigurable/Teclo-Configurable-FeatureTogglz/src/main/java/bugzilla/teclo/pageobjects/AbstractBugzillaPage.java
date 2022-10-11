package bugzilla.teclo.pageobjects;

import bugzilla.mbteclo.state.ConfigurationOption;
import bugzilla.teclo.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractBugzillaPage {
	private static final Logger Logger = LoggerFactory.getLogger(AbstractBugzillaPage.class);

	protected final WebDriver driver;

	@FindBy(linkText = "Home")
	private WebElement homeLink;

	@FindBy(linkText = "New")
	private WebElement newLink;

	@FindBy(linkText = "My Bugs")
	private WebElement myBugsLink;

	@FindBy(linkText = "Search")
	private WebElement searchLink;

	@FindBy(linkText = "Reports")
	private WebElement reportsLink;

	@FindBy(linkText = "Browse")
	private WebElement browseLink;

	@FindBy(id = "quicksearch_top")
	private WebElement quickFindText;

	@FindBy(id = "find_top")
	private WebElement quickFindButton;

	@FindBy(linkText = "Administration")
	private WebElement administrationLink;

	@FindBy(linkText = "Bug Status Workflow")
	private WebElement bugStatusWorkflowLink;

	public AbstractBugzillaPage(WebDriver driver, By by) {
		this.driver = driver;
		driver.findElement(by);
		if (!isMatchingPage()) {
			String errorMsg = "Page object " + this.getClass().getName() + " does not match the displayed page (title: "
					+ driver.getTitle() + ")!";
			Logger.error(errorMsg);
			throw new IllegalStateException(errorMsg);
		}
	}

	abstract protected boolean isMatchingPage();

	public String getTitle() {
		return driver.getTitle();
	}

	public boolean isLoggedIn() {
		return Helper.isElementPresent(driver, By.linkText("Log out"));
	}

	public String getLoggedInUser() {
		WebElement liLogoutUser = driver.findElement(By.xpath("//li[a[text()[contains(.,'Log')]]]"));
		String liText = liLogoutUser.getText();
		return liText.substring(liText.lastIndexOf(" ") + 1);
	}

	public boolean login(String username, String password) {
		gotoLoginPage();
		driver.findElement(By.id("Bugzilla_login")).sendKeys(username);
		driver.findElement(By.id("Bugzilla_login")).sendKeys(Keys.TAB);
		driver.findElement(By.id("Bugzilla_password")).sendKeys(password);
		driver.findElement(By.id("log_in")).click();
		return isLoggedIn();
	}

	public StartPage logout() {
		if (isLoggedIn()) {
			WebElement logoutLink = driver.findElement(By.linkText("Log out"));
			logoutLink.click();
		}
		gotoStartPage();
		return PageFactory.initElements(driver, StartPage.class);
	}

	public StartPage gotoStartPage() {
		homeLink.click();
		return PageFactory.initElements(driver, StartPage.class);
	}

	public AbstractBugzillaPage gotoCreateBugPage() {
		newLink.click();
		if(ConfigurationOption.AddProduct.isActive()) {
			return PageFactory.initElements(driver, CreateBugSelectProductPage.class);
		} else {
			return PageFactory.initElements(driver, CreateBugPage.class);
		}
	}

	public SearchBasePage gotoSearchBasePage() {
		searchLink.click();
		return PageFactory.initElements(driver, SearchBasePage.class);
	}

	public ReportsBasePage gotoReportsBasePage() {
		reportsLink.click();
		return PageFactory.initElements(driver, ReportsBasePage.class);
	}

	/** Goto login page if logged out. */
	public LoginPage gotoLoginPage() {
		newLink.click();
		return PageFactory.initElements(driver, LoginPage.class);
	}

	public EditBugPage findBug(int bugId) {
		quickFindText.clear();
		quickFindText.sendKeys(String.valueOf(bugId));
		quickFindButton.click();
		return PageFactory.initElements(driver, EditBugPage.class);
	}

	public EditBugPage findBug(String query) {
		quickFindText.clear();
		quickFindText.sendKeys(query);
		quickFindButton.click();
		return PageFactory.initElements(driver, EditBugPage.class);
	}

	public SearchResultsPage performSavedSearch(String savedSearchName) {
		WebElement linkToSavedSearch = driver
				.findElement(By.xpath("//li[@id='links-saved']/ul/li/a[text()='" + savedSearchName + "']"));
		linkToSavedSearch.click();
		return PageFactory.initElements(driver, SearchResultsPage.class);
	}

	public ChooseComponentPage gotoBrowse() {
		browseLink.click();
		return PageFactory.initElements(driver, ChooseComponentPage.class);
	}

	public ProductEditPage gotoEditProducts() {
		driver.findElement(By.linkText("Administration")).click();
		driver.findElement(By.linkText("Products")).click();
		return PageFactory.initElements(driver, ProductEditPage.class);
	}

	public ConfigGeneralSettingsPage gotoConfigGeneralSettingsPage() {
		driver.get(getBaseUrl() + "/editparams.cgi?section=general");
		return PageFactory.initElements(driver, ConfigGeneralSettingsPage.class);
	}

	public ConfigBugFieldsPage gotoConfigBugFieldsPage(){
		driver.get(getBaseUrl() + "/editparams.cgi?section=bugfields");
		return PageFactory.initElements(driver, ConfigBugFieldsPage.class);
	}

	public ConfigBugChangePoliciesPage gotoConfigBugChangePoliciesPage(){
		driver.get(getBaseUrl() + "/editparams.cgi?section=bugchange");
		return PageFactory.initElements(driver, ConfigBugChangePoliciesPage.class);
	}

	public AddProductPage gotoAddProductPage(){
		driver.get(getBaseUrl() + "/editproducts.cgi?action=add");
		return PageFactory.initElements(driver, AddProductPage.class);
	}

	public DeleteProductPage gotoDeleteProductPage(String productName) {
		driver.get(getBaseUrl() + "/editproducts.cgi?action=del&product=" + productName);
		return PageFactory.initElements(driver, DeleteProductPage.class);
	}

	public AddComponentPage gotoAddComponentPage(String productName){
		driver.get(getBaseUrl() + "/editcomponents.cgi?action=add&product=" + productName);
		return PageFactory.initElements(driver, AddComponentPage.class);
	}

	public DeleteComponentPage gotoDeleteComponentPage(String productName, String componentName){
		driver.get(getBaseUrl() + "/editcomponents.cgi?action=del&product=" + productName + "&component=" + componentName);
		return PageFactory.initElements(driver, DeleteComponentPage.class);
	}

	public AddVersionPage gotoAddVersionPage(String productName){
		driver.get(getBaseUrl() + "/editversions.cgi?action=add&product=" + productName);
		return PageFactory.initElements(driver, AddVersionPage.class);
	}

	public DeleteVersionPage gotoDeleteVersionPage(String productName, String version) {
		driver.get(getBaseUrl() + "/editversions.cgi?action=del&product=" + productName + "&version=" + version);
		return PageFactory.initElements(driver, DeleteVersionPage.class);
	}

	public ConfigWorkflowPage gotoConfigWorkflowPage() {
		driver.get(getBaseUrl() + "/editworkflow.cgi");
		return PageFactory.initElements(driver, ConfigWorkflowPage.class);
	}

	public ConfigRequiredCommentsPage gotoConfigRequiredCommentsPage() {
		driver.get(getBaseUrl() + "/editworkflow.cgi?action=edit_comment");
		return PageFactory.initElements(driver, ConfigRequiredCommentsPage.class);
	}

	public ConfigProductPage gotoConfigProductPage(String productName) {
		driver.get(getBaseUrl() + "/editproducts.cgi?action=edit&product=" + productName);
		return PageFactory.initElements(driver, ConfigProductPage.class);
	}

	public String getBaseUrl(){
		try
		{
			URL url = new URL(driver.getCurrentUrl());
			return url.getProtocol() + "://" + url.getHost();
		}
		catch (MalformedURLException e)
		{
			// do something
		}
		return null;
	}
}
