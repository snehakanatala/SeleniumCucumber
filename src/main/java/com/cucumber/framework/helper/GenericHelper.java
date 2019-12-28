package com.cucumber.framework.helper;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * @author kanatala
 *
 * Helper Class for 
 * placing an order,
 * verifying purchase,
 * updating users first name,
 * verifying users name 
 */

public class GenericHelper extends BasePage {

	public static Properties properties;
		
	public static void loadConfig() {	
		PropertiesReader propertiesReader = new PropertiesReader();
		properties = propertiesReader.loadProperties();
	}	

	public void updateFirstName(String newName) {
		WebElement header = BasePage.webDriver.findElement(By.id("header"));
		WebElement userInfo = header.findElement(By
				.xpath("./div/div/div/nav/div/a[@title='View my customer account']"));
		userInfo.click();
		WebElement personalInfoTab = BasePage.webDriver.findElement(By
				.xpath("//a[@title='Information']"));
		personalInfoTab.click();
		implicitWaitForPageLoad();
		BasePage.webDriver.findElement(By.id("firstname")).clear();
		BasePage.webDriver.findElement(By.id("firstname")).sendKeys(newName);
		String lastName = BasePage.webDriver.findElement(By.id("lastname")).getAttribute("value");
		BasePage.webDriver.findElement(By.id("old_passwd")).sendKeys(properties.getProperty("password"));		
		WebElement submitButton = BasePage.webDriver.findElement(By
				.xpath("//fieldset/div/button[@name='submitIdentity']"));
		submitButton.click();
		implicitWaitForPageLoad();
		String updatedName = newName + " " + lastName;
		System.out.println("User's name updated to " + updatedName);
	}
	
	public boolean verifyNameUpdate(String newFirstName) {
		WebElement header = BasePage.webDriver.findElement(By.id("header"));
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
		WebElement homepage = BasePage.webDriver.findElement(By.className("home"));
		homepage.click();
	}
	
	public void ViewItemAndAddToCart() {	
		implicitWaitForPageLoad();
		WebElement topMenuBar = BasePage.webDriver.findElement(By.id("block_top_menu"));
		WebElement tShirtsMenu = topMenuBar.findElement(By
				.xpath("./ul/li/a[@title='T-shirts']"));
		tShirtsMenu.click();
		implicitWaitForPageLoad();
		WebElement wantedItemContainer = BasePage.webDriver.findElement(By
				.className("product-container"));
		WebElement itemImage = wantedItemContainer.findElement(By
				.xpath("./div/div/a/img[@title='Faded Short Sleeve T-shirts']"));
		Actions action = new Actions(BasePage.webDriver);		
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
		String productTitle = BasePage.webDriver.findElement(By
				.id("layer_cart_product_title")).getText();
		String productAttributes = BasePage.webDriver.findElement(By
				.id("layer_cart_product_attributes")).getText();		
		String productColour = productAttributes.substring(0, productAttributes.indexOf(','));		
		String productSize = productAttributes.substring(productColour.length()+2, productAttributes.length());		
		String item = productTitle + " - Color : " + productColour + ", Size : " + productSize;	
		return item;
	}
	
	public void checkOut() {
		WebElement proceedCheckout = BasePage.webDriver.findElement(By.id("layer_cart")).findElement(By
				.xpath("./div/div/div/a[@title='Proceed to checkout']"));
		proceedCheckout.click();
		WebElement proceedCheckout2 = BasePage.webDriver.findElement(By.id("center_column")).findElement(By
				.xpath("./p/a[@title='Proceed to checkout']"));
		proceedCheckout2.click();
		WebElement proceedCheckout3 = BasePage.webDriver.findElement(By.id("center_column")).findElement(By
				.xpath("./form/p/button[@name='processAddress']"));
		proceedCheckout3.click();
		WebElement agreeToTerms = BasePage.webDriver.findElement(By.id("cgv"));
		agreeToTerms.click();
		WebElement proceedCheckout4 = BasePage.webDriver.findElement(By.id("form")).findElement(By
				.xpath("./p/button[@name='processCarrier']"));
		proceedCheckout4.click();
	}
	
	public void payAndConfirm() {
		WebElement paymentOptions = BasePage.webDriver.findElement(By.id("HOOK_PAYMENT"));
		WebElement wirePayment = paymentOptions.findElement(By
				.xpath("./div/div/p/a[@title='Pay by bank wire']"));
		wirePayment.click();	
		WebElement orderConfirm = BasePage.webDriver.findElement(By.id("cart_navigation"))
				.findElement(By.xpath("./button[@type='submit']"));
		orderConfirm.click();
	}
	
	public String verifyOrderHistory() {
		WebElement header = BasePage.webDriver.findElement(By.id("header"));
		WebElement userInfo = header.findElement(By.xpath("./div/div/div/nav/div/a[@title='View my customer account']"));
		userInfo.click();
		WebElement orderHistory = BasePage.webDriver.findElement(By.xpath("//a[@title='Orders']"));
		orderHistory.click();
		WebElement orderHistoryTable = BasePage.webDriver.findElement(By.id("order-list"));
		WebElement recentOrderBar = orderHistoryTable.findElement(By.xpath("./tbody/tr[@class='first_item ']"));
		WebElement recentOrder = recentOrderBar.findElement(By.xpath("./td/a[@class='color-myaccount']"));
		recentOrder.click();
		WebElement orderDetailContent = BasePage.webDriver.findElement(By.id("order-detail-content"));
		String itemOrdered = orderDetailContent.findElement(By
				.xpath("./table/tbody/tr/td[@class='bold']"))
				.findElement(By.xpath("./label")).getText();		
		return itemOrdered;		
	}
	
	public void implicitWaitForPageLoad() {
		BasePage.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}
