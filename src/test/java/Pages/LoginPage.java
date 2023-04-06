package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private static String url = "http://training.skillo-bg.com:4300/users/login";


    @FindBy(css="form .h4")
    WebElement signInHeader;
    @FindBy(id ="defaultLoginFormUsername")
    WebElement userNameFild;
    @FindBy(id = "defaultLoginFormPassword")
    WebElement passWordFild;
    @FindBy(id = "sign-in-button")
    WebElement signBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public void  navigateToLoginPage(){
        driver.get(url);
    }

    public void verifyUrl(){
        super.VerifyUrl(url);

    }

    public void enterUsername ( String username){
        enterText(userNameFild,username);
    }
    public void enterPassword ( String password){
        enterText(passWordFild,password);

    }

    public void clickSignIn (){
        clickElement(signBtn);//pri metod w base page

    }

    public String getSignHeaderTex(){
        super.wait.until(ExpectedConditions.visibilityOf(signInHeader));
        return signInHeader.getText();
    }

    public void  login(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }


}
