package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PostModal extends BasePage{
    @FindBy(className = "post-user")
    WebElement user;

    @FindBy (tagName = "app-post-modal")
    WebElement modalDialog;
    @FindBy(className = "fa-lock")
    WebElement lockUnlock;
    @FindBy(className = "fa-unlock")
    WebElement unlockLock;
    @FindBy(className = "like")
    WebElement likeBtn;

    @FindBy(className = "fa-thumbs-down")
    WebElement dislikeBtn;

    @FindBy(css ="[placeholder='Comment here']" )
    WebElement CommentTextField;
    @FindBy(tagName ="app-comment")
    List<WebElement> existingPostComment;
    @FindBy(className = "comment-user")
    List<WebElement> commentedUser;

    @FindBy(className = "comment-content")
    List<WebElement> commentContent;



    public PostModal(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);}

    public String getUsernamefromModal(){
        return getElementText(user);
    }

    public void waitToDialogToAppear(){
        smallWait.until(ExpectedConditions.visibilityOf(modalDialog));

    }
    public void lockUnlockPost(){
        clickElement(lockUnlock);
    }

    public void unlockLockPost(){
        clickElement(unlockLock);
    }

    public String getPostStatus(){
        String postStatus ="";
        if  (driver.findElements(By.className( "fa-lock")).isEmpty()){
            postStatus = "Public";}
        else postStatus="Private";
        return  postStatus;
           }

    public void lakePost(){
        clickElement(likeBtn);
    }

    public void dislakePost(){
        clickElement(dislikeBtn);
    }

    public void writeComment (String text){
        enterText(CommentTextField, text);
        enter( CommentTextField);
    }

    public String getUserOfLastPostComment(int i) {
        WebElement lastUser = commentedUser.get(i);
        return lastUser.getText();
    }

    public String getTextOfLastPostComment(int i) {
            WebElement lastText = commentContent.get(i);
            return lastText.getText();
        }

    public int countExistingComment() throws InterruptedException {
        Thread.sleep(5000);

        return   existingPostComment.size();
    }

  public void closePostModal(){

      CommentTextField.sendKeys(Keys.ESCAPE);

  }
}
