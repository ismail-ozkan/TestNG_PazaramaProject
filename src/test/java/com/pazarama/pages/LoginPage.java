package com.pazarama.pages;

import com.pazarama.utilities.BrowserUtils;
import com.pazarama.utilities.ConfigurationReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "Username")
    public WebElement emailInputBox;
    @FindBy(id = "Password")
    public WebElement passwordInputBox;
    @FindBy(id = "submit-button")
    public WebElement submitButton;

    public void loginWithValidCredential() {
        Homepage homepage = new Homepage();
        // get the credentials from .properties file, it provides us to not use some critical data in our source code
        emailInputBox.sendKeys(ConfigurationReader.getProperty("email"));
        passwordInputBox.sendKeys(ConfigurationReader.getProperty("password"));
        submitButton.click();
        try {
            BrowserUtils.waitForClickablility(homepage.denyNotificationButton, 10);
            homepage.denyNotificationButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
