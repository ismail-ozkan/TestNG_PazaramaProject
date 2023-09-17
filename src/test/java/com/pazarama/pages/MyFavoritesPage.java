package com.pazarama.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MyFavoritesPage extends BasePage {

    @FindBy(css = ".border .line-clamp-2")
    public List<WebElement> listOfLikedProductsNames;

}
