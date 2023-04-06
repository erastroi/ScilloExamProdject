package Tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected Actions actions;

    public static final String TEST_RESOURCES_DIR = "src" + File.separator + "test" + File.separator + "resources";
    public static final String SCREENSHOT_DIR = TEST_RESOURCES_DIR.concat(File.separator + "Screenshots");
    public static final String REPORT_DIR = TEST_RESOURCES_DIR.concat(File.separator + "Reports");
    public static final String UPLOAD_DIR = TEST_RESOURCES_DIR.concat(File.separator + "Upload");

//    @BeforeSuite
//    public void setupTest(ITestContext context,Method method) {
//        TestRunner runner = (TestRunner) context;
//        runner.setOutputDirectory(REPORT_DIR.concat(File.separator +method.getName()));
//        WebDriverManager.chromedriver().setup();
//
//
//
//    }

    @BeforeMethod
    public void setUpDriver(ITestContext context,Method method) throws IOException {
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        actions=    new Actions(driver);

        CleanDirectory(SCREENSHOT_DIR.concat(File.separator +method.getName()));
        CleanDirectory(REPORT_DIR.concat(File.separator +method.getName()));

        TestRunner runner = (TestRunner) context;
        runner.setOutputDirectory(REPORT_DIR.concat(File.separator +method.getName()));
        WebDriverManager.chromedriver().setup();
    }

    private void takeScreenShot(ITestResult testResult) {
        if (ITestResult.FAILURE == testResult.getStatus()) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            String testName = testResult.getName();
            try {
                FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIR + File.separator + testName+ File.separator + testName + ".jpg"));
            } catch (IOException e) {
                 throw new RuntimeException(e);
            }
        }

    }

    private void CleanDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()){
            directory.mkdirs();
        }
        FileUtils.cleanDirectory(directory);
        String[] fileList = directory.list();
        if (fileList != null && fileList.length == 0) {
            System.out.printf("All files are deleted in Directory: %s%n", directoryPath);
        } else {
            System.out.printf("Unable to delete files in Directory: %s%n", directoryPath);
        }

    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        takeScreenShot(testResult);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (this.driver!= null){
            this.driver.quit();}
    }
}



