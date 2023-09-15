package org.example.Items.ItemScraping;

import org.example.Items.Item;
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
    public List<Item> scrapeFileLinks(@RequestParam(name = "filePath") String filePath, @RequestParam(name = "sheetName") String sheetName) throws IOException {
        return itemScrappingService.scrapUrlsInFile(filePath, sheetName);
    }
    @GetMapping("/")
    public List<Item> scrape(@RequestParam(name = "Url") String itemUrl) {
        return itemScrappingService.scrapIndividualItem(itemUrl);
    }
}
