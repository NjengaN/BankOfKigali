package bk;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;

import static org.testng.Assert.assertTrue;
import java.util.List;

public class Test_BK_Website {

	public WebDriver driver;

	@BeforeSuite
	public void beforeSuite() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\njeng\\Desktop\\QA\\Jars\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to("https://www.bk.rw/");
		driver.manage().window().maximize();
	}

	@Test(priority = 0)
	public void verifySections() throws Exception {
		try {
			assertTrue(driver.getTitle().contentEquals("BK | Personal"));
			assertTrue(driver.getPageSource().contains("Personal"), "Business");
			Thread.sleep(3000);
			// Scroll to the sections
			WebElement sections = driver.findElement(By.xpath("/html/body/div[4]/div"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sections);
			Thread.sleep(2000);
			List<WebElement> sectionHeading = sections.findElements(By.tagName("h3"));
			System.out.println("Sections Include:  ");
			for (int i = 0; i < sectionHeading.size(); i++) {
				// extracting tags text
				System.out.println(sectionHeading.get(i).getText());
				// Verify there are only four sections
				assertTrue(sectionHeading.size() == 4);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 1)
	public void openAccount() {
		WebElement openAccountsSections = driver.findElement(By.xpath("//div/ul"));
		List<WebElement> openAccountItems = openAccountsSections.findElements(By.tagName("a"));
		System.out.println("\n" + "Links under Account opening include:  ");
		for (int j = 0; j < openAccountItems.size(); j++) {
			// extracting tags text
			System.out.println(openAccountItems.get(j).getText());
			assertTrue(openAccountItems.size() == 7);
		}

	}

	@Test(priority = 2)
	public void exchangeRate() throws Exception {
		System.out.println("\n" + "EXCHANGE RATES:");
		WebElement exchangeRateTable = driver.findElement(By.xpath("/html/body/footer/div[1]/div[4]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", exchangeRateTable);
		Thread.sleep(2000);
		// Compare USD rate
		double usdBuy = Double.parseDouble(driver.findElement(By.xpath("//tr[2]/td[2]")).getText());
		double usdSell = Double.parseDouble(driver.findElement(By.xpath("//tr[2]/td[3]")).getText());
		System.out.println("BUYING: " + usdBuy + "\n" + "SELLING:" + usdSell + "\n");
		assertTrue((usdSell > 800 && usdSell > usdBuy));
		// Compare EUR rate
		double eurBuy = Double.parseDouble(driver.findElement(By.xpath("//tr[3]/td[2]")).getText().replace(",", ""));
		double eurSell = Double.parseDouble(driver.findElement(By.xpath("//tr[3]/td[3]")).getText().replace(",", ""));
		System.out.println("BUYING: " + eurBuy + "\n" + "SELLING:" + eurSell + "\n");
		assertTrue((eurSell > 800 && eurSell > eurBuy));
		// Compare GBP rate
		double gbpBuy = Double.parseDouble(driver.findElement(By.xpath("//tr[4]/td[2]")).getText().replace(",", ""));

		double gbpSell = Double.parseDouble(driver.findElement(By.xpath("//tr[4]/td[3]")).getText().replace(",", ""));
		System.out.println("BUYING: " + gbpBuy + "\n" + "SELLING:" + gbpSell + "\n");
		assertTrue((gbpSell > 800 && gbpSell > gbpBuy));
		Thread.sleep(3000);

	}

	@Test(priority = 3)
	public void currentAndSavings() throws Exception {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-2000)");
		Thread.sleep(3000);
		WebElement current_Savings = driver.findElement(By.xpath("//a[contains(text(),'Current & Savings ')]"));
		assertTrue(current_Savings.isDisplayed());
		current_Savings.click();
		Thread.sleep(2000);
		// Navigate to online Banking
		driver.findElement(By.xpath("//a[contains(text(),'Online Banking')]")).click();
		Thread.sleep(3000);
		assertTrue(driver.getTitle().contains("BK | Online Banking"));

	}

	@Test(priority = 4)
	public void verifyOnlineBankingApplication() throws Exception {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1000)");
		Thread.sleep(2000);
		WebElement btnApplyNow = driver.findElement(By.xpath("//a[contains(text(),'Apply Now')]"));
		assertTrue(btnApplyNow.isDisplayed());
		Thread.sleep(5000);
	}

	@AfterSuite
	public void afterSuite() {
		driver.quit();
	}

}
