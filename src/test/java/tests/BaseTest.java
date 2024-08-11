package tests;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    protected String baseUrl = "https://demowebshop.tricentis.com/";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        
        
        ChromeOptions options = new ChromeOptions();
        
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-features=PasswordManager");
        options.addArguments("--disable-features=SavePasswords");
      
        driver = new ChromeDriver(options);  
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterMethod
    public void tearDown() {
        // Close the WebDriver instance
        if (driver != null) {
            driver.quit();
        }
    }
    /**
     * Performs a hover action over a top menu element and then clicks on a sub-menu element.
     * Utilizes the Actions class to simulate the hover action and waits for the sub-menu element
     * to become visible and clickable before clicking it.
     *
     * @param topMenuElement The WebElement representing the top menu item to hover over.
     * @param subMenuElement The WebElement representing the sub-menu item to click on.
     */
    
    public void hoverAndSelect(WebElement topMenuElement, WebElement subMenuElement) {
		Actions actions = new Actions(driver);
		actions.moveToElement(topMenuElement).perform();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(subMenuElement));
	    wait.until(ExpectedConditions.elementToBeClickable(subMenuElement));
		actions.moveToElement(subMenuElement).click().perform();
	}
    /**
     * Selects an option from a dropdown menu based on the visible text of the option.
     * Uses the Select class to interact with the dropdown element.
     *
     * @param dropdownElement The WebElement representing the dropdown menu.
     * @param valueToSelect The visible text of the option to select from the dropdown.
     */
    
    public void selectFromDropdownByValue(WebElement dropdownElement, String valueToSelect) {
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText(valueToSelect);
	}
}