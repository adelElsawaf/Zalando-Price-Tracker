package org.example;


import org.example.FileHandler.CsvFile;
import org.example.Items.ItemScraping;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        ItemScraping scrape = new ItemScraping();
        scrape.startScraping("https://en.zalando.de/nike-sportswear-air-max-270-trainers-ni112o00r-n11.html");
        CsvFile itemsFile = new CsvFile("database/items_data.csv");
        List<String> rowsData = new ArrayList<>();
        if (!itemsFile.isExist() ) {
            itemsFile.create();
            rowsData.add("Brand Name,Model Name,Item Color,Before Discount Price,After Discount Price,Currency,Date");
        }
        rowsData.add(scrape.getItem().getBrandName() +","+scrape.getItem().getModelName()+","+scrape.getItem().getColor()+","
                +scrape.getItem().getPrice().getBeforeDiscount()+","+scrape.getItem().getPrice().getAfterDiscount()+","
                +scrape.getItem().getPrice().getCurrency()+","+scrape.getDate().toString());

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