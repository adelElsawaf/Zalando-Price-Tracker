package org.example.Items.ItemScraping;

import com.dropbox.core.DbxException;
import lombok.NoArgsConstructor;
import org.example.FileHandler.Dropbox;
import org.example.FileHandler.XlsxFile;
import org.example.Items.ItemModel;
import org.example.Items.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Component
public class ItemScrappingService {
    @Autowired
    private static ItemRepository itemRepository;
    @Autowired

    public ItemScrappingService(ItemRepository itemRepository) {
        ItemScrappingService.itemRepository = itemRepository;
    }

    public static List<ItemModel> scrapUrlsInFile(String filePath, String sheetName) throws IOException {
        XlsxFile urlsSheetFile = new XlsxFile(filePath, sheetName);
        List<String> allItemsUrls = urlsSheetFile.getFileData();
        List<ItemModel> allItems = new ArrayList<>();
        for (int i = 0; i < allItemsUrls.size()-1; i++) {
                ItemModel scrappedItem = ItemScrapingModel.scrapIndividualItemUrl(allItemsUrls.get(i));
                allItems.add(scrappedItem);
        }
        itemRepository.saveAll(allItems);
        return allItems;
    }
    public static List<ItemModel>scrapUrlsInDropBoxFile(String filePath, String sheetName) throws IOException, DbxException {
        Dropbox.download("/database/items_data.xlsx",
                filePath);
        return (scrapUrlsInFile(filePath,sheetName));
    }
    public static List<ItemModel> scrapIndividualItem(String itemUrl) {
        List<ItemModel>allItemVariations = ItemScrapingModel.scrapItemWithColorVariations(itemUrl);
        itemRepository.saveAll(allItemVariations);
        return (allItemVariations);
    }

}
