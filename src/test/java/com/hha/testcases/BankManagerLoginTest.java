package com.hha.testcases;

import com.hha.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class BankManagerLoginTest extends TestBase {
    @Test
    public void bankManagerLoginTest() throws InterruptedException, IOException {
        verifyEquals("xyz", "xyz");
        Thread.sleep(3000);

        log.debug("Inside Login Test");
        click("bmlBtn_CSS");
        //Thread.sleep(3000);
        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successfully");
        log.debug("Bank Manager Login successfully executed");

        //Assert.fail("Login not successfully");
    }
}
