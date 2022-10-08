package at.scch.teclo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;

public class ConfigRequiredCommentsPage extends AbstractBugzillaPage {

	// column 1
	@FindBy(id = "c_0_1")
	private WebElement start_UNCONFIRMED;
	@FindBy(id = "c_4_1")
	private WebElement RESOLVED_UNCONFIRMED;
	@FindBy(id = "c_5_1")
	private WebElement VERIFIED_UNCONFIRMED;

	// column 2
	@FindBy(id = "c_0_2")
	private WebElement start_CONFIRMED;
	@FindBy(id = "c_1_2")
	private WebElement UNCONFIRMED_CONFIRMED;
	@FindBy(id = "c_3_2")
	private WebElement IN_PROGRESS_CONFIRMED;
	@FindBy(id = "c_4_2")
	private WebElement RESOLVED_CONFIRMED;
	@FindBy(id = "c_5_2")
	private WebElement VERIFIED_CONFIRMED;

	// column 3
	@FindBy(id = "c_0_3")
	private WebElement start_IN_PROGRESS;
	@FindBy(id = "c_1_3")
	private WebElement UNCONFIRMED_IN_PROGRESS;
	@FindBy(id = "c_2_3")
	private WebElement CONFIRMED_IN_PROGRESS;

	// column 4
	@FindBy(id = "c_1_4")
	private WebElement UNCONFIRMED_RESOLVED;
	@FindBy(id = "c_2_4")
	private WebElement CONFIRMED_RESOLVED;
	@FindBy(id = "c_3_4")
	private WebElement IN_PROGRESS_RESOLVED;
	@FindBy(id = "c_5_4")
	private WebElement VERIFIED_RESOLVED;

	// column 5
	@FindBy(id = "c_4_5")
	private WebElement RESOLVED_VERIFIED;

	//update_comment
	@FindBy(id = "update_comment")
	private WebElement update_comment;

	public ConfigRequiredCommentsPage(WebDriver driver) {
		super(driver, By.id("update_comment"));
	}

	@Override
	protected boolean isMatchingPage() {
		return "Comments Required on Status Transitions".equals(driver.getTitle());
	}

	public ConfigRequiredCommentsPage updateCommentsRequiredOnCreation() {
		start_UNCONFIRMED.click();
		start_CONFIRMED.click();
		try{
			start_IN_PROGRESS.click();
		} catch (NoSuchElementException e){
			System.err.println("start_IN_PROGRESS is not enabled.");
		}
		update_comment.click();
		return PageFactory.initElements(driver, ConfigRequiredCommentsPage.class);
	}

	public ConfigRequiredCommentsPage updateCommentsRequiredOnStatusTransitions() {

		start_UNCONFIRMED.click();
		RESOLVED_UNCONFIRMED.click();
		VERIFIED_UNCONFIRMED.click();

		start_CONFIRMED.click();
		UNCONFIRMED_CONFIRMED.click();
		IN_PROGRESS_CONFIRMED.click();
		RESOLVED_CONFIRMED.click();
		VERIFIED_CONFIRMED.click();

		start_IN_PROGRESS.click();
		UNCONFIRMED_IN_PROGRESS.click();
		CONFIRMED_IN_PROGRESS.click();

		UNCONFIRMED_RESOLVED.click();
		CONFIRMED_RESOLVED.click();
		IN_PROGRESS_RESOLVED.click();
		try{
			VERIFIED_RESOLVED.click();
		} catch (NoSuchElementException e){
			System.err.println("VERIFIED_RESOLVED is not enabled.");
		}

		RESOLVED_VERIFIED.click();

		update_comment.click();
		return PageFactory.initElements(driver, ConfigRequiredCommentsPage.class);
	}
}
