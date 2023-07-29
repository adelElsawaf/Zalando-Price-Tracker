package org.example.Items;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;

public class ItemScraping {
    private Item item;
    private Document scraper;
    private String URL;
    private LocalDate date;

    public ItemScraping(String URL) throws IOException {
        this.URL = URL;
        this.scraper= Jsoup.connect(URL).get();
    }

    public Item startScraping() throws IOException {
        setItem(scrapeItem());
        setDate(LocalDate.now());
        return getItem();
    }

    private  Item scrapeItem() throws IOException {
        Elements brandName = scraper.getElementsByClass("_6zR8Lt QdlUSH FxZV-M HlZ_Tf _5Yd-hZ");
        Elements modelName = scraper.getElementsByClass("EKabf7 R_QwOV");
        Elements color = scraper.getElementsByClass("KxHAYs lystZ1 dgII7d HlZ_Tf zN9KaA");
        Elements availability = scraper.getElementsByClass("KxHAYs MnJKTe FxZV-M HlZ_Tf");
        return new Item(brandName.text(), modelName.text(), color.text(), availability.text(), scrapPrice());
    }

    private Price scrapPrice() throws IOException {
        Price itemPrice;
        Document scraper = Jsoup.connect(URL).get();
        Elements afterDiscountPriceMessage;
        Elements originalPriceMessage = scraper.getElementsByClass("KxHAYs _4sa1cA FxZV-M HlZ_Tf");
        if (originalPriceMessage.size() == 0) {
            originalPriceMessage = scraper.getElementsByClass("KxHAYs _4sa1cA FxZV-M Yb63TQ ZiDB59");
            if (originalPriceMessage.size() == 0) {
                originalPriceMessage = scraper.getElementsByClass("KxHAYs lystZ1 FxZV-M Yb63TQ ZiDB59 _3LATVU");
                afterDiscountPriceMessage = scraper.getElementsByClass("KxHAYs _4sa1cA dgII7d Km7l2y");
            } else {
                afterDiscountPriceMessage = scraper.getElementsByClass("KxHAYs _4sa1cA dgII7d Km7l2y _65i7kZ");
            }
            itemPrice = new Price(originalPriceMessage.text(), afterDiscountPriceMessage.text());
        } else {
            itemPrice = new Price(originalPriceMessage.text());
        }
        return itemPrice;
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
