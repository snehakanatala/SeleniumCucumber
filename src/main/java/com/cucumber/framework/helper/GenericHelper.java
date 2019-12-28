package com.cucumber.framework.helper;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * @author kanatala
 *
 */
public class GenericHelper extends BasePage {

	public WebDriver driver = BasePage.webDriver;
	boolean loggedin = false;
	public Properties properties;
		
	public void loadConfig() {	
		PropertiesReader propertiesReader = new PropertiesReader();
		properties = propertiesReader.loadProperties();
	}	
	
	public void launchChrome() {		
		System.setProperty("webdriver.chrome.driver", 
				"src\\main\\resources\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	public void navigateToMyStore() {		
		driver.navigate().to(properties.getProperty("storeUrl"));	
	}
	
	public void login() {		
		if(!loggedin) {
			try {			
				driver.findElement(By.className("login")).click();
				implicitWaitForPageLoad();
				driver.findElement(By.id("email")).sendKeys(properties.getProperty("email"));
				driver.findElement(By.id("passwd")).sendKeys(properties.getProperty("password"));
				driver.findElement(By.id("SubmitLogin")).click();
				loggedin = true;
				System.out.println("Login Successful");
			} catch(final Exception e) {
				System.out.println("Login Failed");
				System.out.println(e);
				loggedin = false;
			}
		}
	}

	public void logout() {
		if(loggedin) {
			driver.findElement(By.className("logout")).click();
		}
	}

	public void closeBrowser() {
		driver.close();
		driver.quit();
	}
	
	public void updateFirstName(String newName) {
		WebElement header = driver.findElement(By.id("header"));
		WebElement userInfo = header.findElement(By
				.xpath("./div/div/div/nav/div/a[@title='View my customer account']"));
		userInfo.click();
		WebElement personalInfoTab = driver.findElement(By
				.xpath("//a[@title='Information']"));
		personalInfoTab.click();
		implicitWaitForPageLoad();
		driver.findElement(By.id("firstname")).clear();
		driver.findElement(By.id("firstname")).sendKeys(newName);
		String lastName = driver.findElement(By.id("lastname")).getAttribute("value");
		driver.findElement(By.id("old_passwd")).sendKeys(properties.getProperty("password"));		
		WebElement submitButton = driver.findElement(By
				.xpath("//fieldset/div/button[@name='submitIdentity']"));
		submitButton.click();
		implicitWaitForPageLoad();
		String updatedName = newName + " " + lastName;
		System.out.println("User's name updated to " + updatedName);
	}
	
	public boolean verifyNameUpdate(String newFirstName) {
		WebElement header = driver.findElement(By.id("header"));
		WebElement userInfo = header.findElement(By
				.xpath("./div/div/div/nav/div/a[@title='View my customer account']"));
		String updatedName = newFirstName + " " + properties.getProperty("lastName");
		if(userInfo.getText().equalsIgnoreCase(updatedName))
			return true;
		else
			return false;
	}	
	
	public String placeOrder() {
		String item = null;	
		goToHomePage();
		ViewItemAndAddToCart();		
		item = getItemAttributes();	
		checkOut();		
		payAndConfirm();
		System.out.println("Item purchased: " + item);
		return item;
	}
	
	public void goToHomePage() {
		implicitWaitForPageLoad();
		WebElement homepage = driver.findElement(By.className("home"));
		homepage.click();
	}
	
	public void ViewItemAndAddToCart() {	
		implicitWaitForPageLoad();
		WebElement topMenuBar = driver.findElement(By.id("block_top_menu"));
		WebElement tShirtsMenu = topMenuBar.findElement(By
				.xpath("./ul/li/a[@title='T-shirts']"));
		tShirtsMenu.click();
		implicitWaitForPageLoad();
		WebElement wantedItemContainer = driver.findElement(By
				.className("product-container"));
		WebElement itemImage = wantedItemContainer.findElement(By
				.xpath("./div/div/a/img[@title='Faded Short Sleeve T-shirts']"));
		Actions action = new Actions(driver);		
		action.moveToElement(itemImage).perform();
		action.moveToElement(wantedItemContainer.findElement(By
				.xpath("./div/div/a[@title='Add to cart']"))).click().perform();
		//wait for 3 seconds to load load the form (not page load)
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getItemAttributes() {
		String productTitle = driver.findElement(By
				.id("layer_cart_product_title")).getText();
		String productAttributes = driver.findElement(By
				.id("layer_cart_product_attributes")).getText();		
		String productColour = productAttributes.substring(0, productAttributes.indexOf(','));		
		String productSize = productAttributes.substring(productColour.length()+2, productAttributes.length());		
		String item = productTitle + " - Color : " + productColour + ", Size : " + productSize;	
		return item;
	}
	
	public void checkOut() {
		WebElement proceedCheckout = driver.findElement(By.id("layer_cart")).findElement(By
				.xpath("./div/div/div/a[@title='Proceed to checkout']"));
		proceedCheckout.click();
		WebElement proceedCheckout2 = driver.findElement(By.id("center_column")).findElement(By
				.xpath("./p/a[@title='Proceed to checkout']"));
		proceedCheckout2.click();
		WebElement proceedCheckout3 = driver.findElement(By.id("center_column")).findElement(By
				.xpath("./form/p/button[@name='processAddress']"));
		proceedCheckout3.click();
		WebElement agreeToTerms = driver.findElement(By.id("cgv"));
		agreeToTerms.click();
		WebElement proceedCheckout4 = driver.findElement(By.id("form")).findElement(By
				.xpath("./p/button[@name='processCarrier']"));
		proceedCheckout4.click();
	}
	
	public void payAndConfirm() {
		WebElement paymentOptions = driver.findElement(By.id("HOOK_PAYMENT"));
		WebElement wirePayment = paymentOptions.findElement(By
				.xpath("./div/div/p/a[@title='Pay by bank wire']"));
		wirePayment.click();	
		WebElement orderConfirm = driver.findElement(By.id("cart_navigation"))
				.findElement(By.xpath("./button[@type='submit']"));
		orderConfirm.click();
	}
	
	public String verifyOrderHistory() {
		WebElement header = driver.findElement(By.id("header"));
		WebElement userInfo = header.findElement(By.xpath("./div/div/div/nav/div/a[@title='View my customer account']"));
		userInfo.click();
		WebElement orderHistory = driver.findElement(By.xpath("//a[@title='Orders']"));
		orderHistory.click();
		WebElement orderHistoryTable = driver.findElement(By.id("order-list"));
		WebElement recentOrderBar = orderHistoryTable.findElement(By.xpath("./tbody/tr[@class='first_item ']"));
		WebElement recentOrder = recentOrderBar.findElement(By.xpath("./td/a[@class='color-myaccount']"));
		recentOrder.click();
		WebElement orderDetailContent = driver.findElement(By.id("order-detail-content"));
		String itemOrdered = orderDetailContent.findElement(By
				.xpath("./table/tbody/tr/td[@class='bold']"))
				.findElement(By.xpath("./label")).getText();		
		return itemOrdered;		
	}
	
	public void implicitWaitForPageLoad() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}
