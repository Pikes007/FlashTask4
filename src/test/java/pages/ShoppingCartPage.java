package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tests.BaseTest;

public class ShoppingCartPage extends BaseTest {
	
	private WebDriverWait wait;
	
	public ShoppingCartPage (WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//div[@class='order-summary-content' and contains(text(),'Your Shopping Cart is empty!')]") 
	public WebElement shoppingCartEmpty;
	@FindBy(xpath = "//input[@name='removefromcart']") 
	public WebElement removeFromCartTickBox;
	@FindBy(xpath = "//input[@name='updatecart']") 
	public WebElement updateCartButton;
	@FindBy(xpath = "//tr[@class='cart-item-row']") 
	public List<WebElement> cartItemRows;
	@FindBy(xpath = "//a[contains(text(),'Edit')]") 
	public WebElement cartItemEditButtons;
	@FindBy(xpath = "//span[@itemprop='price']") 
	public WebElement productPriceContainer;
	@FindBy(xpath = "//span[@class='product-unit-price']") 
	public WebElement unitPrice;
	@FindBy(xpath = "//span[normalize-space()='Shopping cart']") 
	public WebElement shoppingCartMenuItem;
	@FindBy(xpath = "//span[@class='product-price order-total']") 
	public WebElement totalPrice;
	@FindBy(xpath = "//span[@class='product-subtotal']") 
	public List<WebElement> subTotals;
	@FindBy(xpath = "//button[@id='checkout']") 
	public WebElement checkoutButton;
	@FindBy(xpath = "//input[@id='termsofservice']") 
	public WebElement termsOfServiceTickBox;

	/**
     * Checks if the shopping cart is empty by verifying the presence of an empty cart message.
     * 
     * @return True if the shopping cart is empty, false otherwise.
     */
	public boolean isShoppingCartEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOf(shoppingCartEmpty));
            return shoppingCartEmpty.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            // If the element is not found or not visible within the timeout, it means the cart is not empty
            return false;
        }
    }
	/**
     * Clicks the edit button for a product based on its title and retrieves its price.
     * 
     * @param productTitle The title of the product to edit.
     * @return The price of the product, or NaN if the product or price could not be found.
     */
	public Float clickEditReturnItemPrice (String productTitle) {
		// Wait for the shopping cart page to load and check if the cart is empty
	    if (isShoppingCartEmpty()) {
	        System.out.println("The shopping cart is empty. No items to edit.");
	        return Float.NaN;
	    }
	    String dynamicXPath = "//a[@class='product-name'][normalize-space()='" + productTitle + "']";
	    try {
	        // Wait for the element to be present and clickable
	        WebElement editButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dynamicXPath)));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);
	        editButton.click();
	        float productPrice = Float.parseFloat(productPriceContainer.getText());
	        return productPrice;
	        
	    } catch (TimeoutException e) {
	        System.out.println("The edit button for the product '" + productTitle + "' was not found within the timeout period.");
	        return Float.NaN;
	    }
	}
	/**
     * Retrieves the details of a product based on its title, including unit price, quantity, and subtotal.
     * 
     * @param productTitle The title of the product to retrieve details for.
     * @return A map containing the unit price, quantity, and subtotal of the product.
     */	
	public Map<String, Float> getItemDetailsByTitle(String productTitle) {
	    Map<String, Float> itemDetails = new HashMap<>();

	    if (isShoppingCartEmpty()) {
	        System.out.println("The shopping cart is empty. No items to retrieve.");
	        return itemDetails;
	    }

	    String dynamicXPathBase = "//tr[@class='cart-item-row'][.//a[@class='product-name'][normalize-space()='" + productTitle + "']]";
	    String unitPriceXPath = dynamicXPathBase + "//span[@class='product-unit-price']";
	    String qtyXPath = dynamicXPathBase + "//input[@class='qty-input']";
	    String subtotalXPath = dynamicXPathBase + "//span[@class='product-subtotal']";

	    try {
	        WebElement unitPriceElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(unitPriceXPath)));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", unitPriceElement);

	        float unitPrice = Float.parseFloat(unitPriceElement.getText().trim());
	        int quantity = Integer.parseInt(driver.findElement(By.xpath(qtyXPath)).getAttribute("value").trim());
	        float subtotal = Float.parseFloat(driver.findElement(By.xpath(subtotalXPath)).getText().trim());

	        itemDetails.put("unitPrice", unitPrice);
	        itemDetails.put("quantity", (float) quantity);
	        itemDetails.put("subtotal", subtotal);

	    } catch (TimeoutException e) {
	        System.out.println("The details for the product '" + productTitle + "' were not found within the timeout period.");
	    }

	    return itemDetails;
	}
	/**
     * Retrieves the subtotal values for all items in the shopping cart.
     * 
     * @return A list of subtotal values for each item in the cart, or an empty list if the cart is empty.
     */
	public List<Float> getSubTotals(){
		
		if (isShoppingCartEmpty()) {
	        System.out.println("The shopping cart is empty. No items to edit.");
	        return Collections.emptyList();
		}
		
		List<Float> subTotalValues = new ArrayList<>();
	    for (WebElement subtotalElement : subTotals) {
	        String subtotalText = subtotalElement.getText();
	        try {
	            Float subtotal = Float.parseFloat(subtotalText.trim());  // Convert text to Float
	            subTotalValues.add(subtotal);
	        } catch (NumberFormatException e) {
	            System.err.println("Unable to parse subtotal: " + subtotalText);
	        }
	    }
	    
	    return subTotalValues;
	}
	/**
     * Sums up the subtotal values provided.
     * 
     * @param subTotalValues A list of subtotal values.
     * @return The sum of all subtotal values.
     */
	public float addSubTotals(List<Float> subTotalValues) {
	    float sum = 0.0f;
	    for (Float value : subTotalValues) {
	        sum += value;
	    }
	    return sum;
	}
	/**
     * Clicks the terms of service checkbox and the checkout button to proceed to the checkout page.
     * 
     * @return A CheckoutPage object representing the checkout page.
     */
	public CheckoutPage getCheckOutPage () {
		
		termsOfServiceTickBox.click();
		checkoutButton.click();
		
		return new CheckoutPage(driver);
		
		
	}
}

