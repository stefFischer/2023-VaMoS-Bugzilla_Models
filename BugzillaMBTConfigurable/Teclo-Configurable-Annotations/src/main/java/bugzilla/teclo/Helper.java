package bugzilla.teclo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class Helper {

	public static String getSelectedOptionValue(WebElement element) {
		Select select = new Select(element);
		WebElement selectedElement = select.getFirstSelectedOption();
		String selectedOption = selectedElement.getAttribute("value");
		return selectedOption;
	}
	
	public static boolean isElementPresent(WebDriver driver, By by) {

		boolean elementFound = false;
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
			driver.findElement(by);
			elementFound = true;
		} catch (NoSuchElementException e) {
			elementFound = false;
		} finally {
			driver.manage().timeouts().implicitlyWait(BugzillaSetup.TIMEOUT_SECONDS, TimeUnit.SECONDS);
		}
		return elementFound;
	}
}
