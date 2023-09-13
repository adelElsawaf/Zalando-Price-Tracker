package org.example.Items.ItemScraping;

import lombok.NoArgsConstructor;
import org.example.FileHandler.XlsxFile;
import org.example.Items.Item;
import org.example.Items.ItemRepository;
import org.example.Items.ItemService;
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

    public static List<Item> scrapUrlsInFile(String filePath, String sheetName) throws IOException {
        XlsxFile urlsSheetFile = new XlsxFile(filePath, sheetName);
        List<List<String>> allItemsUrls = urlsSheetFile.getFileData();
        List<Item> allItems = new ArrayList<>();
        for (int i = 0; i < allItemsUrls.size()-1; i++) {
            for (int j = 0; j < allItemsUrls.get(i).size(); j++) {
                ItemScrapingModel scrapeInformation = new ItemScrapingModel(allItemsUrls.get(i).get(j));
                Item scrappedItem = scrapeInformation.startScraping();
                allItems.add(scrappedItem);
            }
        }
        itemRepository.saveAll(allItems);
        return allItems;
    }
    public static Item scrapIndvidualItem(String itemUrl) {
        ItemScrapingModel scrappingObj = new ItemScrapingModel(itemUrl);
        Item scrappedItem = scrappingObj.startScraping();
        itemRepository.save(scrappedItem);
        return (scrappedItem);
    }

}
