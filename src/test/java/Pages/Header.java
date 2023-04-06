package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Header extends BasePage {

    @FindBy(id = "homeIcon")
    WebElement homeIcon;
    @FindBy(id="nav-link-home")
    WebElement homeLink;
    @FindBy(id="nav-link-login")
    WebElement loginLink;
    @FindBy(id="nav-link-profile")
    WebElement profileLink;
    @FindBy(id="nav-link-new-post")
    WebElement newPostLink;
    @FindBy(className = "fa-sign-out-alt")
    WebElement logOutLink;
    @FindBy(className = "post-user")
    List<WebElement> allUserNames;
    @FindBy(css = "app-search-dropdown .btn-primary")
    List<WebElement> allUserFollowStatusBtn;

    @FindBy(id = "search-bar")
    WebElement searchBar;
    @FindBy(className = "dropdown-container")
    WebElement dropdownContainer;


    public Header(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public void goToLogin(){
        clickElement(loginLink);
    }

    public void clickToHome(){
         clickElement(homeLink);
    }

    public void clickToProfile(){
       clickElement(profileLink);
    }

    public void openProfilePageInNewTab(){
        clickElementOpenNewTab(profileLink);
    }
    public void clickToNewPost(){
        clickElement(newPostLink);
    }

    public void logOut(){
        clickElement(logOutLink);
    }

    public void searchForAllUser(){
        wait.until(ExpectedConditions.elementToBeClickable(searchBar));
        enterText(searchBar," ");
        superLongWait.until((ExpectedConditions.elementToBeClickable(dropdownContainer)));
    }

    public void verifyThatAllUserIsVisibile(){
        longWait.until(ExpectedConditions.visibilityOfAllElements(allUserNames));
    }

    public void  verifyThatAllUserIsPresence(){
        superLongWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("post-user")));}

    public int getCountAllUsers(){
        return allUserNames.size();
    }

    public String getUsernameOfRandomUser(int i){
        return  getElementText((allUserNames.get(i)));
    }

    public String getFollowStatusOfRandomUser(int i){
        return getElementText(allUserFollowStatusBtn.get(i));
    }

    public void openProfilePageOfRandomUserInNewTab(int i){
        clickElementOpenNewTab(allUserNames.get(i));
    }
    public void clickOnTheRUFollowBtn(int i){
        actions.moveToElement(allUserFollowStatusBtn.get(i));
        clickElement(allUserFollowStatusBtn.get(i));
    }


}
