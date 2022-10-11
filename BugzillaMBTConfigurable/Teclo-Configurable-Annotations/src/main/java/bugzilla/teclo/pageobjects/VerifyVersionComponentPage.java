package bugzilla.teclo.pageobjects;

import bugzilla.teclo.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class VerifyVersionComponentPage extends AbstractLoggedinBugzillaPage {

    //name="component"
    @FindBy(name = "component")
    private WebElement bugComponent;

    //name="version"
    @FindBy(name = "version")
    private WebElement bugVersion;

    @FindBy(id = "change_product")
    private WebElement commitButton;

    public VerifyVersionComponentPage(WebDriver driver) {
        super(driver, By.id("change_product"));
    }

    @Override
    protected boolean isMatchingPage() {
        return getTitle().equals("Verify New Product Details...");
    }

    public String getComponent() {
        return Helper.getSelectedOptionValue(bugComponent);
    }

    public void setComponent(String component){
        new Select(bugComponent).selectByVisibleText(component);
    }

    public String getVersion() {
        return Helper.getSelectedOptionValue(bugVersion);
    }

    public void setVersion(String testVersion){
        new Select(bugVersion).selectByVisibleText(testVersion);
    }

    public BugChangedPage commitBug() {
        commitButton.click();
        return PageFactory.initElements(driver, BugChangedPage.class);
    }
}
