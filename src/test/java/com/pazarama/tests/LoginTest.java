package com.pazarama.tests;

import com.pazarama.pages.Homepage;
import com.pazarama.pages.LoginPage;
import com.pazarama.utilities.BrowserUtils;
import com.pazarama.utilities.ConfigurationReader;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    @Test(testName = "Login with valid credentials")
    public void testValidLogin() {

        Homepage homepage = new Homepage();
        LoginPage loginPage = new LoginPage();

        // User goes to homepage
        homepage.goToHomepage();

        // User clicks login/signin button
        homepage.loginOrSignInButton.click();

        // User enters valid credentials then clicks submit button
        loginPage.loginWithValidCredential();

        // User redirects to home page then see user's full name on the page
        String userFullName = ConfigurationReader.getProperty("firstname") + " " + ConfigurationReader.getProperty("lastname");

        // Because of delayed displaying the full name we need to dynamic wait for assertion
        BrowserUtils.waitForVisibility(homepage.userFullName, 5);

        // Assert that the user login successfully
        Assert.assertEquals(homepage.userFullName.getText(), userFullName);

    }

    // we can reproduce @Test methods in this class, for example testLoginWithInvalidCredentials
}
