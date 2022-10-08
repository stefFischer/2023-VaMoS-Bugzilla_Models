package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ErrorCircularDependencyPage extends AbstractErrorPage {

    public ErrorCircularDependencyPage(WebDriver driver) {
        super(driver, By.xpath("//span[@id='title' and contains(text(), 'Bugzilla – Dependency Loop Detected')]"));
    }

    @Override
    protected boolean isMatchingPage() {
        return "Dependency Loop Detected".equals(getTitle());
    }

}