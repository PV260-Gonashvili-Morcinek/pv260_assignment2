
import static junit.framework.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mari
 */
public class TestLogin {
     @org.junit.Test
    public void positivelogin() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C://Users/Mari/Downloads/geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();
        String loginpage="https://www.wikihow.com/index.php?title=Special:UserLogin&returnto=Main-Page&returntoquery=&type=login&fromhttp=1";
        webDriver.get(loginpage);
        
        WebElement username = webDriver.findElement(By.id("wpName1"));
        WebElement password = webDriver.findElement(By.id("wpPassword1"));
        username.sendKeys("Testuser2");
        password.sendKeys("testtest1");
        webDriver.findElement(By.id("wpLoginAttempt")).click();
        
        
        assertTrue(webDriver.getCurrentUrl().contains("Main-Page")); 
        webDriver.close();
    }
    
     @org.junit.Test
    public void negativelogin() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C://Users/Mari/Downloads/geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();String loginpage="https://www.wikihow.com/index.php?title=Special:UserLogin&returnto=Main-Page&returntoquery=&type=login&fromhttp=1";
        webDriver.get(loginpage);
        WebElement username = webDriver.findElement(By.id("wpName1"));
        WebElement password = webDriver.findElement(By.id("wpPassword1"));
        username.sendKeys("Testuser2");
        password.sendKeys("testtest11");
        webDriver.findElement(By.id("wpLoginAttempt")).click();
        Thread.sleep(10);
        
        assertTrue(webDriver.getCurrentUrl().contains("login"));    
        webDriver.close();
    }
}
