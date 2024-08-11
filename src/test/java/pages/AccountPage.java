package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import tests.BaseTest;

public class AccountPage extends BaseTest {
	
	private WebDriverWait wait;
	
	public AccountPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@class='inactive'][normalize-space()='Orders']") 
	public WebElement ordersMenuOption;
	@FindBy(xpath = "//h1[normalize-space()='My account - Orders']") 
	public WebElement ordersMenuHeader;
	@FindBy(xpath = "//div[@class='section order-item']//div[@class='title']") 
	public WebElement orderNumberTitleHolder;
	
	/**
     * Clicks on the 'Orders' menu option and retrieves the text of the order number title holder.
     * 
     * @return The text of the order number title holder.
     */
	public String getOrderNumber () {
		
		ordersMenuOption.click();
		return orderNumberTitleHolder.getText();
	}
}
