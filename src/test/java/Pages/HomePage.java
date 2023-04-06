package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class HomePage extends BasePage {
    private static final String url = "http://training.skillo-bg.com:4300/posts/all";

    @FindBy (className = "post-feed-img")
    List<WebElement> postImages;


    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);    }

    public void  navigateToHomepage(){
        driver.get(url);
    }

    public void verifyUrl(){
        super.VerifyUrl(url);
    }

    public int countPosts(){
        return countDynamicElements(postImages);
    }

    public void openPostByIndex(int i){
        clickElement(postImages.get(i));
    }




}
