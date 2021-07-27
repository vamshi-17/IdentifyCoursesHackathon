package com.test.classes;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import com.base.classes.BaseClass;
import com.base.classes.GetProperties;
import com.framework.utilities.ExtentReportManager;
import com.page.classes.HomePage;
import com.page.classes.LanguageLearning;
import com.page.classes.SearchCourse;

public class LanguageLearningTest {

	public static WebDriver driver;
	public static WebDriverWait wait;
	// Extent report
	public static ExtentReports report;
	public static ExtentTest logger;

	BaseClass baseclass;
	HomePage homepage;
	SearchCourse searchcourse;
	LanguageLearning languagelearning;

	@BeforeClass(groups = { "Regression" })
	public void invokeBrowser() {
		
		/* Invoking the browser */
		// The browser name is got from the properties file
		// Report and logger is instantiated
		
		System.out.println("***************  Working with Test Scenario-2 ---<getting languages and different type of levels>--- ***************");
		String browser = GetProperties.getBrowser1();
		String Testname = "LanguageLearningTestReport";
		report = ExtentReportManager.getReportInstance(browser, Testname);
		logger = report.createTest("Language Learning");

		baseclass = new BaseClass(driver, logger);

		boolean isvalid = baseclass.validateBrowser(browser);
		AssertJUnit.assertTrue(isvalid);

		// All the page classes are instantiated
		logger.log(Status.INFO, "Opened the browser ");
		driver = baseclass.openBrowser(browser);
		logger.log(Status.PASS, "Successfully opened the " + browser + " browser");
		homepage = new HomePage(driver, logger);
		searchcourse = new SearchCourse(driver, logger);
		languagelearning = new LanguageLearning(driver, logger);
		baseclass.getUrl();

	}
	
	@Test(priority = 5, groups = { "Regression" })
	public void searchLanguageCourseTest() throws Exception {
				
		// Methods of the filling form class is called using the object of the class
		homepage.home("language learning");

	}

	@Test(priority = 6, groups = { "Regression" })
	public void languageSelectTest() {

		languagelearning.selectLanguage();
	}
	
	@Test(priority = 7, groups = {"Regression"})
	public void levelSelectTest() {

		languagelearning.selectLevel();
				
	}
	
	@AfterClass(groups = {"Regression"})
	public void endTest() throws Exception {
		
		// Closing the browser after the test implementation
		logger.log(Status.INFO, "Closing the browser");
		baseclass.closeBrowser();
		System.out.println("***************  Test Scenario-2 completed successfully  ***************");
		logger.log(Status.PASS, "Closed browser successfully");
		report.flush();

	}

}
