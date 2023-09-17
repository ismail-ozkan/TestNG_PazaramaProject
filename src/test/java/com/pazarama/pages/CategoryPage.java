package com.pazarama.pages;

import com.pazarama.utilities.BrowserUtils;
import com.pazarama.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CategoryPage extends BasePage {

    @FindBy(css = "iframe:nth-child(1)")
    public WebElement advertisementIframe;
    @FindBy(css = "div.closeBtn")
    public WebElement closeIframeButton;

    @FindBy(tagName = "iframe")
    public List<WebElement> listOfIframes;

    public void closeAdvertisementPopup() {
        if (listOfIframes.size() > 1) {
            try {
                Driver.getDriver().switchTo().frame(advertisementIframe);
                closeIframeButton.click();
                Driver.getDriver().switchTo().defaultContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FindBy(css = ".product-filter-sidebar h1")
    public WebElement subCategoryName;

    public String getSubCategoryName() {
        BrowserUtils.waitFor(3);
        return subCategoryName.getText();
    }

    @FindBy(css = ".product-filter-sidebar p")
    public WebElement itemCountSubCategory;

    public Integer getItemCountSubCategory() {
        String textOfItemCount = itemCountSubCategory.getText().trim().replace(".", "");
        return Integer.parseInt(textOfItemCount.substring(0, textOfItemCount.indexOf(" ")));
    }

    @FindBy(css = ".product-card")
    public List<WebElement> listOfProducts;

    @FindBy(css = ".product-card span g")
    public List<WebElement> likeButtonsOfProducts;

    @FindBy(css = ".line-clamp-2")
    public List<WebElement> listOfProductsNames;

    @FindBy(xpath = "//div[contains(text(),'Toplam')]")
    public WebElement totalDisplayedProductCount;

    @FindBy(css = ".select-wrapper button.justify-center")
    public WebElement sortingDropdown;

    @FindBy(css = "[role='option']:nth-child(2)")
    public WebElement increasingOrderOption;

    @FindBy(css = ".product-card__price .text-huge")
    public List<WebElement> listOfPrices;
}
