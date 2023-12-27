package org.example.Items.ItemScraping;

import org.example.Items.ItemModels.ItemColor;
import org.example.Items.ItemModels.ItemModel;
import org.example.Items.ItemModels.ItemVariation;
import org.example.Items.ItemModels.Price;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ItemScrapingModel {

    private  String URL;
    private  LocalDate date;
    private  WebDriver seleniumScraper;
    private  WebElement allSizesDiv;
    static int sizeDivIterator = 1;


    private  WebElement getAllSizesDiv() {
        WebElement allSizesDiv = null;
        try {
            WebDriverWait sizeDivOpener = new WebDriverWait(seleniumScraper, Duration.ofSeconds(10));
            sizeDivOpener.until(ExpectedConditions.elementToBeClickable((By.id("picker-trigger"))));
            WebElement sizeButton = seleniumScraper.findElement(By.id("picker-trigger"));
            sizeButton.click();
        } catch (TimeoutException timeOut) {
        }
        return allSizesDiv;
    }

    private  void openUrl(String URL) {
        seleniumScraper.get(URL);
    }

    private  void openFireFoxBrowser() {
        FirefoxOptions options = new FirefoxOptions();
       options.addArguments("--headless");
        seleniumScraper = new FirefoxDriver(options);
        seleniumScraper.manage().window().maximize();

    }

    private  void closeBrowser() {
        seleniumScraper.quit();
        seleniumScraper = null;
    }

    public  void prepareBrowser(String targetURL) {
        if (seleniumScraper == null) {
            openFireFoxBrowser();
            openUrl(targetURL);

        } else {
            openUrl(targetURL);
        }

    }

    public  List<ItemModel> scrapItemWithColorVariations(String itemUrl) {
        prepareBrowser(itemUrl);
        List<String> colorsVariationUrls = scrapColorVariationUrls();
        colorsVariationUrls.add(0, itemUrl);
        List<ItemModel> allItemVariations = new ArrayList<>();
        for (String colorsVariationUrl : colorsVariationUrls) {
            allItemVariations.add(scrapIndividualItemUrl(colorsVariationUrl));
        }
        return allItemVariations;

    }

    public  ItemModel scrapIndividualItemUrl(String itemUrl) {
        ItemModel scrappedItem = null;
        try {
            prepareBrowser(itemUrl);
            String brandName  =  seleniumScraper.findElement(By.cssSelector("h3[class='FtrEr_ QdlUSH FxZV-M HlZ_Tf _5Yd-hZ']")).getText();
            String modelName = seleniumScraper.findElement(By.cssSelector("span[class='EKabf7 R_QwOV']")).getText();
            String color = scrapItemColor();
            String itemImageUrl = scrapImageUrl();
            scrappedItem = new ItemModel(getItemIdFromURL(itemUrl), brandName, modelName,new ItemColor(color,itemImageUrl));
            allSizesDiv = getAllSizesDiv();
            if (allSizesDiv != null) {
                while (true) {
                    try {
                        String availability = scrapItemAvailability();
                        String size = scrapSizeInSizesDiv();
                        Price variationPrice = scrapItemPrice();
                        variationPrice.setScrappedAt(Date.valueOf(LocalDate.now()));
                        ItemVariation sizePriceVariation = new ItemVariation(size, availability, variationPrice);
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
                variationPrice.setScrappedAt(Date.valueOf(LocalDate.now()));
                scrappedItem.getVariationList().add(new ItemVariation(size, availability, variationPrice));

            }
            closeBrowser();
        } catch (Error anyError) {
            scrapIndividualItemUrl(itemUrl);
        }
        return scrappedItem;
    }

//    private String scrapItemBrandName(){
//        WebDriverWait wait = new WebDriverWait(seleniumScraper, Duration.ofSeconds(10));
//        WebElement element = wait.until(
//                ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3[class='FtrEr_ QdlUSH FxZV-M HlZ_Tf _5Yd-hZ']")));
//
//        return  element.getText();
//    }

    private  String scrapItemColor() {
        try {
            return seleniumScraper.findElement(By.cssSelector("span[class='sDq_FX lystZ1 dgII7d HlZ_Tf zN9KaA']")).getText();
        } catch (NoSuchElementException NoColorError) {
            return "ColourLess";
        }
    }

    private  String getItemIdFromURL(String itemUrl) {
        return (itemUrl.substring(itemUrl.length() - 18, itemUrl.length() - 5));
    }

    private  String scrapImageUrl() {
        String imageUrl = seleniumScraper.findElement(By.cssSelector("img[class='sDq_FX lystZ1 FxZV-M _2Pvyxl JT3_zV EKabf7 mo6ZnF _1RurXL mo6ZnF _7ZONEy']")).getAttribute("src");
        return imageUrl;
//                imageUrl.substring(0, imageUrl.length() - 3) + "1800";

    }

    private  String scrapOriginalPriceMessage() {
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

    private  String scrapDiscountPriceMessage() {
        List<WebElement> discountPriceMessage = seleniumScraper.findElements(By.cssSelector("span[class='sDq_FX _4sa1cA dgII7d Km7l2y']"));
        if (!discountPriceMessage.isEmpty())
            return discountPriceMessage.get(0).getText();
        discountPriceMessage = seleniumScraper.findElements(By.cssSelector("p[class='sDq_FX _4sa1cA dgII7d Km7l2y _65i7kZ']"));
        if (!discountPriceMessage.isEmpty())
            return discountPriceMessage.get(0).getText();
        return null;
    }

    private  Price scrapItemPrice() {
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

    private  String scrapPriceInSizesDiv() {
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

    private  boolean isItemAvailableInMainPage() {
        try {
            seleniumScraper.findElement(By.cssSelector("h2[class='sDq_FX MnJKTe FxZV-M HlZ_Tf']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private  String scrapItemAvailability() {
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

    private  String scrapAvailabilityInSizesDiv() {
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

    private  String scrapSizeInSizesDiv() {
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

    private void expandColorsSection() {
        try {
            WebElement expanderBtn = seleniumScraper.findElement(By.cssSelector("button[class='FCIprz K82if3 NN8L-8 cPQe5j Wu1CzW Rt7sMf _6-WsK3 Md_Vex Nk_Omi _MmCDa _0xLoFW KLaowZ mo6ZnF']"));
            expanderBtn.click();
        } catch (NoSuchElementException e) {
        }
    }

    private  List<String> scrapColorVariationUrls() {
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

    public  void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
