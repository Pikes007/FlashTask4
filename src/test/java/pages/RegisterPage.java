package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import tests.BaseTest;


public class RegisterPage extends BaseTest{
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "(//a[normalize-space()='Register'])[1]") 
	public WebElement registerButton;
	@FindBy(xpath = "(//input[@id='gender-male'])[1]") 
	public WebElement radioMale;
	@FindBy(xpath = "(//input[@id='gender-female'])[1]") 
	public WebElement radioFemale;
	@FindBy(xpath = "(//input[@id='FirstName'])[1]") 
	public WebElement inputFirstName;
	@FindBy(xpath = "(//input[@id='LastName'])[1]") 
	public WebElement inputLastName;
	@FindBy(xpath = "(//input[@id='Email'])[1]") 
	public WebElement inputEmail;
	@FindBy(xpath = "(//input[@id='Password'])[1]") 
	public WebElement inputPassword;
	@FindBy(xpath = "(//input[@id='ConfirmPassword'])[1]") 
	public WebElement inputConfirmPassword;
	@FindBy(xpath = "(//input[@id='register-button'])[1]") 
	public WebElement registerSubmitButton;
	@FindBy(xpath = "(//div[@class='result'])[1]") 
	public WebElement registrationComplete;
	@FindBy(xpath = "(//input[@value='Continue'])[1]") 
	public WebElement continueButton;
	
	/**
	 * Fills in the registration form with the provided details and submits the form.

	 * @param firstName The first name of the user.
	 * @param lastName The last name of the user.
	 * @param email The email address of the user.
	 * @param password The password chosen by the user.
	 * @param confirmPassword The password confirmation entered by the user.
	 */
	public void fillInForm (String firstName, String lastName, String email, String password, String confirmPassword) {
		radioMale.click();  //edit to female per test requirements here.
		inputFirstName.sendKeys(firstName);
		inputLastName.sendKeys(lastName);
		inputEmail.sendKeys(email);
		inputPassword.sendKeys(password);
		inputConfirmPassword.sendKeys(confirmPassword);
		registerSubmitButton.click();
	}
	/**
	 * Confirms that the registration was completed by retrieving the confirmation message 
	 * and then clicking the continue button. Returns the confirmation message text.
	 * 
	 * @param registrationConfirmed The WebElement that contains the registration completion message.
	 * @param completeRegistrationButton The WebElement representing the continue button after registration.
	 * @return The text of the registration confirmation message.
	 */
	public String confirmRegistrationComplete (WebElement registrationConfirmed, WebElement completeRegistrationButton) {
		String confirmationMessage = registrationConfirmed.getText();
		completeRegistrationButton.click();
		return confirmationMessage;
	}
}
