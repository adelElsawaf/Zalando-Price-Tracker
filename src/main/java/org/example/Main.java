package org.example;

import com.dropbox.core.DbxRequestConfig;
import org.example.FileHandler.Dropbox;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//package org.example;
//
//
//import org.example.FileHandler.XlsxFile;
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
