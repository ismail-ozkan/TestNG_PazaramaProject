package com.pazarama.tests;

import com.pazarama.pages.CategoryPage;
import com.pazarama.pages.Homepage;
import com.pazarama.utilities.BrowserUtils;
import com.pazarama.utilities.Driver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class SortingByPrice extends BaseTest {

    @Test
    public void testSortingByIncreasingPrice() {
        Homepage homepage = new Homepage();
        CategoryPage categoryPage = new CategoryPage();

        // User goes to homepage
        homepage.goToHomepage();

        // User goes to any subcategory page
        BrowserUtils.hover(homepage.listOfCategories.get(0));

        // Wait for visibility of the subcategories then click one
        BrowserUtils.waitForVisibility(homepage.getListOfClickableSubcategories(0).get(0), 10);
        homepage.getListOfClickableSubcategories(0).get(38).click();

        // User close advertisement and notification popup
        homepage.closeNotificationPopup();
        categoryPage.closeAdvertisementPopup();

        // Sorting by increasing price
        categoryPage.sortingDropdown.click();
        categoryPage.increasingOrderOption.click();
        // wait while items are reordered according to increasing price
        BrowserUtils.waitFor(2);

        // get all prices then compare with each other respectively
        // convert prices into Integer for comparison
        List<Integer> listOfPrice = categoryPage.listOfPrices.stream().map(p -> {
            String price = p.getText().trim().substring(0, p.getText().trim().indexOf(" ")).replace(",", "").replace(".", "");
            return Integer.parseInt(price);
        }).collect(Collectors.toList());

        // compare each price with next one for assertion
        for (int i = 0; i < listOfPrice.size() - 1; i++) {
            Assert.assertTrue(listOfPrice.get(i) <= listOfPrice.get(i + 1));
        }

        // get log records from browser to assert that  "Referrer Policy" parameter has  ‘no referrer-when-downgrade’ value
        LogEntries logEntries = Driver.getDriver().manage().logs().get(LogType.PERFORMANCE);
        for (LogEntry logEntry : logEntries) {
            String logMessage = logEntry.getMessage();
            if (logMessage.contains("documentURL") && logMessage.contains(Driver.getDriver().getCurrentUrl())) {
                System.err.println(logMessage.substring(logMessage.indexOf("\"referrerPolicy\""), logMessage.indexOf("\"referrerPolicy\"") + 47));
                Assert.assertTrue(logMessage.contains("no-referrer-when-downgrade"));
            }
        }

    }

}
