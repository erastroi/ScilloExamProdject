package Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProfilePage extends BasePage{

    private static String baseUrl = "http://training.skillo-bg.com:4300/users";//4068";//ID User ????????

    @FindBy(css = "app-profile-section h2")
    WebElement profileName;

    @FindBy(css = "app-post")
    List<WebElement> existingPosts;

    @FindBy(css="app-post-list")
    WebElement allPostContainer;
    @FindBy(css ="app-footer")
    WebElement footerContainer;
    @FindBy(className = "btn-private")
    WebElement privatePostsBtn;
    @FindBy(className = "btn-all")
    WebElement allPostsBtn;
    @FindBy(className = "btn-public")
    WebElement publicPostsBtn;
    @FindBy(id = "followers")
    WebElement followersCount;

    @FindBy(id = "following")
    WebElement followingCount;

    @FindBy(className = "profile-edit-btn")
    WebElement folowUnfolowBtn;

    @FindBy(className = "fa-user-edit")
    WebElement userPropertyBtn;

    @FindBy(css ="input[formcontrolname='username']")
    WebElement inputUserName;

    @FindBy(css ="input[formcontrolname='password']")
    WebElement inputPassword;
    @FindBy(css ="input[formcontrolname='confirmPassword']")
    WebElement confirmPassword;


    @FindBy (css ="button[type='submit']")
    WebElement saveBtn;

    public ProfilePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getProfileName() {
        wait.until(ExpectedConditions.visibilityOf(profileName));
        return profileName.getText();
    }

    public void getBaseUrl() {
        longWait.until(ExpectedConditions.urlContains(baseUrl));
    }


    public int getExistingPostCount(){
        return countDynamicElements(existingPosts);
    }


    public void goToAllPosts(){
        actions.moveToElement(privatePostsBtn).click().perform();
        clickElement(allPostsBtn);
    }

    public void goToPublicPosts(){
        actions.moveToElement(privatePostsBtn).click().perform();;
        clickElement(publicPostsBtn);
    }

    public void goToPrivatePosts(){
        actions.moveToElement(privatePostsBtn).click().perform();
        clickElement(privatePostsBtn);
    }

    public void openPostByIndex(int index){
        clickElement(existingPosts.get(index));
    }
    
    public int followersCount(){
        String elementText = getElementText(followersCount);
        String result = elementText.substring(0,elementText.length()-10);
        return Integer.valueOf( result);
    }
    public int followingCount(){
        String elementText = getElementText(followingCount);
        String result = elementText.substring(0,elementText.length()-10);
        return Integer.valueOf( result);
    }

    public String getFollowStatus(){
        return getElementText((folowUnfolowBtn));
    }
    public void folowUnfolowAction(){
        clickElement(folowUnfolowBtn);
    }

    public void openUserProperties(){
        clickElement(userPropertyBtn);
    }

    public void enterNewUserName(String newUserName){
        inputUserName.clear();
        enterText(inputUserName,newUserName);
    }
    public Boolean checkIsUserNameIsValid(){
        return isWebElementClassesContain(inputUserName,"is-valid");
    }

    public Boolean checkIsPasswordIsValid(){
        return isWebElementClassesContain(inputPassword,"is-valid");
    }

    public Boolean checkIsConfirmPasswordIsValid(){
        return isWebElementClassesContain(confirmPassword,"is-valid");
    }
    public void enterNewPassword(String newPassword){
        enterText(inputPassword,newPassword);
        enterText(confirmPassword,newPassword);
    }

    public void saveNewCredentials(){
        clickElement(saveBtn);
    }

    public void closeDialogForm(){
        inputUserName.sendKeys(Keys.ESCAPE);
    }

}


