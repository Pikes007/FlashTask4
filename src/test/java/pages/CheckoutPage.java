package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tests.BaseTest;

public class CheckoutPage extends BaseTest{
	
	private WebDriverWait wait;
	
	public CheckoutPage (WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath = "//select[@id='BillingNewAddress_CountryId']") 
	public WebElement selectCountryDropdown;
	@FindBy(xpath = "//input[@id='BillingNewAddress_City']") 
	public WebElement newAddressCity;
	@FindBy(xpath = "//input[@id='BillingNewAddress_Address1']") 
	public WebElement address1Line;
	@FindBy(xpath = "//input[@id='BillingNewAddress_ZipPostalCode']") 
	public WebElement postalCode;
	@FindBy(xpath = "//input[@id='BillingNewAddress_PhoneNumber']") 
	public WebElement phoneNumber;
	@FindBy(xpath = "//input[@onclick='Billing.save()']") 
	public WebElement continueButtonBillingAddress;
	@FindBy(xpath = "//input[@onclick='Shipping.save()']") 
	public WebElement continueButtonBillingShippingAddress;
	@FindBy(xpath = "//input[@onclick='ShippingMethod.save()']") 
	public WebElement continueButtonShippingMethod;
	@FindBy(xpath = "//input[@onclick='PaymentMethod.save()']") 
	public WebElement continuePaymentMethod;
	@FindBy(xpath = "//input[@onclick='PaymentInfo.save()']") 
	public WebElement continuePaymentInfo;
	@FindBy(xpath = "//input[@onclick='ConfirmOrder.save()']") 
	public WebElement continueConfirmOrder;
	@FindBy(xpath = "//strong[normalize-space()='Your order has been successfully processed!']") 
	public WebElement successfulOrderMessage;
	@FindBy(xpath = "//ul[@class='details']/li") 
	public WebElement orderNumberElement;
	@FindBy(xpath = "//input[@onclick=\"setLocation('/')\"]") 
	public WebElement continueOrderSuccess;
	
	/**
     * Fills in the new billing address information and proceeds to the next step.
     * 
     * @param selectCountry The country to select from the dropdown.
     * @param city The city to enter.
     * @param address1 The address line 1 to enter.
     * @param zipCode The postal code to enter.
     * @param contactNumber The phone number to enter.
     */
	public void fillInNewBillingAddress (String selectCountry, String city, String address1, String zipCode, String contactNumber) {
		selectFromDropdownByValue(selectCountryDropdown, selectCountry);
		newAddressCity.sendKeys(city);
		address1Line.sendKeys(address1);
		postalCode.sendKeys(zipCode);
		phoneNumber.sendKeys(contactNumber);
		continueButtonBillingAddress.click();
	}
	  /**
     * Completes the checkout process and retrieves the order number.
     * 
     * @return The order number generated after completing the checkout process.
     */
	public String checkoutAndReturnOrderNumber () {
		
		wait.until(ExpectedConditions.elementToBeClickable(continueButtonBillingShippingAddress)).click();
		wait.until(ExpectedConditions.elementToBeClickable(continueButtonShippingMethod)).click();
		wait.until(ExpectedConditions.elementToBeClickable(continuePaymentMethod)).click();
		wait.until(ExpectedConditions.elementToBeClickable(continuePaymentInfo)).click();
		wait.until(ExpectedConditions.elementToBeClickable(continueConfirmOrder)).click();
		String orderNumber = wait.until(ExpectedConditions.elementToBeClickable(orderNumberElement)).getText();
		return orderNumber;
		
	}
	/**
     * Retrieves the successful order message after completing the checkout process.
     * 
     * @return The success message indicating that the order has been processed successfully.
     */
	public String getSuccessfulOrderMessage () {
		wait.until(ExpectedConditions.visibilityOf(successfulOrderMessage));
		String successMessage = successfulOrderMessage.getText();
		
		return successMessage;
	}
	
}

