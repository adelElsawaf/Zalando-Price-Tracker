package org.example.Items;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ItemScraping {
    private Item item;
    private Document doc;
    private String URL;
    private LocalDate date;

    public ItemScraping(String URL, LocalDate date) {
        this.URL = URL;
        this.date = date;
    }

    public ItemScraping() {
    }

    public  Item startScraping(String URL) throws IOException {
        Document doc = Jsoup.connect(URL).get();
        Price itemPrice;
        Elements brandName = doc.getElementsByClass("_6zR8Lt QdlUSH FxZV-M HlZ_Tf _5Yd-hZ");
        Elements modelName = doc.getElementsByClass("EKabf7 R_QwOV");
        Elements color = doc.getElementsByClass("KxHAYs lystZ1 dgII7d HlZ_Tf zN9KaA");
        Elements availability = doc.getElementsByClass("KxHAYs MnJKTe FxZV-M HlZ_Tf");
        Elements originalPriceMessage = doc.getElementsByClass("KxHAYs _4sa1cA FxZV-M HlZ_Tf");
        if (originalPriceMessage.size() == 0) {
            originalPriceMessage = doc.getElementsByClass("KxHAYs _4sa1cA FxZV-M Yb63TQ ZiDB59");
            Elements afterDiscountPriceMessage = doc.getElementsByClass("KxHAYs _4sa1cA dgII7d Km7l2y _65i7kZ");
            itemPrice = new Price(originalPriceMessage.text(), afterDiscountPriceMessage.text());
        }
        else {
            itemPrice = new Price(originalPriceMessage.text());
        }
        setItem(new Item(brandName.text(), modelName.text(), color.text(), availability.text(), itemPrice));
        setDate(LocalDate.now());
        return getItem();
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
