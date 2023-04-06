package Tests;

import Pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Objects;

public class ChangePostStatus extends BaseTest {


    @BeforeMethod()
    public void userLogin() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomepage();
        Header header = new Header(driver);
        header.goToLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyUrl();
        loginPage.login("Proba123568", "J12345688");
        //loginPage.login("zana", "600390");
    }

    @Test()
    public void TestChangeStatusOfRandomPost() {
        int initialAllPostsCount = 0;
        int finalAllPostsCount = 0;
        int initialPrivatePostsCount = 0;
        int finalPrivatePostsCount = 0;
        int initialPublicPostsCount = 0;
        int finalPublicPostsCount = 0;
        //1. Go to Profile page and check url
        Header header = new Header(driver);
        header.clickToProfile();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.getBaseUrl();

        //2. Go to   all  posts tab and get count of all posts
        profilePage.goToAllPosts();
        initialAllPostsCount = profilePage.getExistingPostCount();
        System.out.println("Initial all:" +initialAllPostsCount);
        if (initialAllPostsCount != 0) {
            //3. Go to   public  posts tab and get count of all public posts
            profilePage.goToPublicPosts();
            initialPublicPostsCount = profilePage.getExistingPostCount();
            System.out.println("Initial public:" +initialPublicPostsCount);
            //4. Go to   private  posts tab and get count of all private posts
            profilePage.goToPrivatePosts();
            initialPrivatePostsCount = profilePage.getExistingPostCount();
            System.out.println("Initial private:" +initialPrivatePostsCount);
            //5. Go to   all  posts tab and open random post
            profilePage.goToAllPosts();
            int r = profilePage.getRandom(initialAllPostsCount);
            System.out.println("Random post:" +r);
            profilePage.openPostByIndex(r);
            //6.Check post status
            PostModal postModal = new PostModal(driver);
            postModal.waitToDialogToAppear();
            String initialPostStatus = postModal.getPostStatus();
            System.out.println("Initial status:" +initialPostStatus);
            //7.1 If status is Private
            if (Objects.equals(initialPostStatus, "Private")) {
                //7.1.2 Click to unlock post
                postModal.lockUnlockPost();
                //7.1.3. Get and check toast message
                Assert.assertEquals(postModal.getToastMessageText(), "Post is now public");

            } //7.2.If status is Public
            else if ((Objects.equals(initialPostStatus, "Public"))) {
                //7.2.1 Click to lock post
                postModal.unlockLockPost();
                //7.2.2. Get and check toast message
                Assert.assertEquals(postModal.getToastMessageText(), "Post is now private");
                //7.1.4 Close postmodal window

            }
            //7.3 Close postmodal window
            postModal.closePostModal();

            //7.4 Go to all post tab and get actual post count
            profilePage.pageRefresh();
            profilePage.goToAllPosts();
            finalAllPostsCount = profilePage.getExistingPostCount();
            System.out.println("Final all:" +finalAllPostsCount);

            //7.5 Go to private post tab and get actual post count
            profilePage.goToPrivatePosts();
            finalPrivatePostsCount = profilePage.getExistingPostCount();
            System.out.println("Final private:" +finalPrivatePostsCount);

            //7.6 Go to public post tab and get actual post count
            profilePage.goToPublicPosts();
            finalPublicPostsCount = profilePage.getExistingPostCount();
            System.out.println("Final public:" +finalPublicPostsCount);

            //7.7 Check that oll post count is the same
            Assert.assertEquals(finalAllPostsCount, initialAllPostsCount);

            if (Objects.equals(initialPostStatus, "Private")) {
                //7.8.1Check that count of private post decrease with 1
                Assert.assertEquals(finalPrivatePostsCount, initialPrivatePostsCount - 1);

                //7.8.1 Check that count of Public post increase with 1
                Assert.assertEquals(finalPublicPostsCount, initialPublicPostsCount + 1);
            } else if ((Objects.equals(initialPostStatus, "Public"))) {

                //7.8.2 Check that count of private post increase with 1
                Assert.assertEquals(initialPrivatePostsCount + 1, finalPrivatePostsCount);
                //7.8.2 Check that count of Public post decrease with 1
                Assert.assertEquals(initialPublicPostsCount - 1, finalPublicPostsCount);


            }
        }
        else {
        System.out.println("There is no post to change the status of ");

        }
    }
}
