import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

public class TestNG_UseCaseMS2_Solution {
	private static WebDriver driver;

	@BeforeClass
	public static void preset() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// Navigate to https://www.moneycontrol.com/
	//1.	Mouse hover on Personal Finance and click on “Tools” under Explore. 
	@Test (priority=1)
	public static void testNavigateToHomeLoanEMI() throws IOException {
		try {

			String baseUrl = "https://www.moneycontrol.com/"; 

			driver.get(baseUrl);

			WebElement menuOption = driver.findElement(By.partialLinkText("Personal Finance"));

			org.openqa.selenium.interactions.Actions actions = new Actions(driver);

			actions.moveToElement(menuOption).perform();


			//Now click 'Tools' from sub menu Explore 
			WebElement subMenuOption = driver.findElement(By.linkText("Tools")); 

			actions.moveToElement(subMenuOption).perform();
			actions.click().perform();

			//Click on Home Loan EMI Calculator
			try {

				WebElement ele = driver.findElement(By.xpath("//a[contains(text(),'Home Loan')]"));

				WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Home Loan')]"))));
				System.out.println(ele.getAttribute("href"));

				((JavascriptExecutor)driver).executeScript("arguments[0].click();",ele);	

			}
			catch (StaleElementReferenceException e)
			{
				System.out.println(e.getMessage());
			}
			//	 yakshaAssert(currentTest(), IsHoverSuccessful, businessTestFile);

		}catch(Exception ex) {

			System.out.println("In catch exception " + ex);
			//yakshaAssert(currentTest(), false, businessTestFile);
		}
	}
	@Test (priority=2)
	public void TestCalculateHomeLoanEMI() throws InterruptedException
	{

		//id=carhome_loan 
		driver.findElement(By.id("carhome_loan")).clear();
		driver.findElement(By.id("carhome_loan")).sendKeys("3000000");

		//id=emi_start_from 

		Select select = new Select(driver.findElement(By.id("emi_start_from")));
		select.selectByVisibleText("At the time of loan disbursement");
		//At the time of loan disbursement
		driver.findElement(By.id("interest_rate")).clear();
		driver.findElement(By.id("interest_rate")).sendKeys("10");


		//Click submit button
		driver.findElement(By.linkText("Submit")).click();

		//Print Total Payment
		  
		WebElement totalPayment = new WebDriverWait(driver, Duration.ofSeconds(10))
		        .until(ExpectedConditions.presenceOfElementLocated(By.id("total_payment")));

		System.out.println("Total payment :" + totalPayment.getText());

		System.out.println("EMI :" + driver.findElement(By.id("emi")).getText());

	}
	@Test (priority=3)
	public void TestPrintTableDetails() throws InterruptedException
	{
		JavascriptExecutor jsx = (JavascriptExecutor)driver;
		jsx.executeScript("window.scrollBy(0,600)", "");

		
		System.out.println("--Seventh year data--");
		// EMI Payment in the year
		
		System.out.println("EMI Payment in the 7th year :" + driver.findElement(By.xpath("//table[@id=\"emi_table\"]/tbody/tr/td[1][text()='7']/following-sibling::td[2]")).getText());
		//Interest Payment in the year
		System.out.println("Interest Payment in the 7th year:" + driver.findElement(By.xpath("//table[@id=\"emi_table\"]/tbody/tr/td[1][text()='7']/following-sibling::td[3]")).getText());
		//Principal payment in the year. 
		System.out.println("Principal payment in the 7th year:"+driver.findElement(By.xpath("//table[@id=\"emi_table\"]/tbody/tr/td[1][text()='7']/following-sibling::td[4]")).getText());
		
		
		//11.	Print “Outstanding Principal at end of year” value for 5th year in the table.
		System.out.println("Fifth year data");
		//Print “Outstanding Principal at end of 5th year” value for 5th year in the table.
		System.out.println("Outstanding Principal at end of 5th year:"+driver.findElement(By.xpath("//table[@id=\"emi_table\"]/tbody/tr/td[1][text()='5']/following-sibling::td[1]")).getText());		

		System.out.println("**Use case Ms2 Ends**");



	}
}

