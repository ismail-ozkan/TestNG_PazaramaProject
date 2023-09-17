package com.pazarama.pages;

import com.pazarama.utilities.BrowserUtils;
import com.pazarama.utilities.ConfigurationReader;
import com.pazarama.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Homepage extends BasePage {

    @FindBy(css = "[href='/giris']")
    public WebElement loginOrSignInButton;

    @FindBy(css = ".leading-snug")
    public WebElement acceptCookiesButton;

    @FindBy(css = ".horizontal .dn-slide-deny-btn")
    public WebElement denyNotificationButton;

    public void goToHomepage() {
        Driver.getDriver().get(ConfigurationReader.getProperty("homepageUrl"));
        BrowserUtils.waitWithFluentWaitToClickElement(acceptCookiesButton, 10);
    }

    @FindBy(css = ".v-popover .w-full")
    public WebElement userFullName;
    @FindBy(css = "li.nav-item a")
    public List<WebElement> listOfCategories;

    public static List<WebElement> getListOfClickableSubcategories(int categoryNumber) {
        return Driver.getDriver().findElements(By.cssSelector(".h-17 li:nth-child(" + (categoryNumber + 1) + ") .mega-menu-category li a"));
    }

    @FindBy(css = "a .svg--pazarama-isbank-logo")
    public WebElement logo;
    @FindBy(css = "[href*='begendiklerim']")
    public WebElement myFavoritesButton;

    public void closeNotificationPopup() {
        try {
            BrowserUtils.waitForClickablility(denyNotificationButton, 10);
            denyNotificationButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
