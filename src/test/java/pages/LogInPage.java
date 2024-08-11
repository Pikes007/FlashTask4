package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tests.BaseTest;

public class LogInPage extends BaseTest{

	private WebDriverWait wait;
	
	public LogInPage (WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "(//input[@id='Email'])[1]") 
	public WebElement inputEmail;
	@FindBy(xpath = "(//input[@id='Password'])[1]") 
	public WebElement inputPassword;
	@FindBy(xpath = "(//input[@value='Log in'])[1]") 
	public WebElement logInButton;
	@FindBy(xpath = "(//a[normalize-space()='Computers'])[1]") 
	public WebElement topMenuComputers;
	@FindBy(xpath = "(//a[normalize-space()='Desktops'])[1]") 
	public WebElement subMenuDesktops;
	@FindBy(xpath = "(//h1[normalize-space()='Desktops'])[1]") 
	public WebElement desktopsHeader;
	
	/**
     * Logs in a returning user by entering the provided email and password,
     * and clicking the login button.
     * 
     * @param email The email address of the user.
     * @param password The password for the user.
     */
	public void logInReturningUser(String email, String password) {
		inputEmail.sendKeys(email);;
		inputPassword.sendKeys(password);;
		logInButton.click();
	}
	/**
     * Retrieves the WebElement for the logged-in user based on the provided email address.
     * This WebElement represents a dynamic element on the page whose locator is built using the email address.
     * 
     * @param email The email address of the logged-in user.
     * @return The WebElement corresponding to the user's element.
     */
	public WebElement getLoggedInUserElement (String email) {
		String xpath = String.format("(//a[normalize-space()='%s'])[1]", email);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}
	/**
     * Navigates to the Computers page by hovering over the top menu element
     * and selecting the Desktops submenu. Waits until the Desktops header is visible,
     * and then returns a new instance of the ComputersPage.
     * 
     * @return A ComputersPage object representing the Computers page.
     */
	public ComputersPage getComputersPage () {
		hoverAndSelect(topMenuComputers, subMenuDesktops);
		wait.until(ExpectedConditions.visibilityOf(desktopsHeader));
		return new ComputersPage(driver);
	}
}
