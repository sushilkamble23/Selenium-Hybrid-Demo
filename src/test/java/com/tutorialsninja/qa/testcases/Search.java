package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;

public class Search extends Base{

	WebDriver driver;

	@BeforeMethod()
	public void setUp() {

		driver = initializeBrowserAndOpenApplicationURL("chrome");

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void verifySearchWithValidProduct() {
		driver.findElement(By.name("search")).sendKeys("HP");
		driver.findElement(By.xpath("//div[@id='search']/descendant::button")).click();
		Assert.assertTrue(driver.findElement(By.linkText("HP LP3065")).isDisplayed());
	}

	@Test(priority = 2)
	public void verifySearchWithInvalidProduct() {
		driver.findElement(By.name("search")).sendKeys("HONDA");
		driver.findElement(By.xpath("//div[@id='search']/descendant::button")).click();
		String actualErrorMessage = driver.findElement(By.xpath("//div[@id='content']/h2/following-sibling::p"))
				.getText();
		Assert.assertEquals(actualErrorMessage, "There is no product that matches the search criteria.",
				"No product found message is not displayed");
	}
	
	@Test(priority = 3)
	public void verifySearchWithoutProduct() {
		
		driver.findElement(By.xpath("//div[@id='search']/descendant::button")).click();
		String actualErrorMessage = driver.findElement(By.xpath("//div[@id='content']/h2/following-sibling::p"))
				.getText();
		Assert.assertEquals(actualErrorMessage, "There is no product that matches the search criteria.",
				"No product found message is not displayed");
	}

}
