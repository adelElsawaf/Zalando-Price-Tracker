package org.example.Items;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemScraping {
    private Item item;
    private Document jsoupScraper;
    private String URL;
    private LocalDate date;
    private WebDriver seleniumScraper;
    private WebElement allSizesDiv;


    public ItemScraping(String URL) throws IOException {
        this.URL = URL;
        this.jsoupScraper = Jsoup.connect(URL).get();
        ChromeOptions options = new ChromeOptions();
        seleniumScraper = new ChromeDriver(options);
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
    public  List<Item> startScraping() throws IOException {
        List<Item> scrappedItems = scrapeItemVariations();
        for (int i = 0; i < scrappedItems.size(); i++) {
            setItem(scrappedItems.get(i));
            System.out.println(item.getSizes());
            System.out.println(item.getPrice().getBeforeDiscount());
            System.out.println(item.getPrice().getAfterDiscount());
            System.out.println(item.getPrice().getCurrency().hashCode());
            System.out.println(item.getAvailability());
            System.out.println(item.getId());
            System.out.println("-----------------------------------------------------------------------");
        }
        setDate(LocalDate.now());
        System.out.println(getDate());
        return scrappedItems;
    }

    private List<Item> scrapeItemVariations() throws IOException {

        String brandName = jsoupScraper.getElementsByClass("_6zR8Lt QdlUSH FxZV-M HlZ_Tf _5Yd-hZ").text();
        String modelName = jsoupScraper.getElementsByClass("EKabf7 R_QwOV").text();
        String color = jsoupScraper.getElementsByClass("KxHAYs lystZ1 dgII7d HlZ_Tf zN9KaA").text();
        List<Item> scrappedItems = new ArrayList<>();
        int sizeElementIndex = 1;
        if (allSizesDiv != null) {
            while (true) {
                try {
                    String availability = scrapItemAvailability(sizeElementIndex);
                    String size = scrapSizeInSizesDiv(sizeElementIndex);
                    Price itemPrice = scrapItemPrice(sizeElementIndex);
                    scrappedItems.add(new Item(generateItemId(size),brandName, modelName, size, color, availability, itemPrice));
                    sizeElementIndex++;
                } catch (Error e) {
                    break;
                }

            }
        } else {
            // ask bondoq in sizeElementIndex
            String availability = scrapItemAvailability(sizeElementIndex);
            String size = scrapSizeInSizesDiv(sizeElementIndex);
            Price itemPrice = scrapItemPrice(sizeElementIndex);
            scrappedItems.add(new Item(generateItemId(size),brandName, modelName, size, color, availability, itemPrice));
        }
        return scrappedItems;
    }
    private String getItemIdFromURL(){
        return(URL.substring(URL.length()-18,URL.length()-5));
    }
    private String generateItemId(String itemSize){
        return (getItemIdFromURL()+"-"+itemSize);
    }
    private String scrapOriginalPriceMessage() {
        Elements originalPriceMessage = jsoupScraper.getElementsByClass("KxHAYs _4sa1cA FxZV-M Yb63TQ ZiDB59");
        if (originalPriceMessage.size() != 0)
            return originalPriceMessage.text();
        originalPriceMessage = jsoupScraper.getElementsByClass("KxHAYs lystZ1 FxZV-M Yb63TQ ZiDB59 _3LATVU");
        if (originalPriceMessage.size() != 0)
            return originalPriceMessage.text();
        originalPriceMessage = jsoupScraper.getElementsByClass("KxHAYs _4sa1cA FxZV-M HlZ_Tf");
        if (originalPriceMessage.size() != 0)
            return originalPriceMessage.text();

        return null;
    }

    private String scrapDiscountPriceMessage() {
        Elements discountPriceMessage = jsoupScraper.getElementsByClass("KxHAYs _4sa1cA dgII7d Km7l2y");
        if (discountPriceMessage.size() != 0)
            return discountPriceMessage.text();
        discountPriceMessage = jsoupScraper.getElementsByClass("KxHAYs _4sa1cA dgII7d Km7l2y _65i7kZ");
        if (discountPriceMessage.size() != 0)
            return discountPriceMessage.text();

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

    private String scrapItemAvailability(int elementIndex) {
        String availability = jsoupScraper.getElementsByClass("KxHAYs MnJKTe FxZV-M HlZ_Tf").text();
        if (availability == "") {
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



    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
