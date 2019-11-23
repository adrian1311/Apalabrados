package edu.uclm.esi.apalabreitor.apalabreitor;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestPartida2Jugadores {
  private WebDriver driver;
  private WebDriver driverJuan;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  System.setProperty("webdriver.chrome.driver", "C:/Users/Manu/Downloads/chromedriver.exe");
    driver = new ChromeDriver();
    driverJuan = new ChromeDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driverJuan.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
  
  private void login(WebDriver driverRec, String userName, String pwd) {
	  	//login(driverRec, userName,pwd);
	    driverRec.get("http://172.19.196.66:8080/");
	    driverRec.findElement(By.id("loginUserName")).click();
	    driverRec.findElement(By.id("loginUserName")).clear();
	    driverRec.findElement(By.id("loginUserName")).sendKeys(userName);
	    driverRec.findElement(By.id("loginPwd")).clear();
	    driverRec.findElement(By.id("loginPwd")).sendKeys(pwd);
	    driverRec.findElement(By.id("btnLogin")).click();
  }

  @Test
  public void testLoginManu() throws Exception {
	 login(driver, "manu","manu");
	 WebDriverWait wait = new WebDriverWait(driver,10);
	 WebElement message= driver.findElement(By.id("messageSalaDeEspera"));
	 wait.until(ExpectedConditions.textToBePresentInElement(message, "Bienvenid@"));
	 WebElement btnManu = driver.findElement(By.id("btnNuevaPartida"));
	 
	 login(driverJuan, "juan","juan");
	 wait = new WebDriverWait(driverJuan,10);
	 message= driverJuan.findElement(By.id("messageSalaDeEspera"));
	 wait.until(ExpectedConditions.textToBePresentInElement(message, "Bienvenid@"));
	 WebElement btnJuan = driverJuan.findElement(By.id("btnUnirAPartida"));
	 
	 btnManu.click();
	 Thread.sleep(1000);
	 
	 btnJuan.click();
	 
	 poner(driver,8,8,1); poner(driver,8,9,1); poner(driver,8,10,1);
	 poner(driver,8,11,1); poner(driver,8,12,1); poner(driver,8,13,1);
	 poner(driver,8,14,1);
	 driver.findElement(By.id("btnJugar")).click();
	
  }
  
  private void poner(WebDriver driver, int fila, int columna, int letra) {
	  WebElement casilla = driver.findElement(By.xpath("/html/body/div[2]/div[1]/table/tbody/tr["+fila+"]/td["+columna+"]"));
	  WebElement boton = driver.findElement(By.xpath("/html/body/div[2]/div[2]/button["+letra+"]"));
	  casilla.click();
	  boton.click();
	 
  }

  @After
  public void tearDown() throws Exception {
    //driver.quit();
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
