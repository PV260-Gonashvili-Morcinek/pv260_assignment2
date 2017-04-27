
import static junit.framework.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mari
 */
public class TestWriteanArticle {
    
    @org.junit.Test
    public void positive() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C://Users/Mari/Downloads/geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();String page="http://www.wikihow.com/Special:CreatePage";
        webDriver.get(page);
        WebElement title = webDriver.findElement(By.id("cp_title_input"));
        title.sendKeys("how to use selenium");
        webDriver.findElement(By.id("cp_title_btn")).click();
        Thread.sleep(10);
        
        WebDriverWait wait = new WebDriverWait(webDriver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cpr_title_hdr")));
        assertTrue(webDriver.getPageSource().contains("Are any of these topics the same as yours?"));
        
        webDriver.close();
    }
    
    @org.junit.Test
    public void negative() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C://Users/Mari/Downloads/geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();String page="http://www.wikihow.com/Special:CreatePage";
        webDriver.get(page);
        WebElement title = webDriver.findElement(By.id("cp_title_input"));
        title.sendKeys("how to use wikiHow");
        webDriver.findElement(By.id("cp_title_btn")).click();
        Thread.sleep(10);
        
        WebDriverWait wait = new WebDriverWait(webDriver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cpr_title_hdr")));
        assertTrue(webDriver.getPageSource().contains("That article already exists!"));
        
        webDriver.close();
    }
}
