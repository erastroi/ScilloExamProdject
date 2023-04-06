package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.List;

public class NewPostPage extends BasePage {
    private  final    String url="http://training.skillo-bg.com:4300/posts/create";
    @FindBy(css=".image-preview")
    WebElement imagePreview;
    @FindBy(css="input.input-lg")
    WebElement fileNameField;
    @FindBy(name="caption")
    WebElement captionInput;
    @FindBy(id="create-post")
    WebElement createPostBtn;
    @FindBy(css="input.file[type='file']")
    WebElement fileUploadInput;

    @FindBy(css =".post-status-label.active" )
    WebElement postStausLable;


    public NewPostPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    public void VerifyUrl(){
        VerifyUrl(url);
    }

    public void UploadImage(File file){
        fileUploadInput.sendKeys(file.getAbsolutePath());
    }
    public void waitForImageToShow(){
        smallWait.until(ExpectedConditions.visibilityOf(imagePreview));
    }
    public String getImageFileName(){

        smallWait.until(ExpectedConditions.visibilityOf(fileNameField));
        return fileNameField.getAttribute("placeholder");
    }
    public void populateCaption(String captionText){
        enterText(captionInput,captionText);
    }
    public void submitPost(){
        clickElement(createPostBtn);
    }

    public String getPostStatusLable(){
        return getElementText(postStausLable);
    }
    public void makePostStatusPrivate(){
        postStausLable.click();
    }

}




