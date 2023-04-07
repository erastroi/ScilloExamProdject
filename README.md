# SkilloExamProdject
This project is a final practical assignment for the course "Automation QA"

The project consists of developed 5 Java test scenarios using TestNG and Selenium WebDriver. The tests check the functionality of http://training.skillo-bg.com:4200/posts/all via Chrome browser.
The Page Object model and Page Factory were used for the development of the project.

For this purpose, all web element locators and methods related to a given page are collected in one Page Object
The following Page Objects have been created in the project – Base Page, Header, Home Page, Login Page, New Post Page, Post Modal, Profile Page. In the Base page, methods and objects common to all pages are defined, such as - WebDriver, Actions and different Wait durations.

A Base Test class is also created in which @Before and @After methods are created and global variables are defined. The @Before methods in this class initialize ChromeDriver and actions. The methods specify the directories in which to save the test results and screenshots in case of an error during the test execution. The @After method closes the browser and takes a screenshot in case of an error.
For each test, a separate class is created with the corresponding name.
1.	Create new private post

After a successful login, a new post is created with the status "Private" and a picture chosen randomly from the "Upload" directory. Before its publication, checks are made whether the picture is visible, whether its name is the same as the previously selected one. After publishing the new post, a check is made to see if the number of private posts has increased by 1.

2.	Follow/Unfollow random selected user from a search list

After a successful login, in the search field located at the top of each page, enter the symbol " ", which will start a search for all registered users. After they are listed, one user is randomly selected from all users. The link to his profile page opens in a new tab. The Follow/Unfollow button is clicked, which changes the status of the button and should change the statuses of both the logged in and the random user. Depending on the initial state of the Follow/Unfollow button, a check is made to see if the followers of the casual user and those followed by the logged-in user have changed in the relevant direction.

3.	Change public status of random selected existing post

After a successful login, the number of all posts is checked, as well as the number of public and private posts separately. A random one is chosen from all posts. It opens in a modal window and its status is changed using the corresponding button. A check is made for the appearance of a message and its text. A check is also made that the total number of posts has not changed. The number of public and private posts is also checked and compared to the initial values.

Note: there is a bug in the backend of the webpage which consists of the following: the total number of posts that are visible is greater than the number of actually created ones. Posting,what I believe, to be a "Public" post results in a double post creation. In the case when the randomly selected post is such, the test gives an error. For the User used in this test, these are posts selected by numbers 5-14.

4.	Change username and password of existing user

After successful login, go to the user's profile page. A modal window opens in which the new username and password are entered. Then the save button is pressed. After reloading the page, a check is made for matching the Url and whether the displayed username matches the new one. 
Note: after the procedure, the username is not changed and the test always ends with an error.

5.	Leaving a comment on a random selected post

After a successful login, the home page displays all private posts whose authors are followed by the logged-in user. After the posts are counted, one of them is randomly selected and a comment is left. A check is made to increase the number of comments for the selected post. The username of the logged in user is compared with the username of the last user who commented on the selected post. It also checks the text of the left comment for a match.


A testng.xml file is created in the project, the path to which is specified in the POM.xml file, enabling the tests to be run sequentially via Maven from the command line. In this case maven-surefire-plugin is used for listener.
