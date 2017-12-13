package edu.uclm.esi.tysweb.laoca.test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestingLogin {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testIngLogin() throws Exception {
    driver.get(baseUrl + "localhost:8080/LaOca/index/index.html");
    driver.findElement(By.linkText("Inicio")).click();
    driver.findElement(By.linkText("Registrarse")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("testing");
    driver.findElement(By.id("pwd1")).clear();
    driver.findElement(By.id("pwd1")).sendKeys("testing");
    driver.findElement(By.id("pwd2")).clear();
    driver.findElement(By.id("pwd2")).sendKeys("testing");
    driver.findElement(By.id("nick")).clear();
    driver.findElement(By.id("nick")).sendKeys("testing");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    assertEquals("La Oca", driver.getTitle());
    driver.findElement(By.linkText("Desconectarse")).click();
    driver.findElement(By.linkText("Identificarse")).click();
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("testing_failure");
    driver.findElement(By.id("pwd")).clear();
    driver.findElement(By.id("pwd")).sendKeys("testing");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    assertEquals("Email o contraseña incorrectos", closeAlertAndGetItsText());
    assertEquals("Identificarse", driver.getTitle());
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("testing");
    driver.findElement(By.id("pwd")).clear();
    driver.findElement(By.id("pwd")).sendKeys("testing");
    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
    assertEquals("La Oca", driver.getTitle());
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
