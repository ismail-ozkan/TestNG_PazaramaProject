package com.pazarama.tests;

import com.github.javafaker.Faker;
import com.pazarama.pages.CategoryPage;
import com.pazarama.pages.Homepage;
import com.pazarama.pages.LoginPage;
import com.pazarama.pages.MyFavoritesPage;
import com.pazarama.utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class AddToFavoriteTest extends BaseTest {


    @Test
    public void testAddToFavorite() {
        Homepage homepage = new Homepage();
        LoginPage loginPage = new LoginPage();
        CategoryPage categoryPage = new CategoryPage();
        MyFavoritesPage myFavoritesPage = new MyFavoritesPage();
        Faker faker = new Faker();

        // User already login and is on the home page
        homepage.goToHomepage();
        homepage.loginOrSignInButton.click();
        loginPage.loginWithValidCredential();

        do {
            // generate a random index to choose category randomly
            int randomCategoryNumber = faker.random().nextInt(homepage.listOfCategories.size());
            WebElement chosenCategory = homepage.listOfCategories.get(randomCategoryNumber);

            // User hover over the random category on the menu
            BrowserUtils.hover(chosenCategory);

            // Wait for visibility of the subcategories
            BrowserUtils.waitForVisibility(homepage.getListOfClickableSubcategories(randomCategoryNumber).get(0), 10);

            // in order to increase readability of code, I created a new object
            List<WebElement> listOfClickableSubcategories = homepage.getListOfClickableSubcategories(randomCategoryNumber);

            // Generate a "random number" according to number of clickable subcategories
            int randomSubcategoryNumber = faker.random().nextInt(listOfClickableSubcategories.size());

            // "print" chosen subcategory name and click it
            String chosenSubCategoryName = listOfClickableSubcategories.get(randomSubcategoryNumber).getText();
            System.out.println("Chosen Sub Category Name: " + chosenSubCategoryName);
            listOfClickableSubcategories.get(randomSubcategoryNumber).click();

        }// Check whether the subcategory has any items
        // If it has no items then do block execute again and again
        while (categoryPage.listOfProducts.size() == 0);

        // generate a "random number" according to total item count in chosen subcategory then use this "random number" to select the product randomly to add favorite
        int randomItemNumber = (categoryPage.getItemCountSubCategory() > 80) ? faker.random().nextInt(80) : faker.random().nextInt(categoryPage.getItemCountSubCategory());

        // find the item by scrolling down then add item to favorites
        while (categoryPage.listOfProducts.size() < randomItemNumber-1) {
            BrowserUtils.pageDown(2);
            BrowserUtils.waitFor(2);
            categoryPage.closeAdvertisementPopup();
        }
        categoryPage.closeAdvertisementPopup();
        BrowserUtils.waitForClickablityThenClick(categoryPage.likeButtonsOfProducts.get(randomItemNumber), 10);

        // Store the chosen item name for assertion
        String expectedItemName = categoryPage.listOfProductsNames.get(randomItemNumber).getText().trim();
        // User goes to my favorites page and asserts that the chosen item exists in my favorites page or not
        homepage.myFavoritesButton.click();
        BrowserUtils.waitFor(3);
        String actualItemName = myFavoritesPage.listOfLikedProductsNames.get(0).getText().trim();
        Assert.assertEquals(actualItemName, expectedItemName);

    }


}
