package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//package org.example;
//
//
//import org.example.FileHandler.Excel.XlsxFile;
//import org.example.Items.Item;
//import org.example.Items.ItemScraping;
//import org.example.Urls.ItemsUrlScrapping;
//import org.example.Urls.LeftNavbarUrlScrapping;
//
//
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.List;
//
@SpringBootApplication
@EnableScheduling
public class Main {

    public static void main(String[] args)  {
        SpringApplication.run(Main.class, args);
    }

//    public void run(String...args) throws Exception {
//        Dropbox testFile = new Dropbox();
//        testFile.upload("database/items_data.xlsx","/database/items_data.xlsx");
//        System.out.println(testFile.getAccountName());
//
//    }
}
