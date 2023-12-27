package org.example.Items.ItemScraping;

import com.dropbox.core.DbxException;
import lombok.NoArgsConstructor;
import org.example.FileHandler.DropBox.DropboxModel;
import org.example.FileHandler.Excel.XlsxFile;
import org.example.Items.ItemModels.Database.ItemFileRepository;
import org.example.Items.ItemModels.ItemModel;
import org.example.Items.ItemModels.Database.ItemDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ItemScrappingService {
    private static ItemDbRepository itemDbRepository;
    ItemScrappingService(){}
    @Autowired
    public ItemScrappingService(ItemDbRepository itemRepository) {
        ItemScrappingService.itemDbRepository = itemRepository;
    }

    public static String scrapUrlsInFile(String filePath, String sheetName) throws IOException, DbxException {
        XlsxFile urlsSheetFile = new XlsxFile(filePath, sheetName);
        ItemScrapingModel itemScrapper = new ItemScrapingModel();
        List<String> allItemsUrls = urlsSheetFile.getFileData();
        List<ItemModel> allItems = new ArrayList<>();
        for (int i = 0; i < allItemsUrls.size() - 1; i++) {
            System.out.println(allItemsUrls.get(i));
            ItemModel scrappedItem = itemScrapper.scrapIndividualItemUrl(allItemsUrls.get(i));
            allItems.add(scrappedItem);
        }
        itemDbRepository.saveAll(allItems);
        return ItemFileRepository.saveAllToExcelFile("database/test.xlsx","items_data",allItems);
    }

    public static String scrapUrlsInDropBoxFile(String filePath, String sheetName) throws IOException, DbxException {
        DropboxModel.download("/database/items_data.xlsx",
                filePath);
        return (scrapUrlsInFile(filePath, sheetName));
    }

    public static List<ItemModel> scrapIndividualItem(String itemUrl) {
        ItemScrapingModel itemScrapper = new ItemScrapingModel();
        List<ItemModel> allItemVariations = itemScrapper.scrapItemWithColorVariations(itemUrl);
        itemDbRepository.saveAll(allItemVariations);
        return (allItemVariations);
    }

}
