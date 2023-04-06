package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasePage {

    protected final WebDriver driver;
    protected Actions actions;
    protected WebDriverWait smallWait;
    protected WebDriverWait wait;
    protected WebDriverWait longWait;
    protected WebDriverWait superLongWait;
    @FindBy(css = "app-footer")
    WebElement footerContainer;

    public BasePage(WebDriver driver) {

        this.driver = driver;
        this.actions = new Actions(driver);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        longWait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
        smallWait = new WebDriverWait(this.driver, Duration.ofSeconds(5));
        superLongWait = new WebDriverWait(this.driver, Duration.ofSeconds(50));
    }

    protected void clickElement(WebElement element) {
        smallWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void clickElementOpenNewTab(WebElement element) {
        smallWait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            actions.keyDown(Keys.CONTROL).click(element).build().perform();
            Thread.sleep(300L);
        } catch (Exception e1) {
        }
    }

    protected void clickElementLongWait(WebElement element) {
        longWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void enterText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
    }

    protected void VerifyUrl(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }

    public String getElementText(WebElement element) {
        smallWait.until((ExpectedConditions.visibilityOf(element)));
        return element.getText();
    }

    public void pageRefresh() {
        driver.navigate().refresh();
    }

    public void enter(WebElement element) {
        element.sendKeys(Keys.ENTER);

    }

    public int getRandom(int bound) {
        Random ran = new Random();
        int r = ran.nextInt(bound - 1);
        return r;
    }

    public String getToastMessageText() {
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-message")));
        return toastMessage.getText().trim();
    }

    public Boolean isWebElementClassesContain(WebElement element, String text) {
        String clasess = element.getAttribute("class");

        return clasess.contains(text);
    }

    public int countDynamicElements(List<WebElement> element) {
        if (element.size() >= 1) {
            int numberOfPixelsToDragTheScrollbarDown = 10;
            for (int i = 50; i < 1000; i += numberOfPixelsToDragTheScrollbarDown) {

                try {
                    actions.moveToElement(footerContainer).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release().perform();
                    Thread.sleep(500L);

                } catch (Exception e1) {
                }

            }
            return element.size();
        } else return element.size();
    }


}
