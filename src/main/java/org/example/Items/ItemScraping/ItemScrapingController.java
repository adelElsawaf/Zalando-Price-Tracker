package org.example.Items.ItemScraping;

import com.dropbox.core.DbxException;
import org.example.Items.ItemModels.ItemModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("item-scrap")
public class ItemScrapingController {
    ItemScrappingService itemScrappingService;

    public ItemScrapingController() {
        itemScrappingService = new ItemScrappingService();
    }

    @GetMapping("/file")
    public String scrapeFileLinks(@RequestParam(name = "filePath") String filePath, @RequestParam(name = "sheetName") String sheetName) throws IOException, DbxException {
        return itemScrappingService.scrapUrlsInFile(filePath, sheetName);
    }
    @GetMapping("/file/dropbox")
    public String scrapeDropboxFileLinks(@RequestParam(name = "filePath") String filePath, @RequestParam(name = "sheetName") String sheetName) throws IOException, DbxException {
        return itemScrappingService.scrapUrlsInDropBoxFile(filePath, sheetName);
    }
    @GetMapping("/")
    public List<ItemModel> scrape(@RequestParam(name = "Url") String itemUrl) {
        return itemScrappingService.scrapIndividualItem(itemUrl);
    }
}
