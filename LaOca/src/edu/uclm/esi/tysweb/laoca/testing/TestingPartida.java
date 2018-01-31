package edu.uclm.esi.tysweb.laoca.testing;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;

public class TestingPartida {
	private WebDriver playerA, playerB;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver","chromedriver" ); 
		playerA = new ChromeDriver();
		playerB = new OperaDriver();
		baseUrl = "http://172.19.156.188:8080/";
		playerA.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		playerB.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	@Test
	public void testPartidaA() throws Exception {
		playerA.get(baseUrl + "/LaOca/index/index.html");
		playerA.findElement(By.linkText("Juego")).click();
		playerA.findElement(By.id("nick_input")).clear();
		playerA.findElement(By.id("nick_input")).sendKeys("Hilario");
		playerA.findElement(By.id("play_button")).click();
		playerA.findElement(By.id("createSala_button")).click();
		playerA.findElement(By.id("createSala_input")).clear();
		playerA.findElement(By.id("createSala_input")).sendKeys("TestPartida");
		playerA.findElement(By.id("createSala_button")).click();
		
		playerB.get(baseUrl + "/LaOca/index/index.html");
		playerB.findElement(By.linkText("Juego")).click();
		playerB.findElement(By.id("nick_input")).clear();	
		playerB.findElement(By.id("nick_input")).sendKeys("Mestizo");
		playerB.findElement(By.id("play_button")).click();
		playerB.findElement(By.id("idSala_TestPartidabutton")).click();
		Thread.sleep(25000);
		playerA.findElement(By.cssSelector("button.botonlisto")).click();
		playerB.findElement(By.cssSelector("button.botonlisto")).click();
		Thread.sleep(10000);
		//condicion turnoA
		playerA.findElement(By.id("numeroTest")).clear();
		playerA.findElement(By.id("numeroTest")).sendKeys("4");
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		playerA.findElement(By.id("numeroTest")).clear();
		playerA.findElement(By.id("numeroTest")).sendKeys("3");
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		playerA.findElement(By.id("numeroTest")).clear();
		playerA.findElement(By.id("numeroTest")).sendKeys("1");
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		//condicionTurnoB
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("1");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		//condicionTurnoA
		playerA.findElement(By.id("numeroTest")).clear();
		playerA.findElement(By.id("numeroTest")).sendKeys("4");
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		//condicionTurnoB A Posada
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("4");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("1");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("5");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("3");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("2");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("1");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		//Turno playerA
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		playerA.findElement(By.id("numeroTest")).clear();
		playerA.findElement(By.id("numeroTest")).sendKeys("5");
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		playerA.findElement(By.id("numeroTest")).clear();
		playerA.findElement(By.id("numeroTest")).sendKeys("1");
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		//TurnoB
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("6");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		//TurnoA
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		//TurnoB
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("2");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		playerA.findElement(By.id("numeroTest")).clear();
		playerA.findElement(By.id("numeroTest")).sendKeys("4");
		playerA.findElement(By.cssSelector("button.botonTjuego")).click();
		//TurnoB
		playerB.findElement(By.id("numeroTest")).clear();
		playerB.findElement(By.id("numeroTest")).sendKeys("1");
		playerB.findElement(By.cssSelector("button.botonTjuego")).click();
		playerB.findElement(By.linkText("Inicio")).click();
		playerA.findElement(By.linkText("Inicio")).click();


		//



		/*driver.get(baseUrl + "/LaOca/index/index.html");
		      driver.findElement(By.linkText("Juego")).click();
		      driver.findElement(By.id("nick_input")).clear();
		      driver.findElement(By.id("nick_input")).sendKeys("B");
		      driver.findElement(By.id("play_button")).click();
		      driver.findElement(By.id("idSala_Testbutton")).click();
		      driver.findElement(By.xpath("//div[5]/div")).click();
		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("1");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();
		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("4");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();
		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("1");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();
		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("5");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();
		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("3");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();
		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("2");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();
		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("1");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();

		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("6");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();
		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("2");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();

		      driver.findElement(By.id("numeroTest")).clear();
		      driver.findElement(By.id("numeroTest")).sendKeys("1");
		      driver.findElement(By.cssSelector("button.botonTjuego")).click();
		      driver.findElement(By.linkText("Inicio")).click();*/


	}

	@After
	public void tearDown() throws Exception {
		playerA.quit();
		playerB.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			playerA.findElement(by);
			playerB.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			playerA.switchTo().alert();
			playerB.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alertA = playerA.switchTo().alert();
			Alert alertB = playerB.switchTo().alert();
			String alertTextA = alertA.getText();
			String alertTextB = alertB.getText();
			if (acceptNextAlert) {
				alertA.accept();
				alertB.accept();
			} else {
				alertA.dismiss();
				alertA.dismiss();
			}
			if(alertTextA != null)
				return alertTextA;
			else
				return alertTextB;
		} finally {
			acceptNextAlert = true;
		}
	}
}
