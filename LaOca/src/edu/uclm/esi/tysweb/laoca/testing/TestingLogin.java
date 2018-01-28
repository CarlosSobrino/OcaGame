package edu.uclm.esi.tysweb.laoca.testing;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestingLogin {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	 System.setProperty("webdriver.chrome.driver","chromedriver.exe" ); 
	driver = new ChromeDriver();
    //driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testIngLogin() {
    try {
		driver.get(baseUrl + "LaOca/index/index.html");
		driver.findElement(By.linkText("Inicio")).click();
		driver.findElement(By.linkText("Registrarse")).click();
		System.out.println("Hasta aqui llegamos");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("testing");
		System.out.println("Hemos enviado email");
		driver.findElement(By.id("pwd1")).clear();
		driver.findElement(By.id("pwd1")).sendKeys("testing");
		System.out.println("Contraseña 1");
		driver.findElement(By.id("pwd2")).clear();
		driver.findElement(By.id("pwd2")).sendKeys("testing");
		System.out.println("Contraseña 2");
		driver.findElement(By.id("nick")).clear();
		driver.findElement(By.id("nick")).sendKeys("testing");
		System.out.println("nick");
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		assertEquals("La Oca", driver.getTitle());
		driver.findElement(By.linkText("Desconectarse")).click();
		driver.findElement(By.linkText("Identificarse")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("testing_failure");
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys("testing2");
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		assertEquals("Email o contraseña incorrectos", closeAlertAndGetItsText());
		assertEquals("Identificarse", driver.getTitle());
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("testing2");
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys("testing2");
		driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
		assertEquals("La Oca", driver.getTitle());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
