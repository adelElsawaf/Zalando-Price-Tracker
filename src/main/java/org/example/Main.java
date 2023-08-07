package org.example;


import org.example.FileHandler.CsvFile;
import org.example.Items.Item;
import org.example.Items.ItemScraping;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        ItemScraping scrapeInformation = new ItemScraping("https://en.zalando.de/vans-ultrarange-exo-trainers-va215o04f-q11.html");
        List<Item> scrappedItems = scrapeInformation.startScraping();
        CsvFile itemsFile = new CsvFile("database/items_data.csv");
        List<String> rowsData = new ArrayList<>();
        if (!itemsFile.isExist()) {
            itemsFile.create();
            rowsData.add("Item Id,Brand Name,Model Name,Item Color ,Size,Availability,Before Discount Price,After Discount Price,Currency,Date");
        }
        for (Item scrappedItem : scrappedItems) {
            rowsData.add(scrappedItem.getId() + "," + scrappedItem.getBrandName() + "," + scrappedItem.getModelName() + "," + scrappedItem.getColor() + "," +
                    scrappedItem.getSizes() + "," + scrappedItem.getAvailability() + "," + scrappedItem.getPrice().getBeforeDiscount() + "," +
                    scrapeInformation.getItem().getPrice().getAfterDiscount() + ","
                    + scrappedItem.getPrice().getCurrency() + "," + scrapeInformation.getDate().toString());
            itemsFile.insert(rowsData);
            rowsData = new ArrayList<>();
        }

    }
//        List<String> itemsData = itemsFile.read();
//        for(String itemProperty :itemsData){
//            System.out.println(itemProperty);
//        }
//        System.out.println("------------------------------------------------------------------");
//        System.out.println(scrapeInformation.getItem().getBrandName());
//        System.out.println(scrapeInformation.getItem().getModelName());
//        System.out.println(scrapeInformation.getItem().getColor());
//        System.out.println(scrapeInformation.getItem().getPrice().getBeforeDiscount());
//        System.out.println(scrapeInformation.getItem().getPrice().getAfterDiscount());
//        System.out.println(scrapeInformation.getItem().getPrice().getCurrency());
//        System.out.println(scrapeInformation.getDate());


}
