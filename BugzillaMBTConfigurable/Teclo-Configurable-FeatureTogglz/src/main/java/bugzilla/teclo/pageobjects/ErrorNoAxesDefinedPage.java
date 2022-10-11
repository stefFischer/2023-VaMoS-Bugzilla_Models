package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ErrorNoAxesDefinedPage extends AbstractErrorPage {

    @FindBy(id = "error_msg")
    private WebElement errorMsg;

    public ErrorNoAxesDefinedPage(WebDriver driver) {
        super(driver, By.xpath("//span[@id='title' and contains(text(), 'No Axes Defined')]"));
    }

    @Override
    protected boolean isMatchingPage() {
        return "No Axes Defined".equals(getTitle());
    }

    public String getErrorMessage() {
        return errorMsg.getText();
    }

}