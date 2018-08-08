package com.hha.base;

import com.hha.utilities.ExcelReader;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    /*
     * WebDriver
     * Properties
     * Logs - Log4j jar, .log, log4j.properties, Logger
     * ExtentReports
     * DB
     * Excel
     * Mail
     * TestNG, ExtentReports
     * Jenkins
     */

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static String log4jConfPath = "//home//huyen//IdeaProjects//DataDrivenFramework//src//test//resources//properties//log4j.properties";
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "//src//test//resources//excel//testdata.xlsx");
    public static WebDriverWait wait;

    @BeforeSuite
    public void setUp() {
        PropertyConfigurator.configure(log4jConfPath);
        if(driver == null){
            try {
                fis = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//properties//Config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fis);
                log.debug("Config file loaded !!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fis = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//properties//OR.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                OR.load(fis);
                log.debug("OR file loaded !!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(config.getProperty("browser").equals("firefox")){
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//src//test//resources//executables//geckodriver.exe");
                driver = new FirefoxDriver();
                log.debug("FireFox Lauched !!!");
            } else if (config.getProperty("browser").equals("chrome")){
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//src//test//resources//executables//chromedriver.exe");
                driver = new ChromeDriver();
                log.debug("Chrome Lauched !!!");
            } else if (config.getProperty("browser").equals("ie")){
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//src//test//resources//executables//IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                log.debug("InternetExplorer Lauched !!!");
            }

            driver.get(config.getProperty("testsiteurl"));
            log.debug("Navigated to : " + config.getProperty("testsiteurl"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 5);
        }
    }

    public boolean isElementPresent(By by){
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }

    @AfterSuite
    public void tearDown() {
        if(driver != null){
            driver.quit();
        }

        log.debug("Test execution completed !!!");
    }
}
