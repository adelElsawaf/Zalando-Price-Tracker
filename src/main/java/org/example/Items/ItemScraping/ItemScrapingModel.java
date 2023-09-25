package org.example.Items.ItemScraping;

import org.example.Items.ItemModel;
import org.example.Items.ItemVariation;
import org.example.Items.Price;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemScrapingModel {

    private static String URL;
    private static LocalDate date;
    private static WebDriver seleniumScraper;
    private static WebElement allSizesDiv;
    static int sizeDivIterator = 1;


    private static WebElement getAllSizesDiv() {
        WebElement allSizesDiv;
            WebDriverWait sizeDivOpener = new WebDriverWait(seleniumScraper, Duration.ofSeconds(10));
            sizeDivOpener.until(ExpectedConditions.elementToBeClickable((By.id("picker-trigger"))));
            WebElement sizeButton = seleniumScraper.findElement(By.id("picker-trigger"));
            sizeButton.click();
            try {
                WebDriverWait wait = new WebDriverWait(seleniumScraper, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.elementToBeClickable((By.className("F8If-J"))));
                allSizesDiv = seleniumScraper.findElement(By.className("F8If-J"));
            } catch (TimeoutException timeOut) {
                allSizesDiv = null;
            }
        return allSizesDiv;
    }
    private static void openUrl(String URL) {
        seleniumScraper.get(URL);
    }

    private static void openEdgeBrowser() {
        seleniumScraper = new EdgeDriver();
        seleniumScraper.manage().window().maximize();

    }

    private static void closeBrowser() {
        seleniumScraper.quit();
        seleniumScraper = null;
    }

    public static void prepareBrowser(String targetURL) {
        if (seleniumScraper == null) {
            openEdgeBrowser();
            openUrl(targetURL);

        }
        else {
            openUrl(targetURL);
        }

    }

    public static List<ItemModel> scrapItemWithColorVariations(String itemUrl) {
        prepareBrowser(itemUrl);
        List<String> colorsVariationUrls = scrapColorVariationUrls();
        colorsVariationUrls.add(0,itemUrl);
        List<ItemModel> allItemVariations = new ArrayList<>();
        for (String colorsVariationUrl : colorsVariationUrls) {
            allItemVariations.add(scrapIndividualItemUrl(colorsVariationUrl));
        }
        return allItemVariations;

    }

    public static ItemModel scrapIndividualItemUrl(String itemUrl) {
        prepareBrowser(itemUrl);
        String brandName = seleniumScraper.findElement(By.cssSelector("h3[class='FtrEr_ QdlUSH FxZV-M HlZ_Tf _5Yd-hZ']")).getText();
        String modelName = seleniumScraper.findElement(By.cssSelector("span[class='EKabf7 R_QwOV']")).getText();
        ItemModel scrappedItem = new ItemModel(getItemIdFromURL(itemUrl), brandName, modelName);
        allSizesDiv = getAllSizesDiv();
        String color = seleniumScraper.findElement(By.cssSelector("span[class='sDq_FX lystZ1 dgII7d HlZ_Tf zN9KaA']")).getText();
        if (allSizesDiv != null) {
            while (true) {
                try {
                    String availability = scrapItemAvailability();
                    String size = scrapSizeInSizesDiv();
                    Price variationPrice = scrapItemPrice();
                    ItemVariation sizePriceVariation = new ItemVariation(size, color, availability, variationPrice);
                    scrappedItem.getVariationList().add(sizePriceVariation);
                    sizeDivIterator++;
                } catch (Error e) {
                    sizeDivIterator = 1;
                    break;
                }
            }
        } else {
            String availability = scrapItemAvailability();
            String size = scrapSizeInSizesDiv();
            Price variationPrice = scrapItemPrice();
            scrappedItem.getVariationList().add((new ItemVariation(size, color, availability, variationPrice)));

        }
        closeBrowser();
        return scrappedItem;
    }


    private static String getItemIdFromURL(String itemUrl) {
        return (itemUrl.substring(itemUrl.length() - 18, itemUrl.length() - 5));
    }


    private static String scrapOriginalPriceMessage() {
        List<WebElement> originalPriceMessage = seleniumScraper.findElements(By.cssSelector("p[class='sDq_FX _4sa1cA FxZV-M Yb63TQ ZiDB59']"));
        if (!originalPriceMessage.isEmpty())
            return originalPriceMessage.get(0).getText();
        originalPriceMessage = seleniumScraper.findElements(By.cssSelector("span[class='sDq_FX lystZ1 FxZV-M Yb63TQ ZiDB59 _3LATVU']"));
        if (!originalPriceMessage.isEmpty())
            return originalPriceMessage.get(0).getText();
        originalPriceMessage = seleniumScraper.findElements(By.cssSelector("span[class='sDq_FX _4sa1cA FxZV-M HlZ_Tf']"));
        if (!originalPriceMessage.isEmpty())
            return originalPriceMessage.get(0).getText();
        originalPriceMessage = seleniumScraper.findElements(By.cssSelector("p[class='sDq_FX _4sa1cA FxZV-M HlZ_Tf']"));
        if (!originalPriceMessage.isEmpty())
            return originalPriceMessage.get(0).getText();
        return null;
    }

    private static String scrapDiscountPriceMessage() {
        List<WebElement> discountPriceMessage = seleniumScraper.findElements(By.cssSelector("span[class='sDq_FX _4sa1cA dgII7d Km7l2y']"));
        if (!discountPriceMessage.isEmpty())
            return discountPriceMessage.get(0).getText();
        discountPriceMessage = seleniumScraper.findElements(By.cssSelector("p[class='sDq_FX _4sa1cA dgII7d Km7l2y _65i7kZ']"));
        if (!discountPriceMessage.isEmpty())
            return discountPriceMessage.get(0).getText();
        return null;
    }

    private static Price scrapItemPrice() {
        String discountPriceMessage = scrapDiscountPriceMessage();
        String originalPriceMessage = scrapOriginalPriceMessage();
        if (discountPriceMessage == null) {
            return new Price(originalPriceMessage);
        } else {
            String itemPriceInSizeDiv = scrapPriceInSizesDiv();
            if (itemPriceInSizeDiv == null) {
                if (scrapAvailabilityInSizesDiv().contains("N"))
                    return new Price(originalPriceMessage);
                else
                    return new Price(originalPriceMessage, discountPriceMessage);
            } else {
                return new Price(originalPriceMessage, itemPriceInSizeDiv);
            }
        }
    }

    private static String scrapPriceInSizesDiv() {
        if (allSizesDiv != null) {
            WebElement itemPriceElement;
            try {
                itemPriceElement = allSizesDiv.findElement(By.cssSelector("div.YuYw-E:nth-child(" + sizeDivIterator + ") > div:nth-child(2) > label:nth-child(1) > span:nth-child(1) > div:nth-child(1) > span:nth-child(2)"));
            } catch (NoSuchElementException e) {
                return null;
            }
            return itemPriceElement.getText();
        }
        return null;
    }

    private static boolean isItemAvailableInMainPage() {
        try {
            seleniumScraper.findElement(By.cssSelector("h2[class='sDq_FX MnJKTe FxZV-M HlZ_Tf']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private static String scrapItemAvailability() {
        if (!isItemAvailableInMainPage()) {
            if (allSizesDiv != null) {
                String availabilityInSizeDiv = scrapAvailabilityInSizesDiv();
                if (availabilityInSizeDiv.contains("N")) {
                    return "Out Of Stock";
                } else {
                    return availabilityInSizeDiv;
                }
            } else {
                return "In Stock";
            }
        } else {
            return "Out Of Stock";

        }
    }

    private static String scrapAvailabilityInSizesDiv() {
        if (allSizesDiv != null) {
            WebElement itemAvailabilityElement;
            try {
                itemAvailabilityElement = allSizesDiv.findElement(By.cssSelector("div.YuYw-E:nth-child(" + sizeDivIterator + ") > div:nth-child(2) > label:nth-child(1) > div:nth-child(2) > span:nth-child(1)"));
            } catch (NoSuchElementException e) {
                return "In stock";
            }
            return itemAvailabilityElement.getText();
        }
        return "Out Of Stock";
    }

    private static String scrapSizeInSizesDiv() {
        if (allSizesDiv != null) {
            WebElement itemSizeElement;
            try {
                itemSizeElement = allSizesDiv.findElement(By.cssSelector("div.YuYw-E:nth-child(" + sizeDivIterator + ") > div:nth-child(2) > label:nth-child(1) > span:nth-child(1) > div:nth-child(1) > span:nth-child(1)"));
            } catch (NoSuchElementException e) {
                throw new Error("No More Elements");
            }
            return itemSizeElement.getText();
        }
        return "1n";
    }

    private static void expandColorsSection() {
        WebElement expanderBtn = seleniumScraper.findElement(By.cssSelector("button[class='FCIprz K82if3 NN8L-8 cPQe5j Wu1CzW Rt7sMf _6-WsK3 Md_Vex Nk_Omi _MmCDa _0xLoFW KLaowZ mo6ZnF']"));
        expanderBtn.click();
    }

    private static List<String> scrapColorVariationUrls() {
        int colorDivIterator = 2;
        expandColorsSection();
        List<String> allColorsUrls = new ArrayList<>();
        while (true) {
            try {
                String url = seleniumScraper.findElement(By.cssSelector("li._2ZBgf:nth-child(" + colorDivIterator + ") > div:nth-child(1) > a:nth-child(2)")).getAttribute("href");
                allColorsUrls.add(url);
                colorDivIterator++;
            } catch (NoSuchElementException e) {
                return allColorsUrls;
            }
        }

    }

    public static void setDate(LocalDate date) {
        ItemScrapingModel.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
