package com.techpanda.account;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;

public class Account_01_Register {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("Webdriver.gecko.driver", projectPath + " \\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
	  }
	
  @Test
  public void Register_01_Empty_Data() {
  }
  
  @Test
  public void Register_02_Invalid_Email() {
  }
  
  @Test
  public void Register_03_Invalid_Password() {
  }
  

  @AfterClass
  public void afterClass() {
	  driver.quit(); 
  }

}
