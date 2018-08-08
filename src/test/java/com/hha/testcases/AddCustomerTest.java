package com.hha.testcases;

import com.hha.base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddCustomerTest extends TestBase {
    @Test(dataProvider = "getData")
    public void addCustomer(String firstName, String lastName, String postCode, String alertText) throws InterruptedException {
        driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();

        driver.findElement(By.cssSelector(OR.getProperty("firstname"))).sendKeys(firstName);
        driver.findElement(By.cssSelector(OR.getProperty("lastname"))).sendKeys(lastName);
        driver.findElement(By.cssSelector(OR.getProperty("postcode"))).sendKeys(postCode);

        driver.findElement(By.cssSelector(OR.getProperty("addBtn"))).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        //Thread.sleep(3000);
        Assert.assertTrue(alert.getText().contains(alertText));
        alert.accept();

        Assert.fail("Customer not added successfully");
    }

    @DataProvider
    public Object[][] getData(){
        String sheetName = "AddCustomerTest";
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);
        System.out.println("rows: " + rows);
        System.out.println("cols: " + cols);

        Object[][] data = new Object[rows-1][cols];

        for (int rowNum = 2; rowNum <= rows; rowNum++){  //2
            for (int colNum = 0; colNum < cols; colNum++){
                Object obj = excel.getCellData(sheetName, colNum+1, rowNum);
                System.out.println("colNum: " + colNum + ", rowNum: " + rowNum + " - " + obj);
                data[rowNum-2][colNum] = obj;
            }
        }
        return data;
    }
}
