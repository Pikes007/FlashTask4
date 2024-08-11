package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tests.BaseTest;

public class ComputersPage extends BaseTest {
	
	private WebDriverWait wait;
	
	public ComputersPage (WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//select[@id='products-orderby']") 
	public WebElement sortByDropdownElement;
	@FindBy(xpath = "//div[@class='item-box']") 
	public List<WebElement> productGridCommonClass;
	@FindBy(xpath = "//h2[@class = 'product-title']") 
	public List<WebElement> productTitles;
	@FindBy(xpath = "(//input[@value='Add to cart'])") 
	public WebElement addToCartButton;
	@FindBy(xpath = "(//input[@class='button-1 add-to-cart-button'])") 
	public WebElement addToCartButtonFinal;
	@FindBy(xpath = "//div[@id='bar-notification']") 
	public WebElement barNotification;
	@FindBy(xpath = "//input[@id='product_attribute_16_3_6_18']") 
	public WebElement hddButton320GB;
	@FindBy(xpath = "//input[@id='product_attribute_75_5_31_96']") 
	public WebElement processorButtonSlow;
	@FindBy(xpath = "//span[@title='Close']") 
	public WebElement notifyClose;
	@FindBy(xpath = "(//a[normalize-space()='Computers'])[1]") 
	public WebElement topMenuComputers;
	@FindBy(xpath = "//ul[@class='top-menu']//a[normalize-space()='Desktops']") 
	public WebElement subMenuDesktops;
	@FindBy(xpath = "//span[normalize-space()='Shopping cart']") 
	public WebElement shoppingCartIcon;
	
	 /**
     * Retrieves a list of product titles for products that have an 'Add to Cart' button.
     * 
     * @return A list of product titles that have an 'Add to Cart' button.
     */
	public List<String> getProductTitlesWithAddToCartButton() {
        List<String> titlesWithAddToCart = new ArrayList<>();
        List<WebElement> productsWithAddToCart = new ArrayList<>();
        
        for (int i = 0; i < productGridCommonClass.size(); i++) {
            WebElement product = productGridCommonClass.get(i);
            // Check if the 'Add to Cart' button exists within the current product
            List<WebElement> addToCartButtons = product.findElements(By.xpath(".//input[@value='Add to cart']"));
            
            if (!addToCartButtons.isEmpty() && addToCartButtons.get(0).isDisplayed()) {
                // Get the title of this product
                WebElement titleElement = productTitles.get(i);
                if (titleElement != null) {
                    titlesWithAddToCart.add(titleElement.getText());
                    productsWithAddToCart.add(product);
                }
            }
        }
        
        return titlesWithAddToCart;
    }
	/**
     * Adds all products with an 'Add to Cart' button to the shopping cart.
     * Iterates through each product, clicks the 'Add to Cart' button, handles product-specific options,
     * and ensures the addition is confirmed by handling the notification.
     */
	public void addAllProductsItemsToCart() {
	    List<String> titlesWithAddToCart = getProductTitlesWithAddToCartButton();
	    
	    for (String title : titlesWithAddToCart) {
	        // Find the product container based on the product title
	        WebElement product = driver.findElement(By.xpath("//h2[@class='product-title']/a[text()='" + title + "']/ancestor::div[@class='product-item']"));
	        
	        
	        // Locate the 'Add to Cart' button within this product
	        WebElement addToCartButton = product.findElement(By.xpath(".//input[@value='Add to cart']"));
	        
	        // Scroll into view and click the 'Add to Cart' button
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
	        addToCartButton.click();
	        
	     // Conditional checks based on the product title
	        if (title.equalsIgnoreCase("Build your own computer")) {
	            // Wait for the HDD option (320GB) to be clickable, scroll into view, and then click
	            wait.until(ExpectedConditions.elementToBeClickable(hddButton320GB));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hddButton320GB);
	            hddButton320GB.click();
	        } 
	        if (title.equalsIgnoreCase("Simple Computer")) {
	            // Wait for the Processor option (Slow) to be clickable, scroll into view, and then click
	            wait.until(ExpectedConditions.elementToBeClickable(processorButtonSlow));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", processorButtonSlow);
	            processorButtonSlow.click();
	        }


	        // Wait for the notification to confirm the item was added to the cart
	        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonFinal)).click();
	        wait.until(ExpectedConditions.attributeToBe(barNotification, "style", "display: block;"));
	        notifyClose.click();

	        // Bring the top menu 'Computers' into view before hovering
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", topMenuComputers);
	        wait.until(ExpectedConditions.visibilityOf(topMenuComputers));
	        wait.until(ExpectedConditions.elementToBeClickable(topMenuComputers));

	        // Hover over the 'Computers' menu and select 'Desktops'
	        hoverAndSelect(topMenuComputers, subMenuDesktops);
	    }
	}
	/**
     * Clicks the shopping cart icon and returns an instance of the ShoppingCartPage.
     * 
     * @return A ShoppingCartPage object representing the shopping cart page.
     */
	public ShoppingCartPage getShoppingCartPage(){
		shoppingCartIcon.click();
		return new ShoppingCartPage(driver);
		
	}
}