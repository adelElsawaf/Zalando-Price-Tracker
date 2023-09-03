package org.example;


import org.example.FileHandler.XlsxFile;
import org.example.Items.Item;
import org.example.Items.ItemScraping;
import org.example.Urls.ItemsUrlScrapping;


import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void testUrlScrapping() throws IOException {
        ItemsUrlScrapping test = new ItemsUrlScrapping("https://en.zalando.de/outlet-mens-clothing/");
        List<String> testUrls = test.getItemsUrls();
        XlsxFile file = new XlsxFile("database/items_data.xlsx", "URLS");

        for (int i = 0; i < testUrls.size(); i++) {
            file.appendCell(0, testUrls.get(i));
            System.out.println(testUrls.get(i));
        }
    }


    public static void testMultipleItemScrapping() throws IOException {
        XlsxFile urlsSheetFile = new XlsxFile("database/items_data.xlsx", "URLS");
        XlsxFile itemsSheetFile = new XlsxFile("database/items_data.xlsx", "items_data");
        List<List<String>> allItemsUrls = urlsSheetFile.getFileData();
        for (int i = 0; i < allItemsUrls.size(); i++) {
            for (int j = 0; j < allItemsUrls.get(i).size(); j++) {
                ItemScraping scrapeInformation = new ItemScraping(allItemsUrls.get(i).get(j));
                List<Item> scrappedItems = scrapeInformation.startScraping();
                List<String> ItemData = new ArrayList<>();
                for (Item scrappedItem : scrappedItems) {
                    ItemData.add(scrappedItem.getId());
                    ItemData.add(scrappedItem.getBrandName());
                    ItemData.add(scrappedItem.getModelName());
                    ItemData.add(scrappedItem.getColor());
                    ItemData.add(scrappedItem.getSizes());
                    ItemData.add(scrappedItem.getAvailability());
                    ItemData.add(String.valueOf(scrappedItem.getPrice().getBeforeDiscount()));
                    ItemData.add(String.valueOf(scrappedItem.getPrice().getAfterDiscount()));
                    ItemData.add(scrappedItem.getPrice().getCurrency());
                    ItemData.add(scrapeInformation.getDate().toString());
                    itemsSheetFile.appendRow(ItemData);
                    ItemData = new ArrayList<>();
                }
            }
        }
    }

    public void testIndividualURL() throws IOException {
        ItemScraping scrapeInformation = new ItemScraping("https://en.zalando.de/pier-one-bow-tie-beige-pi952r02v-b12.html");
        List<Item> scrappedItems = scrapeInformation.startScraping();
        List<String> ItemData = new ArrayList<>();
        for (Item scrappedItem : scrappedItems) {
            ItemData.add(scrappedItem.getId());
            ItemData.add(scrappedItem.getBrandName());
            ItemData.add(scrappedItem.getModelName());
            ItemData.add(scrappedItem.getColor());
            ItemData.add(scrappedItem.getSizes());
            ItemData.add(scrappedItem.getAvailability());
            ItemData.add(String.valueOf(scrappedItem.getPrice().getBeforeDiscount()));
            ItemData.add(String.valueOf(scrappedItem.getPrice().getAfterDiscount()));
            ItemData.add(scrappedItem.getPrice().getCurrency());
            ItemData.add(scrapeInformation.getDate().toString());

        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        //testUrlScrapping();
        testMultipleItemScrapping();
        long end = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
//        ItemScraping scrapeInformation = new ItemScraping("https://en.zalando.de/vans-ultrarange-exo-trainers-va215o04f-q11.html");
//        List<Item> scrappedItems = scrapeInformation.startScraping();
//        XlsxFile test = new XlsxFile("database/items_data.xlsx", "items_data");
//        CsvFile itemsFile = new CsvFile("database/items_data.csv");
//        List<String> ItemData = new ArrayList<>();
//
//        for (Item scrappedItem : scrappedItems) {
//            ItemData.add(scrappedItem.getId());
//            ItemData.add(scrappedItem.getBrandName());
//            ItemData.add(scrappedItem.getModelName());
//            ItemData.add(scrappedItem.getColor());
//            ItemData.add(scrappedItem.getSizes());
//            ItemData.add(scrappedItem.getAvailability());
//            ItemData.add(String.valueOf(scrappedItem.getPrice().getBeforeDiscount()));
//            ItemData.add(String.valueOf(scrapeInformation.getItem().getPrice().getAfterDiscount()));
//            ItemData.add(scrappedItem.getPrice().getCurrency());
//            ItemData.add(scrapeInformation.getDate().toString());
//            test.appendRow(ItemData);
//            ItemData = new ArrayList<>();
//        }

    }
}



