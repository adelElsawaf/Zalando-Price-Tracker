package org.example.Urls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
import java.util.List;

public class ItemsUrlScrapping {

    private WebDriver seleniumScraper;

    public ItemsUrlScrapping(String pageURL) {
        seleniumScraper = new EdgeDriver();
        seleniumScraper.get(pageURL);
    }

    public List<String> getItemsUrls() {
        List<String> itemsUrls = new ArrayList<>();
        for (int pageNumber = 0; pageNumber < 4; pageNumber++) {
            itemsUrls.addAll(getIndividualPageItemUrls());
            navigateToNextPage();
        }
        seleniumScraper.quit();
        return itemsUrls;
    }

    private List<String> getIndividualPageItemUrls() {
        List<WebElement> scrappedAnchorElements = seleniumScraper.findElements(By.cssSelector("a[class='_LM JT3_zV CKDt_l CKDt_l LyRfpJ']"));
        List<String> Urls = new ArrayList<>();
        for (int elementIndex = 0; elementIndex < scrappedAnchorElements.size(); elementIndex++) {
            String itemUrl = scrappedAnchorElements.get(elementIndex).getAttribute("href");
            if (isValidItemUrl(itemUrl)) {
                Urls.add(itemUrl);
            }
        }
        return Urls;
    }

    private boolean isValidItemUrl(String url) {
        if (url.contains(".html"))
            return true;
        return false;
    }

    private void navigateToNextPage() {
        List<WebElement> navigatorElements = seleniumScraper.findElements(By.cssSelector("a[class='DJxzzA DDVsUa'"));
        try {
            seleniumScraper.get(navigatorElements.get(1).getAttribute("href"));
        } catch (IndexOutOfBoundsException e) {
            seleniumScraper.get(navigatorElements.get(0).getAttribute("href"));
        }
    }

}
