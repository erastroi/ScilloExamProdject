package Tests;

import Pages.Header;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Follow_UnfollowRandomUser extends BaseTest {


    @BeforeMethod()
    public void userLogin() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomepage();
        Header header = new Header(driver);
        header.goToLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyUrl();
        loginPage.login("Proba123568", "J12345688");

    }

    @Test()
    public void TestFollow_UnfollowRandomUserFromSearchList(){
        int allUserCount=0;

        //1. Go to profile page
        Header header = new Header(driver);
        header.clickToProfile();

        // 2.Get count of my initial following
        ProfilePage myProfilePage = new ProfilePage(driver);
        int myInitialFollowing = myProfilePage.followingCount();
        //System.out.println("Count of my initial following user is :" + myInitialFollowing);

        //3. Search for all user and Wait until all user is visibility in drop down container
        header.searchForAllUser();


        //4.get count of all user
        allUserCount= header.getCountAllUsers();
        //System.out.println("Count of all users is: " +allUserCount);

        //5. Chose random user

        int r = header.getRandom(allUserCount);
        //System.out.println("Random User number is: " + r);

        //6.Get Name of Random user
        String randomUserName = header.getUsernameOfRandomUser(r);
        //System.out.println("Random User name is : " +randomUserName);

        //7.Get follow status of random user
        String randomUserFollowStatus = header.getFollowStatusOfRandomUser(r);
        //System.out.println("Random user follow status is: "+randomUserFollowStatus);

        //8.Open profile page of random user in new tab
        header.openProfilePageOfRandomUserInNewTab(r);

        //9. Click on the RU follow/unfollow button in search list
        header.clickOnTheRUFollowBtn(r);

        //10.Verify that toast message appear
        if (Objects.equals(randomUserFollowStatus, "Follow")) {
            Assert.assertEquals(header.getToastMessageText(),"You followed!", "Тhe toast message is different than expected");}
        else{
            Assert.assertEquals(header.getToastMessageText(),"You unfollowed!","Тhe toast message is different than expected");}

        //11.get my  new following user
        int myExistingFollowing = myProfilePage.followingCount();
        //System.out.println("Count of my existing following users is: "+myExistingFollowing);

        //12. Count open tab and give names to different tab
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        String myProfileWindows = windows.get(0);
        String randomUserProfileWindow = windows.get(1);

        //11.Switch driver to random user profile window
        driver.switchTo().window(randomUserProfileWindow);

        //12.check username on the profile page
        ProfilePage ruProfilePage = new ProfilePage(driver);
        Assert.assertEquals(ruProfilePage.getProfileName(),randomUserName,"Тhe profile name does not correspond to the pre-selected randomly");

        //13.get RU initial following user
        int ruInitialFollowers = myProfilePage.followersCount();
        //System.out.println("Count of initial followers of random user is :"+ruInitialFollowers);

        //14.Refresh RU profile page
        ruProfilePage.pageRefresh();

        //15. get RU actual count of following users
        int ruExistingFollowing = myProfilePage.followersCount();
        //System.out.println("Count of random user existing following users is : "+ ruExistingFollowing);

        String ruFollowBtnText = ruProfilePage.getFollowStatus();
        //16.Checking the followers, the following and foll. status that have been changed
        if (Objects.equals(randomUserFollowStatus, "Follow")) {
            Assert.assertEquals(myInitialFollowing+1,myExistingFollowing,"Count of my following user at the end of test is different than expected.  " );
            Assert.assertEquals(ruInitialFollowers+1,ruExistingFollowing, "Count of RandomUser followers at the end of test is different than expected. " );
            Assert.assertEquals(ruFollowBtnText,"Unfollow", "Actual buttons Text (Follow/Unfollow) on the profile page of Random User is different than expected  ");

        }
        else {
            Assert.assertEquals(myInitialFollowing-1,myExistingFollowing,"Count of my following user at the end of test is different than expected.  " );
            Assert.assertEquals(ruInitialFollowers-1,ruExistingFollowing,"Count of RandomUser followers at the end of test is different than expected. ");
            Assert.assertEquals(ruFollowBtnText,"Follow","Actual buttons Text (Follow/Unfollow) on the profile page of Random User is different than expected  ");
        }




    }


}
