package com.tutorialsninja.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.utils.Utilities;

public class Register {
	WebDriver driver;

	@BeforeMethod()
	public void setUp() {

		String browserName = "chrome";
		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(8));
		driver.get("https://tutorialsninja.com/demo/");

		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 1)
	public void verifyRegisterAccountWithMandatoryFields() {

		driver.findElement(By.id("input-firstname")).sendKeys("sushil");
		driver.findElement(By.id("input-lastname")).sendKeys("kamble");
		driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailWithTimeStamp());
		driver.findElement(By.id("input-telephone")).sendKeys("9998887771");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.id("input-confirm")).sendKeys("12345");
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualSuccessHeading = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		Assert.assertEquals(actualSuccessHeading, "Your Account Has Been Created!",
				"Account successfully created message is not displayed");
	}

	@Test(priority = 2)
	public void verifyRegisterAccountWithAllFields() {

		driver.findElement(By.id("input-firstname")).sendKeys("sushil");
		driver.findElement(By.id("input-lastname")).sendKeys("kamble");
		driver.findElement(By.id("input-email")).sendKeys(Utilities.generateEmailWithTimeStamp());
		driver.findElement(By.id("input-telephone")).sendKeys("9998887771");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.id("input-confirm")).sendKeys("12345");
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@name='newsletter' and @value='1']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualSuccessHeading = driver.findElement(By.xpath("//div[@id='content']/h1")).getText();
		Assert.assertEquals(actualSuccessHeading, "Your Account Has Been Created!",
				"Account successfully created message is not displayed");
	}

	@Test(priority = 3)
	public void verifyRegisterAccountWithExistingEmailAddress() {

		driver.findElement(By.id("input-firstname")).sendKeys("sushil");
		driver.findElement(By.id("input-lastname")).sendKeys("kamble");
		driver.findElement(By.id("input-email")).sendKeys("sushil.kamble@test.com");
		driver.findElement(By.id("input-telephone")).sendKeys("9998887771");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.id("input-confirm")).sendKeys("12345");
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@name='newsletter' and @value='1']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualWarningMessage = driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]")).getText();
		Assert.assertTrue(actualWarningMessage.contains("Warning: E-Mail Address is already registered!"),
				"Expected warning regarding duplicate email address is not displayed");

	}
	@Test(priority=4)
	public void verifyRegisterAccountWithoutFillingAnyDetails() {

		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String actualPrivacyPolicyWarning = driver.findElement(By.xpath("//div[contains(@class,'alert-dismissible')]")).getText();
		Assert.assertTrue(actualPrivacyPolicyWarning.contains("Warning: You must agree to the Privacy Policy!"),
				"Privacy policy warning message is not displayed");
		
		String actualFirstNameWarning = driver.findElement(By.xpath("//input[@id='input-firstname']/following-sibling::div")).getText();
		Assert.assertEquals(actualFirstNameWarning,"First Name must be between 1 and 32 characters!",
				"First name warning message is not displayed");
		
		String actualLastNameWarning = driver.findElement(By.xpath("//input[@id='input-lastname']/following-sibling::div")).getText();
		Assert.assertEquals(actualLastNameWarning,"Last Name must be between 1 and 32 characters!",
				"Last name warning message is not displayed");
		
		String actualEmailWarning = driver.findElement(By.xpath("//input[@id='input-email']/following-sibling::div")).getText();
		Assert.assertEquals(actualEmailWarning,"E-Mail Address does not appear to be valid!",
				"Email warning message is not displayed");
		
		String actualTelephoneWarning = driver.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText();
		Assert.assertEquals(actualTelephoneWarning,"Telephone must be between 3 and 32 characters!",
				"Telephone warning message is not displayed");
		
		String actualPasswordWarning = driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div")).getText();
		Assert.assertEquals(actualPasswordWarning,"Password must be between 4 and 20 characters!",
				"Password warning message is not displayed");

	}
	
	
}
