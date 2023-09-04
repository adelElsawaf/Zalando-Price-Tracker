package org.example.Urls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
import java.util.List;

public class LeftNavbarUrlScrapping {
    private WebDriver seleniumScraper;

    public LeftNavbarUrlScrapping(String pageURL) {
        seleniumScraper = new EdgeDriver();
        seleniumScraper.get(pageURL);
        seleniumScraper.manage().window().maximize();
    }

    public List<String> getUrls() {
        List<WebElement> scrappedAnchorElements = seleniumScraper.findElements(By.cssSelector("a[class='Ny1wrl JT3_zV CKDt_l LyRfpJ HlZ_Tf DJxzzA wTFXcM Vp9qYE _5Yd-hZ']"));
        List<String> Urls = new ArrayList<>();
        for (int elementIndex = 0; elementIndex < scrappedAnchorElements.size(); elementIndex++) {
            scrappedAnchorElements.get(elementIndex).getAttribute("href");
            Urls.add(scrappedAnchorElements.get(elementIndex).getAttribute("href"));

        }
        return Urls;
    }
}
