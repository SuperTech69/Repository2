package pack2;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class OurWebSite {
	WebDriver driver;
	
	@BeforeTest
	public void setup(){
		//System.setProperty("webdriver.ie.driver", "G:\\Software\\Selenium\\IEDriverServer.exe");
		//driver=new InternetExplorerDriver();
		//System.setProperty("webdriver.chrome.driver", "G:\\Software\\Selenium\\chromedriver_win32\\chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
		//driver=new ChromeDriver(); 
		driver=new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.manage().window().maximize();
		driver.get("file:///C:/Users/Admin/OneDrive/WebSite/SuperTech.html");
	}
	
	@AfterTest
	public void teardown(){
		driver.close();
	}
	
	@Test(priority=0)
	public void VerifyTitleAndScrollText(){
		
		String ActualTitle=driver.getTitle();
		String ExpectedTitle="Super Tech IT Solution";
		Assert.assertEquals(ActualTitle, ExpectedTitle);
		
		System.out.println("Main Window Title is : "+ActualTitle);
		String actualStext=driver.findElement(By.xpath(".//*[@id='linkForm']/header/marquee/font")).getText();
		Assert.assertEquals(actualStext, "Welcome to our IT Training Center. We are giving Traning on real project of Software Testing. Selenium with Java , TestNg, JUnit for functional Testing ** Cucumber for BDD Testing ** SoapUI for Webservice Testing ** MS SQL Server, MySql, Oracle Database for Backend Testing ** Appium for Mobile Testing ** Jmeter for Performance Testing**");
		System.out.println("Scroll Text is : "+driver.findElement(By.xpath(".//*[@id='linkForm']/header/marquee/font")).getText());
		
	}
	
	@Test(priority=1)
	public void ShowConfirmBoxTextVerify() throws InterruptedException{
		driver.findElement(By.xpath(".//*[@id='btnConfirm']")).click();
		Thread.sleep(5000);
		Alert alt1=driver.switchTo().alert();
		String actualText=alt1.getText();
		String expectedText="Chose an option.";
		Assert.assertEquals(actualText, expectedText);
		alt1.accept();
		Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='output']")).getText(), "Confirmed.");
	}
	@Test(priority=2)
	public void ShowAlertBoxTextVerify() throws InterruptedException{
		driver.findElement(By.xpath(".//*[@id='btnAlert']")).click();
		Thread.sleep(5000);
		Alert alt2=driver.switchTo().alert();
		String actualText=alt2.getText();
		String expectedText="I'm blocking!";
		Assert.assertEquals(actualText, expectedText);
		alt2.accept();
		Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='output']")).getText(), "Alert is gone.");
		
	}
	@Test(priority=3)
	public void ShowPromotBoxTextVerify() throws InterruptedException{
		driver.findElement(By.xpath(".//*[@id='btnPrompt']")).click();
		Thread.sleep(5000);
		Alert alt3=driver.switchTo().alert();
		String actualText=alt3.getText();
		String expectedText="What's the best web QA tool?";
		Assert.assertEquals(actualText, expectedText);
		alt3.accept();
		Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='output']")).getText(), "Selenium");
	}
	@Test(priority=4)
	public void SubMenuHandlingAndNewWindowTitleVerify() throws InterruptedException{
		
		WebElement socialMedia=driver.findElement(By.linkText("Social Media"));
		
		Actions act=new Actions(driver);
		
		act.moveToElement(socialMedia).build().perform();
		Thread.sleep(5000);
		driver.findElement(By.linkText("Google")).click();
		
		Thread.sleep(3000);
		String Parent_Window = driver.getWindowHandle();
		Set<String> Child_Window=driver.getWindowHandles();
		
		for (String Child_Window1 : Child_Window) { 
			driver.switchTo().window(Child_Window1);
			
		}
		
		String actualNewWindowTitle=driver.getTitle();
		String expectedNewWindoTitle="Google";
		Assert.assertEquals(actualNewWindowTitle, expectedNewWindoTitle);
		
		System.out.println("2nd Window Title is :"+actualNewWindowTitle);
	
		driver.findElement(By.name("q")).sendKeys("Selenium WebDriver");
		driver.findElement(By.name("btnG")).click();
		driver.findElement(By.partialLinkText("Selenium WebDriver")).click();
		
		System.out.println("3rd Window Title is : "+driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Selenium WebDriver - Google Search");
		Thread.sleep(3000);
		System.out.println("4th Window Title is : "+driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "Selenium WebDriver");
		
		driver.close();
		
		driver.switchTo().window(Parent_Window);
		
	}
	@Test(priority=5)
	public void TextBoxTestingAndTextVerify() throws InterruptedException{
		
		driver.findElement(By.xpath(".//*[@id='form2']/input[1]")).sendKeys("Mohammed");
		driver.findElement(By.xpath(".//*[@id='form2']/input[2]")).sendKeys("Kabir");
		//driver.findElement(By.xpath("html/body/form[1]/input[3]")).click();
		Thread.sleep(2000);
		String actualFNText=driver.findElement(By.xpath(".//*[@id='form2']/h[1]")).getText();
		String expectedFNText="First Name must be within 20 characters";
		Assert.assertEquals(actualFNText, expectedFNText);
		
		String actualLNText=driver.findElement(By.xpath(".//*[@id='form2']/h[2]")).getText();
		String expectedLNText="Last Name must be within 20 characters";
		Assert.assertEquals(actualLNText, expectedLNText);
	}
	@Test(priority=6)
	public void CheckBoxTesting () throws InterruptedException{
		boolean isBoxChecked=driver.findElement(By.xpath(".//*[@id='form3']/input[4]")).isSelected();
		System.out.println("Check box is Checked : "+isBoxChecked);
		WebElement blankBox=driver.findElement(By.xpath(".//*[@id='form3']/input[4]"));
		if(!isBoxChecked==true){
			blankBox.click();
		}
		Thread.sleep(1000);
		
	}
	
	@Test(priority=7)
	public void RadioButtonTesting () throws InterruptedException{
		boolean isMaleChecked=driver.findElement(By.xpath(".//*[@id='form4']/input[1]")).isSelected();
		System.out.println("Radio Button is Checked : "+isMaleChecked);
		WebElement blankRadioButton=driver.findElement(By.xpath(".//*[@id='form4']/input[1]"));
		if(!isMaleChecked==true){
			blankRadioButton.click();
		}
		Thread.sleep(1000);
		
	}
	@Test(priority=8)
	public void FruitsDropDownBoxTesting(){
		List<WebElement>FruitList=driver.findElement(By.xpath(".//*[@id='form5']/select[1]")).findElements(By.tagName("option"));
		int TotalFruits=FruitList.size()-1;
		System.out.println("Total fruits are : "+TotalFruits);
		for(int i=1; i<FruitList.size(); i++){
			System.out.println(i+". "+FruitList.get(i).getText());
		}
		Assert.assertEquals(TotalFruits, 5);
		
		Select FruitSelect=new Select(driver.findElement(By.xpath(".//*[@id='form5']/select[1]")));
		FruitSelect.selectByIndex(2);
	}
	@Test(priority=9)
	public void MonthDropDownBoxTesting() throws InterruptedException{
		List<WebElement>MonthList=driver.findElement(By.xpath(".//*[@id='form5']/select[2]")).findElements(By.tagName("option"));
		int TotalMonth=MonthList.size()-1;
		System.out.println("Total Months are : "+TotalMonth);
		for(int i=1; i<MonthList.size(); i++){
			System.out.println(i+". "+MonthList.get(i).getText());
		}
		Assert.assertEquals(TotalMonth, 12);
		Thread.sleep(1000);
		Select MonthSelect=new Select(driver.findElement(By.xpath(".//*[@id='form5']/select[2]")));
		MonthSelect.selectByVisibleText("June");
	}
	@Test(priority=10)
	public void DayDropDownBoxTesting() throws InterruptedException{
		List<WebElement>DayList=driver.findElement(By.xpath(".//*[@id='form5']/select[3]")).findElements(By.tagName("option"));
		int TotalDay=DayList.size()-1;
		System.out.println("Total Days are : "+TotalDay);
		for(int i=1; i<DayList.size(); i++){
			System.out.println(i+". "+DayList.get(i).getText());
		}
		Assert.assertEquals(TotalDay, 7);
		Thread.sleep(1000);
		Select DaySelect=new Select(driver.findElement(By.xpath(".//*[@id='form5']/select[3]")));
		DaySelect.selectByValue("fri");
	}
	@Test(priority=11)
	public void DoubleClickAndResultVerify() throws InterruptedException{
		
		WebElement CopyText=driver.findElement(By.xpath(".//*[@id='fullbody']/button"));
		
		Actions act=new Actions(driver);
		act.moveToElement(CopyText).doubleClick().perform();
		
		
		String ActualResult=driver.findElement(By.xpath(".//*[@id='field2']")).getAttribute("value");
		Assert.assertEquals(ActualResult, "Hello World!");
		Thread.sleep(3000);
		
		
	}

}
