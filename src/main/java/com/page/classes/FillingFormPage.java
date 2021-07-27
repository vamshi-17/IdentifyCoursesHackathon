package com.page.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.base.classes.BaseClass;

public class FillingFormPage extends BaseClass {

	String FirstName, LastName, JobFunction, JobTitle, Email, phone;
	String InstituteName, InstituteType, PrimaryDisipline, Country, State, parentWindow;
	FileInputStream stream;
	XSSFWorkbook book;
	XSSFSheet sheet;
	
	@FindBy(xpath = "//a[@id='enterprise-link']")
	public static WebElement Enterprise;

	@FindBy(linkText = "Products")
	WebElement products;

	@FindBy(xpath = "//*[@id='menu-item-4901']")
	public static WebElement forCampus;

	@FindBy(id = "FirstName")
	public static WebElement Fname;

	@FindBy(id = "LastName")
	public static WebElement Lname;

	@FindBy(id = "C4C_Job_Title__c")
	public static WebElement Jobfunction;

	@FindBy(id = "Title")
	public static WebElement Jobtitle;

	@FindBy(id = "Email")
	public static WebElement EmailId;

	@FindBy(id = "Phone")
	public static WebElement Phone;

	@FindBy(id = "Company")
	public static WebElement Institutename;

	@FindBy(id = "Institution_Type__c")
	public static WebElement InstituteTypedrop;

	@FindBy(id = "Primary_Discipline__c")
	public static WebElement PrimaryDisiplinedrop;

	@FindBy(id = "Country")
	public static WebElement Countrydrop;

	@FindBy(id = "State")
	public static WebElement Statedrop;

	@FindBy(xpath = "//*[@id=\"mktoForm_1512\"]/div[20]/span/button")
	public static WebElement Submit;

	@FindBy(id = "ValidMsgEmail")
	public static WebElement ErrorMessage;

	@FindBy(xpath = "//h2[contains(text(),'Ready to transform your campus?')]")
	public static WebElement FormHead;

	@FindBy(xpath = "//h1[contains(text(),'Thank you for your interest in Coursera for Campus')]")
	public static WebElement title;

	String screenshot = "FillingFormShot";
	static String projectPath = System.getProperty("user.dir");

	public FillingFormPage(WebDriver l_driver, ExtentTest logger) {

		super(l_driver, logger);
		PageFactory.initElements(driver, this);

	}

	public void ForEnterprise() {

		// Click "For Enterprise"
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.elementToBeClickable(Enterprise)).click();

	}

	public void OpenForm() {

		// Look for Campus under Product
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		parentWindow = driver.getWindowHandle();
		Actions action = new Actions(driver);
		logger.log(Status.INFO, "Clicking the product link");
		
		//Hover the mouse pointer on Products
		action.moveToElement(products);
		action.build().perform();
		logger.log(Status.PASS, "The product link is selected");
		
		//Clicking the For Campus Link
		logger.log(Status.INFO, "Clicking the for campus link");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		forCampus.click();
		logger.log(Status.PASS, "For campus link is selected");

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public void getUserDetails() throws IOException {
		
		// Locate the excel sheet to be read
		File src = new File(projectPath + "\\resources\\FormFillingTestData.xlsx");
		FileInputStream stream = new FileInputStream(src);
		book = new XSSFWorkbook(stream);
		sheet = book.getSheet("Sheet1");

		// Fill the "Ready to transform" form from the data given in excel sheet
		FirstName = sheet.getRow(1).getCell(0).getStringCellValue();
		LastName = sheet.getRow(1).getCell(1).getStringCellValue();
		JobFunction = sheet.getRow(1).getCell(2).getStringCellValue();
		JobTitle = sheet.getRow(1).getCell(3).getStringCellValue();
		Email = sheet.getRow(1).getCell(4).getStringCellValue();
		long PhoneNumber = (long) sheet.getRow(1).getCell(5).getNumericCellValue();
		// Change the phone number from integer to string
		phone = String.valueOf(PhoneNumber);
		InstituteName = sheet.getRow(1).getCell(6).getStringCellValue();
		InstituteType = sheet.getRow(1).getCell(7).getStringCellValue();
		PrimaryDisipline = sheet.getRow(1).getCell(8).getStringCellValue();
		Country = sheet.getRow(1).getCell(9).getStringCellValue();
		State = sheet.getRow(1).getCell(10).getStringCellValue();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	public void setUserDetails() {
		
		Set<String> handle = driver.getWindowHandles();
		for (String childWindow : handle) {
			driver.switchTo().window(childWindow);
		}

		try {

			// Fill the "Ready to transform" form from the data given in excel sheet
			logger.log(Status.INFO, "The form is filled with the data got from the excel");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			Fname.sendKeys(FirstName);

			Lname.sendKeys(LastName);

			Select select = new Select(Jobfunction);
			select.selectByVisibleText(JobFunction);

			Jobtitle.sendKeys(JobTitle);

			EmailId.sendKeys(Email);

			Phone.sendKeys(phone);

			Institutename.sendKeys(InstituteName);

			book.close();
			Select select1 = new Select(InstituteTypedrop);
			select1.selectByVisibleText(InstituteType);

			Select select2 = new Select(PrimaryDisiplinedrop);
			select2.selectByVisibleText(PrimaryDisipline);

			Select select3 = new Select(Countrydrop);
			select3.selectByVisibleText(Country);

			Select select4 = new Select(Statedrop);
			select4.selectByVisibleText(State);

			logger.log(Status.PASS, "The form is filled");

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			logger.log(Status.INFO, "clicking the submit button");
			Submit.click();

			// to check if there is a error or invalid input
			String Expected_title = "Thank you - Coursera for Campus";
			String Actual_title = driver.getTitle();
			
			if (Expected_title.equalsIgnoreCase(Actual_title)) {
				logger.log(Status.PASS, "The submit button is selected");
			} else {
				// Capture the error message & display
				logger.log(Status.PASS, "Error : " + ErrorMessage.getText());
				System.out.println("Error message : " + ErrorMessage.getText());
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", FormHead);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				takeScreenShot(screenshot);
			}

			System.out.println("All details entered in the form");
			driver.switchTo().window(parentWindow);
			driver.close();

		}

		catch (Exception e) {
			System.out.println(e);
		}
	}
}
