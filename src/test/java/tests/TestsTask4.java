package tests;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AccountPage;
import pages.CheckoutPage;
import pages.ComputersPage;
import pages.HomePage;
import pages.LogInPage;
import pages.RegisterPage;
import pages.ShoppingCartPage;

public class TestsTask4 extends BaseTest {
	
	private String email;
	private String password;
	
	@BeforeClass
	public void prepareTestData() {
		email = "flash05@gmail.com"; // needs to be unique for each test run, update here (increment the number for each run)
		password = "testPassword"; 
	}
	/**
     * Tests user registration and verifies the registration confirmation message.
     */
	@Test(priority = 1)
	public void testRegisterUser() {
		
		HomePage homePage = new HomePage(driver);
		RegisterPage registerPage = homePage.clickRegisterButton();
		registerPage.fillInForm("testName", "testSurname", email, password, password);
		String actualMessage = registerPage.confirmRegistrationComplete(registerPage.registrationComplete, registerPage.continueButton);
	    String expectedMessage = "Your registration completed";
	    
	    Assert.assertEquals(actualMessage, expectedMessage, "Registration confirmation message does not match.");
	}
	/**
     * Tests user login and verifies the logged-in user's email.
     */
	@Test(priority = 2)
	public void testLogInUser() {
		
		HomePage homepage = new HomePage (driver);
		LogInPage logInPage = homepage.clickLogInButton();
		logInPage.logInReturningUser(email, password);
		
		WebElement loggedInUserElement = logInPage.getLoggedInUserElement(email);
		Assert.assertEquals(loggedInUserElement.getText(), email, "Logged in user email does not match!");
	} 
	/**
     * Tests adding items to the cart and validates that the total price matches the sum of subtotals.
     */
	@Test(priority = 3)
	public void testAddToCartAndValidateTotalPrice() {
		
		HomePage homepage = new HomePage (driver);
		LogInPage logInPage = homepage.clickLogInButton();
		logInPage.logInReturningUser(email, password);
		ComputersPage computersPage = logInPage.getComputersPage();
		selectFromDropdownByValue(computersPage.sortByDropdownElement, "Name: A to Z");
		computersPage.addAllProductsItemsToCart();
		ShoppingCartPage shoppingCartPage = computersPage.getShoppingCartPage();
		Float totalPriceDisplayed = (Float.parseFloat(shoppingCartPage.totalPrice.getText()));
		List<Float> subTotals = shoppingCartPage.getSubTotals();
		Float allSubTotalsAddedUp = shoppingCartPage.addSubTotals(subTotals);
		Assert.assertEquals(totalPriceDisplayed, allSubTotalsAddedUp, "Sum of subtotals is NOT equal to total price!");
		System.out.printf("Assertion passed: Sum of subtotals (%.2f) EQUAL to total price (%.2f).%n", allSubTotalsAddedUp, totalPriceDisplayed);
	}
	/**
     * Tests that the price of items in the cart matches the price pulled from the edit function.
     */
	@Test(priority = 4)
	public void testPricePullsThroughToCartCorrectly() {
		
		HomePage homepage = new HomePage (driver);
		LogInPage logInPage = homepage.clickLogInButton();
		logInPage.logInReturningUser(email, password);
		ComputersPage computersPage = logInPage.getComputersPage();
		List<String> productTitles = computersPage.getProductTitlesWithAddToCartButton();
		//computersPage.addAllProductsItemsToCart();
		ShoppingCartPage shoppingCartPage = computersPage.getShoppingCartPage();
		
		for (String productTitle : productTitles) {
	        // Get the price by clicking the edit button
	        Float priceFromEdit = shoppingCartPage.clickEditReturnItemPrice(productTitle);
	        
	        shoppingCartPage.shoppingCartMenuItem.click();
	        
	        // Get the item details by title
	        Map<String, Float> itemDetails = shoppingCartPage.getItemDetailsByTitle(productTitle);
	        Float unitPriceFromDetails = itemDetails.get("unitPrice");
	        
	        // Compare the prices and print the result
	        if (priceFromEdit != null && unitPriceFromDetails != null) {
	            if (priceFromEdit.equals(unitPriceFromDetails)) {
	                System.out.println("Price match for '" + productTitle + "': " + priceFromEdit);
	            } else {
	                System.out.println("Price mismatch for '" + productTitle + "'. " +
	                                   "Price from Edit: " + priceFromEdit + ", " +
	                                   "Price from Details: " + unitPriceFromDetails);
	            }
	        } else {
	            System.out.println("Price comparison could not be performed for '" + productTitle + 
	                               "'. Price from Edit: " + priceFromEdit + 
	                               ", Price from Details: " + unitPriceFromDetails);
	        }
	    }
	}
	/**
     * Tests removing an item from the cart and validates that the total price updates correctly.
     */
	@Test(priority = 5)
	public void testRemoveItemFromCartAndValidatePrice() {
		
		HomePage homepage = new HomePage (driver);
		LogInPage logInPage = homepage.clickLogInButton();
		logInPage.logInReturningUser(email, password);
		ShoppingCartPage shoppingCartPage = homepage.clickShoppingCartButton();
		shoppingCartPage.removeFromCartTickBox.click();
		shoppingCartPage.updateCartButton.click();
		Float totalPriceDisplayed = (Float.parseFloat(shoppingCartPage.totalPrice.getText()));
		List<Float> subTotals = shoppingCartPage.getSubTotals();
		Float allSubTotalsAddedUp = shoppingCartPage.addSubTotals(subTotals);
		Assert.assertEquals(totalPriceDisplayed, allSubTotalsAddedUp, "Sum of subtotals is NOT equal to total price!");
		System.out.printf("Assertion passed: Sum of subtotals (%.2f) EQUAL to total price (%.2f).%n", allSubTotalsAddedUp, totalPriceDisplayed);
	}
	/**
     * Tests the checkout process and verifies that the order number is correct and appears in the account.
     */
	@Test(priority = 6)
	public void testPerformCheckOut () {
		
		HomePage homepage = new HomePage (driver);
		
		LogInPage logInPage = homepage.clickLogInButton();
		logInPage.logInReturningUser(email, password);
		ShoppingCartPage shoppingCartPage= homepage.clickShoppingCartButton();
		CheckoutPage checkoutPage = shoppingCartPage.getCheckOutPage();
		checkoutPage.fillInNewBillingAddress("South Africa", "Cape Town", "test", "1111", "021");
		String orderNumber = checkoutPage.checkoutAndReturnOrderNumber();
		
		String expectedMessage = "Your order has been successfully processed!";
		String actualMessage = checkoutPage.getSuccessfulOrderMessage();
		Assert.assertEquals(actualMessage, expectedMessage, "Order success could not be confirmed.");

		
		AccountPage accountPage = homepage.clickAccount(email);
		String myAccountOrderNumber = accountPage.getOrderNumber();
		
		Assert.assertTrue(orderNumber.equalsIgnoreCase(myAccountOrderNumber), "Order does not appear in my account");
		System.out.println("Navigated to my account and validated order is created");
		
	}
	
	
		
}

