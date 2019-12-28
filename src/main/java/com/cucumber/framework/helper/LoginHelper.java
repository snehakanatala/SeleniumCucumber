package com.cucumber.framework.helper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

/**
 * 
 * @author kanatala
 *
 *	Helper class for authorization functions
 */
public class LoginHelper {
	
	boolean loggedin = false;
	
	public void login() {		
		if(!loggedin) {
			try {		
				BasePage.webDriver.findElement(By.className("login")).click();
				BasePage.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				BasePage.webDriver.findElement(By.id("email"))
						.sendKeys(GenericHelper.properties.getProperty("email"));
				BasePage.webDriver.findElement(By.id("passwd"))
						.sendKeys(GenericHelper.properties.getProperty("password"));
				BasePage.webDriver.findElement(By.id("SubmitLogin")).click();
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
			BasePage.webDriver.findElement(By.className("logout")).click();
		}
	}
}
