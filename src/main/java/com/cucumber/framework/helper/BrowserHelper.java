package com.cucumber.framework.helper;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * @author kanatala
 *
 * Helper class for browser functions
 */
public class BrowserHelper {

	public void launchChrome() {		
		System.setProperty("webdriver.chrome.driver", 
				GenericHelper.properties.getProperty("winChromeDriverPath"));
		BasePage.webDriver = new ChromeDriver();
		BasePage.webDriver.manage().window().maximize();
	}

	public void navigateToMyStore() {		
		BasePage.webDriver.navigate().to(GenericHelper.properties.getProperty("storeUrl"));	
	}
	
	public void closeBrowser() {
		BasePage.webDriver.close();
		BasePage.webDriver.quit();
	}
}
