package bugzilla.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorCircularDuplicatesPage extends AbstractErrorPage {

    public ErrorCircularDuplicatesPage(WebDriver driver) {
        super(driver, By.xpath("//span[@id='title' and contains(text(), 'Bugzilla – Loop detected among duplicates')]"));
    }

    @Override
    protected boolean isMatchingPage() {
        return "Loop detected among duplicates".equals(getTitle());
    }

}