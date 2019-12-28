package com.cucumber.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * 
 * @author kanatala
 *
 */

@CucumberOptions(features = { "classpath:featurefile/myStore.feature" }, glue = {
		"classpath:com.cucumber.framework.stepdefinition",
		"classpath:com.cucumber.framework.helper" }, plugin = { "pretty",
		"json:target/MyStoreTestRunner.json" })
public class MyStoreTestRunner extends AbstractTestNGCucumberTests {

}
