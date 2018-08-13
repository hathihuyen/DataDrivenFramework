package com.hha.testcases;

import com.hha.base.TestBase;
import com.hha.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddCustomerTest extends TestBase {
    @Test(dataProviderClass=TestUtil.class, dataProvider="dp")
    public void addCustomerTest(String firstName, String lastName, String postCode, String alertText) throws InterruptedException {
        click("addCustBtn_CSS");

        type("firstname_CSS", firstName);
        type("lastname_XPATH", lastName);
        type("postcode_CSS", postCode);

        click("addBtn_CSS");

        Thread.sleep(2000);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        Assert.assertTrue(alert.getText().contains(alertText));
        alert.accept();
    }
}
