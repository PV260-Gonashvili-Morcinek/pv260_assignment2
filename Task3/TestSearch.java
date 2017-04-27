
import static junit.framework.Assert.assertTrue;
import org.openqa.selenium.By;
import static org.openqa.selenium.Keys.NULL;
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
public class TestSearch {
    @org.junit.Test
    public void positiveSearchTest() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C://Users/Mari/Downloads/geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();
        webDriver.get("http://www.wikihow.com");
        WebElement searchField = webDriver.findElement(By.name("search"));
        searchField.sendKeys("how to use wikiHow");
        webDriver.findElement(By.id("search_site_bubble")).click();
    
        WebDriverWait wait = new WebDriverWait(webDriver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchresults_list")));
        assertTrue(webDriver.getPageSource().contains("views"));
        
        webDriver.close();
    }
    
    
    @org.junit.Test
    public void negativeSearchTest() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C://Users/Mari/Downloads/geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();
        webDriver.get("http://www.wikihow.com");
        WebElement searchField = webDriver.findElement(By.name("search"));
        searchField.sendKeys("sssssssssssssssssssssss");
        webDriver.findElement(By.id("search_site_bubble")).click();

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sr_noresults")));
        
        assertTrue(webDriver.getPageSource().contains("Please try another search"));
        

        webDriver.close();
    }
}
