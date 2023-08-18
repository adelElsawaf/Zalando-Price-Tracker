package org.example.Items;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemScraping {

    private String URL;
    private LocalDate date;
    private WebDriver seleniumScraper;
    private WebElement allSizesDiv;


    public ItemScraping(String URL) {
        this.URL = URL;
        seleniumScraper = new EdgeDriver();
        seleniumScraper.get(URL);
        allSizesDiv = getAllSizesDiv();
    }

    private WebElement getAllSizesDiv() {
        WebElement allSizesDiv;
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

    public List<Item> startScraping() {
        List<Item> scrappedItems = scrapeItemVariations();
        setDate(LocalDate.now());
        seleniumScraper.quit();
        return scrappedItems;
    }

    private List<Item> scrapeItemVariations() {
        String brandName = seleniumScraper.findElement(By.cssSelector("h3[class='_6zR8Lt QdlUSH FxZV-M HlZ_Tf _5Yd-hZ']")).getText();
        String modelName = seleniumScraper.findElement(By.cssSelector("span[class='EKabf7 R_QwOV']")).getText();
        String color = seleniumScraper.findElement(By.cssSelector("p[class='KxHAYs lystZ1 dgII7d HlZ_Tf zN9KaA']")).getText();
        List<Item> scrappedItems = new ArrayList<>();
        int sizeDivIterator = 1;
        if (allSizesDiv != null) {
            while (true) {
                try {
                    String availability = scrapItemAvailability(sizeDivIterator);
                    String size = scrapSizeInSizesDiv(sizeDivIterator);
                    Price itemPrice = scrapItemPrice(sizeDivIterator);
                    scrappedItems.add(new Item(generateItemId(size), brandName, modelName, size, color, availability, itemPrice));
                    sizeDivIterator++;
                } catch (Error e) {
                    break;
                }

            }
        } else {
            // ask bondoq in sizeDivIterator
            String availability = scrapItemAvailability(sizeDivIterator);
            String size = scrapSizeInSizesDiv(sizeDivIterator);
            Price itemPrice = scrapItemPrice(sizeDivIterator);
            scrappedItems.add(new Item(generateItemId(size), brandName, modelName, size, color, availability, itemPrice));
        }
        return scrappedItems;
    }

    private String getItemIdFromURL() {
        return (URL.substring(URL.length() - 18, URL.length() - 5));
    }

    private String generateItemId(String itemSize) {
        return (getItemIdFromURL() + "-" + itemSize);
    }

    private String scrapOriginalPriceMessage() {
        List<WebElement> originalPriceMessage = seleniumScraper.findElements(By.cssSelector("p[class='KxHAYs lystZ1 FxZV-M Yb63TQ ZiDB59']"));
        if (!originalPriceMessage.isEmpty())
            return originalPriceMessage.get(0).getText();
        originalPriceMessage = seleniumScraper.findElements(By.cssSelector("p[class='KxHAYs lystZ1 FxZV-M Yb63TQ ZiDB59 _3LATVU']"));
        if (!originalPriceMessage.isEmpty())
            return originalPriceMessage.get(0).getText();
        originalPriceMessage = seleniumScraper.findElements(By.cssSelector("h3[class='KxHAYs _4sa1cA FxZV-M HlZ_Tf']"));
        if (!originalPriceMessage.isEmpty())
            return originalPriceMessage.get(0).getText();
        return null;
    }

    private String scrapDiscountPriceMessage() {
        List<WebElement> discountPriceMessage = seleniumScraper.findElements(By.cssSelector("p[class='KxHAYs _4sa1cA dgII7d Km7l2y']"));
        if (!discountPriceMessage.isEmpty())
            return discountPriceMessage.get(0).getText();
        discountPriceMessage = seleniumScraper.findElements(By.cssSelector("p[class='KxHAYs _4sa1cA dgII7d Km7l2y _65i7kZ']"));
        if (!discountPriceMessage.isEmpty())
            return discountPriceMessage.get(0).getText();
        return null;
    }

    private Price scrapItemPrice(int elementIndex) {
        String discountPriceMessage = scrapDiscountPriceMessage();
        String originalPriceMessage = scrapOriginalPriceMessage();
        if (discountPriceMessage == null) {
            return new Price(originalPriceMessage);
        } else {
            String itemPriceInSizeDiv = scrapPriceInSizesDiv(elementIndex);
            if (itemPriceInSizeDiv == null) {
                if (scrapAvailabilityInSizesDiv(elementIndex).contains("N"))
                    return new Price(originalPriceMessage);
                else
                    return new Price(originalPriceMessage, discountPriceMessage);
            } else {
                return new Price(originalPriceMessage, itemPriceInSizeDiv);
            }
        }
    }

    private String scrapPriceInSizesDiv(int elementIndex) {
        if (allSizesDiv != null) {
            WebElement itemPriceElement;
            try {
                itemPriceElement = allSizesDiv.findElement(By.cssSelector("div.YuYw-E:nth-child(" + elementIndex + ") > div:nth-child(2) > label:nth-child(1) > span:nth-child(1) > div:nth-child(1) > span:nth-child(2)"));
            } catch (NoSuchElementException e) {
                return null;
            }
            return itemPriceElement.getText();
        }
        return null;
    }
    private boolean isItemAvailableInMainPage(){
        try {
            seleniumScraper.findElement(By.cssSelector("h2[class='KxHAYs MnJKTe FxZV-M HlZ_Tf']"));
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }
    private String scrapItemAvailability(int elementIndex) {
        if (!isItemAvailableInMainPage()) {
            if (allSizesDiv != null) {
                String availabilityInSizeDiv = scrapAvailabilityInSizesDiv(elementIndex);
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

    private String scrapAvailabilityInSizesDiv(int elementIndex) {
        if (allSizesDiv != null) {
            WebElement itemAvailabilityElement;
            try {
                itemAvailabilityElement = allSizesDiv.findElement(By.cssSelector("div.YuYw-E:nth-child(" + elementIndex + ") > div:nth-child(2) > label:nth-child(1) > div:nth-child(2) > span:nth-child(1)"));
            } catch (NoSuchElementException e) {
                return "In stock";
            }
            return itemAvailabilityElement.getText();
        }
        return "Out Of Stock";
    }

    private String scrapSizeInSizesDiv(int elementIndex) {
        if (allSizesDiv != null) {
            WebElement itemSizeElement;
            try {
                itemSizeElement = allSizesDiv.findElement(By.cssSelector("div.YuYw-E:nth-child(" + elementIndex + ") > div:nth-child(2) > label:nth-child(1) > span:nth-child(1) > div:nth-child(1) > span:nth-child(1)"));
            } catch (NoSuchElementException e) {
                throw new Error("No More Elements");
            }
            return itemSizeElement.getText();
        }
        return "1n";
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
