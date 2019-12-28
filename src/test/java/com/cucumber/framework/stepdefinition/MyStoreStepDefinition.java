package com.cucumber.framework.stepdefinition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.cucumber.framework.helper.BrowserHelper;
import com.cucumber.framework.helper.GenericHelper;
import com.cucumber.framework.helper.LoginHelper;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 * @author kanatala
 *
 */
public class MyStoreStepDefinition {
	
	GenericHelper helper = new GenericHelper();	
	LoginHelper loginHelper = new LoginHelper();
	BrowserHelper browserHelper = new BrowserHelper();
	String item = null;
	
	@Before
	public void setup() {
		GenericHelper.loadConfig();
		browserHelper.launchChrome();
		browserHelper.navigateToMyStore();		
	}
	
	@Given("^I login to My Store$")
	public void loginToMyStore() {
		loginHelper.login();
	} 
	
	@When("^I update my first name to \"([^\"]*)\"$")
	public void updatePersonalDetails(String newName) {
		helper.updateFirstName(newName);
	}
	
	@Then("^I verify that my first name is updated to \"([^\"]*)\"$")
	public void verifyUserName(String nameToVerify) {
		assertTrue(helper.verifyNameUpdate(nameToVerify));
	}
		
	@When("^I place an order$")
	public void placeAnOrder() {
		item = helper.placeOrder();
	}
	
	@Then("^I verify that order is seen in account order history$")
	public void verifyOrderHistory() {
		String itemOrdered = helper.verifyOrderHistory();
		assertEquals(item, itemOrdered);
	}
	
	@After
	public void teardown() {
		loginHelper.logout();
		browserHelper.closeBrowser();
	}
}
