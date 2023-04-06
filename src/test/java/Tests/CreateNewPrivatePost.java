package Tests;

import Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Random;

import java.io.File;

public class CreateNewPrivatePost extends BaseTest{
    @DataProvider(name= "users")
    public Object[][] getUser(){
        return new Object[][]{
                 {"Proba123568", "J12345688",  "Skillo Exam Post"},

        };

    }

    @BeforeTest
    public File ChoseRandomFile(){
        File[] files = new File(BaseTest.UPLOAD_DIR).listFiles();
        Random rand = new Random();
        return  files[rand.nextInt(files.length-1)];
    }
    @Test(dataProvider = "users")
    public void TestCreateNewPrivatePost(String username, String password, String captionText) {
        int initialExistingPrivatePostsCount=0;
        int finalExistingPrivatePostsCount=0;
        //1.Open Home page
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomepage();
        //2.Go to login page
        Header header = new Header(driver);
        header.goToLogin();
        //3.Verify Login Page Url
        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyUrl();
        //4. Login with username and password
        loginPage.login(username, password);
        //5. Go to Profile page and check url
        header.clickToProfile();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.getBaseUrl();
        //6. Go to  on private tab posts
        profilePage.goToPrivatePosts();
        //6. get current count of Private posts
        initialExistingPrivatePostsCount = profilePage.getExistingPostCount();

        //7.Go to new post
        header.clickToNewPost();
        //8.Verify Url of New post page
        NewPostPage postPage = new NewPostPage(driver);
        postPage.VerifyUrl();
        //9. upload new picture
        File file = ChoseRandomFile();
        postPage.UploadImage(file);
        //10.Verify that the image is visible
        postPage.waitForImageToShow();
        //11.Verify the image name is correct
        Assert.assertEquals(postPage.getImageFileName(), file.getName());
        //12.Check post status
        Assert.assertEquals(postPage.getPostStatusLable(),"Public");
        //13.Make post private
        postPage.makePostStatusPrivate();
        //14.Check new post status
        Assert.assertEquals(postPage.getPostStatusLable(),"Private");
       //15. Enter caption text
        postPage.populateCaption(captionText);
        //17. Submit post
        postPage.submitPost();
        //18.Check that toast messages appear
        Assert.assertEquals(postPage.getToastMessageText(),"Post created!","Тhe toast message is different than expected");
        //19.Check profile page url
        profilePage.getBaseUrl();

        //20.Go to private post tab
        profilePage.goToPrivatePosts();

        //21.Verify the post number has increase
        finalExistingPrivatePostsCount = profilePage.getExistingPostCount();
        Assert.assertEquals(finalExistingPrivatePostsCount, (initialExistingPrivatePostsCount + 1), "Count of existing private posts not equal to expected  ");
        // 22.Open the latest post
        profilePage.openPostByIndex(finalExistingPrivatePostsCount - 1);
        //23.Check username on modal window
        PostModal postModal = new PostModal(driver);
        postModal.waitToDialogToAppear();
        //24.verify the post details
        Assert.assertEquals(postModal.getUsernamefromModal(), username, "Username is not correct in the post");

    }






}


