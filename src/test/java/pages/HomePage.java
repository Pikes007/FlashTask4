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

public class HomePage extends BaseTest {
	
	private WebDriverWait wait;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "(//a[normalize-space()='Register'])[1]") 
	public WebElement registerButton;
	@FindBy(xpath = "(//h1[normalize-space()='Register'])[1]") 
	public WebElement registerHeader;
	@FindBy(xpath = "(//a[normalize-space()='Log in'])[1]") 
	public WebElement logInButton;
	@FindBy(xpath = "(//h1[normalize-space()='Welcome, Please Sign In!'])[1]") 
	public WebElement logInHeader;
	@FindBy(xpath = "//span[normalize-space()='Shopping cart']") 
	public WebElement shoppingCartIcon;
	@FindBy(xpath = "//h1[normalize-space()='Shopping cart']") 
	public WebElement shoppingCartHeader;
	
	/**
	 * Clicks the 'Register' button on the home page and navigates to the Register page.
	 * @return A new instance of `RegisterPage`.
	 */
	public RegisterPage clickRegisterButton() {
		wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
		RegisterPage registerPage = new RegisterPage(driver);
		wait.until(ExpectedConditions.visibilityOf(registerHeader));
		return registerPage;
	}
	/**
	 * Clicks the 'Log In' button on the home page and navigates to the Log In page.
	 * @return A new instance of `LogInPage`.
	 */
	public LogInPage clickLogInButton() {
		wait.until(ExpectedConditions.elementToBeClickable(logInButton)).click();
		LogInPage logInPage = new LogInPage(driver);
		wait.until(ExpectedConditions.visibilityOf(logInHeader));
		return logInPage;
	}
	/**
	 * Clicks the 'Shopping Cart' icon on the home page and navigates to the Shopping Cart page.
	 * @return A new instance of `ShoppingCartPage`.
	 */
	public ShoppingCartPage clickShoppingCartButton() {
		wait.until(ExpectedConditions.elementToBeClickable(shoppingCartIcon)).click();
		ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
		wait.until(ExpectedConditions.visibilityOf(shoppingCartHeader));
		return shoppingCartPage;
		
	}
	/**
	 * Clicks on the account link associated with the given email address.
	 * Constructs a dynamic XPath using the provided email, waits for the presence of the element located by the XPath,
	 * @param email The email address used to locate the account link on the page.
	 * @return A new instance of `AccountPage`.
	 */
	public AccountPage clickAccount(String email) {
		String dynamicXpath = "//a[normalize-space()='" + email + "']";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dynamicXpath))).click();
		AccountPage accountPage = new AccountPage(driver);
		return accountPage;
		
	}
	
}