package org.example;


import org.example.FileHandler.CsvFile;
import org.example.Items.ItemScraping;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        ItemScraping scrape = new ItemScraping("https://en.zalando.de/marc-opolo-mod-jasper-trainers-grey-ma312o03t-c11.html");
        scrape.startScraping();
        CsvFile itemsFile = new CsvFile("database/items_data.csv");
        List<String> rowsData = new ArrayList<>();
        if (!itemsFile.isExist()) {
            itemsFile.create();
            rowsData.add("Brand Name,Model Name,Item Color,In Stock,Before Discount Price,After Discount Price,Currency,Date");
        }
        rowsData.add(scrape.getItem().getBrandName() + "," + scrape.getItem().getModelName() + "," + scrape.getItem().getColor() + "," +
                scrape.getItem().getIsInStock() + "," + scrape.getItem().getPrice().getBeforeDiscount() + "," +
                scrape.getItem().getPrice().getAfterDiscount() + ","
                + scrape.getItem().getPrice().getCurrency() + "," + scrape.getDate().toString());

        itemsFile.insert(rowsData);
//        List<String> itemsData = itemsFile.read();
//        for(String itemProperty :itemsData){
//            System.out.println(itemProperty);
//        }
//        System.out.println("------------------------------------------------------------------");
//        System.out.println(scrape.getItem().getBrandName());
//        System.out.println(scrape.getItem().getModelName());
//        System.out.println(scrape.getItem().getColor());
//        System.out.println(scrape.getItem().getPrice().getBeforeDiscount());
//        System.out.println(scrape.getItem().getPrice().getAfterDiscount());
//        System.out.println(scrape.getItem().getPrice().getCurrency());
//        System.out.println(scrape.getDate());


    }
}