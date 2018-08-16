package DemoPack.DemoProject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import DemoPack.DemoProject.LoginPage.LoginPageUI;

public class TC_001 extends BasePage {

	public static final Logger log = Logger.getLogger(TC_001.class.getName());

	LoginPageUI page;

	@BeforeClass
	@Parameters("browser")
	public void startProcess() throws Exception {
		BrowserLaunch("chrome",getData("url"));
	}

	@Test
	public void customerRegistration() throws Exception {

		log.info("------Starting TC_001 Test------");

		page=new LoginPageUI(driver);
		page.customerRegistration();

		log.info("------Ending TC_001 Test------");
	}

	@AfterClass
	public void endProcess() {
		closeBrowser();
	}

	public void closeBrowser() {
		driver.quit();
		log.info("---Browser closed---");
		extent.endTest(test);
		extent.flush();
	}

	@BeforeMethod
	public void beforeMethod(Method result) {
		extent = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html");
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName()+"-------Test Started------");
	}

	@AfterMethod
	public void afterMethod(ITestResult iResult) throws Exception {
		getResult(iResult);
		
	}

	public void getResult(ITestResult iResult) throws Exception {

		if(iResult.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, iResult.getName()+" -----Test is passed----");
		}
		else if(iResult.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, iResult.getName()+" ----Test is Skipped and reason is--- "+iResult.getThrowable());
		}
		else if(iResult.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, iResult.getName()+" ----Test is Failed and the failed reason is---- "+iResult.getThrowable());
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		}
	}

	public String captureScreen(String fileName) {

		if(fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try 
		{
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return destFile.toString();
	}

}
