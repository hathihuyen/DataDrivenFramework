package com.hha.testcases;

import com.hha.base.TestBase;
import com.hha.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OpenAccountTest extends TestBase {
    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void openAccountTest(String customer, String currency) throws InterruptedException {
    }
}
