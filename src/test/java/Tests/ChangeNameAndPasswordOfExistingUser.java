package Tests;

import Pages.Header;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ChangeNameAndPasswordOfExistingUser extends BaseTest {

    @BeforeMethod()
    public void userLogin() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomepage();
        Header header = new Header(driver);
        header.goToLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyUrl();
        loginPage.login("zana1", "Jani123");
    }
    @Test
    public void TestChangeNameAndPasswordOfExistingUser(){
        //1. Go to profile page
        Header header = new Header(driver);
        header.clickToProfile();
        //2 Get current url for future comparison
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String userUrl = driver.getCurrentUrl();
        //System.out.println("User url is; "+userUrl);
        //3.Get initial profile name for future comparisons
        ProfilePage profilePage = new ProfilePage(driver);
        String initialProfileName =  profilePage.getProfileName();
        //4. Open modal window with user property
        profilePage.openUserProperties();
        //5.input new username
        String newUserName ="zana11";
        profilePage.enterNewUserName(newUserName);
        //6. Input new password
        String newPassword ="Jani1234";
        profilePage.enterNewPassword(newPassword);
        //7. Check that new username is valid
        Assert.assertEquals(profilePage.checkIsUserNameIsValid(), true);
        //8.Check that new password is valid
        Assert.assertEquals(profilePage.checkIsPasswordIsValid(),true);
        //9.Check that confirm password is valid
        Assert.assertEquals(profilePage.checkIsConfirmPasswordIsValid(), true);
        //10 save new credentials
        profilePage.saveNewCredentials();

        //11. close modal window
        profilePage.closeDialogForm();
        //12.refresh profile page
        profilePage.pageRefresh();

        //13. get new user url
        String userNewUrl = driver.getCurrentUrl();
        //14.Check that new user url is the same as initial user url
        Assert.assertEquals(userNewUrl,userUrl, "The new users URL is different than initial");

        //15. get new user profile name
        String newProfileName =  profilePage.getProfileName();

        //16. Check that the new username is the same as new profile name
        Assert.assertEquals(newProfileName,newUserName,"The New Profile name and New User name are different.");





    }


}
