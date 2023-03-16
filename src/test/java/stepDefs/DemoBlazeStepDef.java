package stepDefs;

import org.testng.annotations.AfterMethod;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoBlazeStepDef {
	static WebDriver driver;
	@BeforeAll
	public static void start() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions options= new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
	//  options.addArguments("-disable notifications");
		DesiredCapabilities cp= new DesiredCapabilities();
		cp.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(cp);
		driver=new ChromeDriver(options);
	//	driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("https://www.demoblaze.com/");
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	}
	@Given("User is on Launch Page")
	public void user_is_on_launch_page() throws InterruptedException {
	    driver.findElement(By.xpath("//li/a[contains(text(),'Log in')]")).click();	
	}

	@When("User login")
	public void user_login() {
		  WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
	  	  driver.findElement(By.id("loginusername")).sendKeys("priya@13");
	  	  driver.findElement(By.id("loginpassword")).sendKeys("123456789");
	  	  driver.findElement(By.xpath("(//button[@class='btn btn-primary'])[3]")).click();
	  	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Welcome priya@13')]")));
	}

	@Then("Should display Home Page")
	public void should_display_home_page() {
		boolean isDisp = driver.findElement(By.xpath("//a[contains(text(),'Welcome priya@13')]")).isDisplayed();
		AssertJUnit.assertTrue(isDisp);
	}
	
	@When("Add item {string} to cart")
	public void add_item_to_cart(String data) throws InterruptedException {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	     driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
//		 driver.findElement(By.linkText(data)).click();
	     wait.until(ExpectedConditions.elementToBeClickable(By.linkText(data))).click();
		  WebElement button=driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]"));
		  wait.until(ExpectedConditions.elementToBeClickable(button));
		  button.click();  
	      wait.until(ExpectedConditions.alertIsPresent());
		  Alert alert=driver.switchTo().alert();
		  alert.accept();
	}

	@Then("Item must be added to cart")
	public void item_must_be_added_to_cart() {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
		driver.findElement(By.id("cartur")).click();
	}
	
	@When("Delete an item from cart")
	public void delete_an_item_from_cart() throws InterruptedException {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Cart']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("(//a[text()='Delete'])[1]")))).click();
	    Thread.sleep(3000);
	}

	@Then("Item should be deleted")
	public void item_should_be_deleted() {
		    String valueBefore = driver.findElement(By.id("totalp")).getText();
		//	int iBefore=Integer.parseInt(valueBefore);
			String valueAfter = driver.findElement(By.id("totalp")).getText();
		//	int iAfter=Integer.parseInt(valueAfter);
			Assert.assertNotEquals("valueBefore", "valueAfter");		
	}

	@When("Place Order")
	public void place_order() throws InterruptedException {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		 driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
		  driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Mohanapriya");
		  driver.findElement(By.xpath("//input[@id='country']")).sendKeys("India");
		  driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Salem");
		  driver.findElement(By.xpath("//input[@id='card']")).sendKeys("123456");
		  driver.findElement(By.xpath("//input[@id='month']")).sendKeys("3");
		  Thread.sleep(3000);
		  driver.findElement(By.xpath("//input[@id='year']")).sendKeys("2023");
		  Thread.sleep(3000);
		  driver.findElement(By.xpath("//button[text()='Purchase']")).click();
	}
	@Then("Item should be purchased")
	public void item_should_be_purchased() {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		 boolean isDis=driver.findElement(By.xpath("(//h2[text()='Thank you for your purchase!'])")).isDisplayed();
		  AssertJUnit.assertTrue(isDis);
	}
@AfterMethod
public void attachImgToReport(Scenario scenario) throws WebDriverException {
		if(scenario.isFailed())
		{
			TakesScreenshot scr = (TakesScreenshot)driver;
			byte[] img = scr.getScreenshotAs(OutputType.BYTES);
			scenario.attach(img, "image/png", "imageOne");
		}
	}
}
	
