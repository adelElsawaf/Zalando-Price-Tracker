package org.example.Items;

import lombok.NoArgsConstructor;
import org.example.FileHandler.XlsxFile;
import org.example.Items.ItemScraping.ItemScrapingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Component
public class ItemService {
    @Autowired
    private static ItemRepository itemRepository;


    @Autowired

    public ItemService(ItemRepository itemRepository) {
        ItemService.itemRepository = itemRepository;
    }





    public static List<Item> readItems() {
        return itemRepository.findAll();
    }
}

