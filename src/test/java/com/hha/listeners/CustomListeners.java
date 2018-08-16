package com.hha.listeners;

import com.hha.base.TestBase;
import com.hha.utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;

import java.io.IOException;

public class CustomListeners extends TestBase implements ITestListener {
    public void onTestStart(ITestResult iTestResult) {
        //runmodes - Y
        if (!TestUtil.isTestRunnable(iTestResult.getName(), excel)){
            throw new SkipException("Skipping the test " + iTestResult.getName().toUpperCase() + "as the Run mode is N");
        }
        test = rep.startTest(iTestResult.getName().toUpperCase());
    }

    public void onTestSuccess(ITestResult iTestResult) {
        test.log(LogStatus.PASS, iTestResult.getName().toUpperCase() + " PASS");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestFailure(ITestResult iTestResult) {
        //make the display for html tag in report
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        Reporter.log("Click to see the screenshot ");
        try {
            TestUtil.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Extent Report
        test.log(LogStatus.FAIL, iTestResult.getName().toUpperCase() + " Failed with exception : " + iTestResult.getThrowable());
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

        //ReportNG
        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + ">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img srs=" + TestUtil.screenshotName + " height=200 width=200></img></a>");

        rep.endTest(test);
        rep.flush();
    }

    public void onTestSkipped(ITestResult iTestResult) {

        test.log(LogStatus.SKIP, "_Skipping the test " + iTestResult.getName().toUpperCase() + "as the Run mode is N");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {
    }

    public void onFinish(ITestContext iTestContext) {

    }
}
