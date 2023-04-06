package Tests;

import Pages.Header;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.PostModal;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeavingACommentOnARandomPost extends BaseTest{
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

    @Test
    public void TestLeavingACommentOnARandomPost() throws InterruptedException {
        //1. Gop to home page after login
        HomePage homePage = new HomePage(driver);

        //2. Count existing posts
        int countOfPosts = homePage.countPosts();

        //3.Check if posts count is greater than 0
        if (countOfPosts!=0) {

            //4. Open random post
            int r = homePage.getRandom(countOfPosts);
            homePage.openPostByIndex(r);

            //5. Get Initial count of comments
            PostModal postModal = new PostModal(driver);
            int initialCountCommentOfRandomPost = postModal.countExistingComment();
            //System.out.println("Initial count of comment : " + initialCountCommentOfRandomPost);

            //6. Post new comment
            int ran = postModal.getRandom(100000);
            String postComment = "Auto comment by Jani " + ran;
            postModal.writeComment(postComment);

            //7.Get actual count of comments
            int actualCommentCount = postModal.countExistingComment();
            //System.out.println("Existing count of comment : " + actualCommentCount);
            Assert.assertEquals(actualCommentCount, initialCountCommentOfRandomPost + 1, "The number of comments has not increased");

            //8. Get text of last comment
            String actualLastPostComment = postModal.getTextOfLastPostComment(actualCommentCount - 1);

            //9. comparison between posted comment and last comment of post
            Assert.assertEquals(actualLastPostComment, postComment,"\n" +
                    "The last comment is different from the expected comment");

            //10. Get last commented user
            String actualLastCommentedUser = postModal.getUserOfLastPostComment(actualCommentCount - 1);

            //11.Comparison  between last commented user and logged user
            Assert.assertEquals(actualLastCommentedUser, "Proba123568","The last user who left a comment is different from the logged user");

            // 12. Close post
            postModal.closePostModal();
        }
        //13. Print message that posts count is 0
        else {
            System.out.println(" Count of posts is 0. There is no post to leave a comment on.");
        }




    }
}
