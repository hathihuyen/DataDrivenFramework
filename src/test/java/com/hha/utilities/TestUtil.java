package com.hha.utilities;

import com.hha.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

public class TestUtil extends TestBase {
    public static String screenshotPath;
    public static String screenshotName;

    public static void captureScreenshot() throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Date date = new Date();
        screenshotName = date.toString().replace(":", "_").replace(" ", "_") + ".jpg";;
        screenshotPath = System.getProperty("user.dir") + "//target//surefire-reports//html//" + screenshotName;
        FileUtils.copyFile(scrFile, new File(screenshotPath));
    }

    @DataProvider(name="dp")
    public Object[][] getData(Method m){
        String sheetName = m.getName();
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
