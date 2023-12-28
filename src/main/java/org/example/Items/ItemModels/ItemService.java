package org.example.Items.ItemModels;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.example.Items.ItemModels.Database.ItemDbRepository;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ItemService {
    private static ItemDbRepository itemDBRepository;

    public ItemService() {
    }

    @Autowired
    public ItemService(ItemDbRepository itemRepository) {
        ItemService.itemDBRepository = itemRepository;
    }

    public static List<ItemModel> readItems() {
        return itemDBRepository.findAll();
    }
    static ItemModel getItemById(String itemId) {
        return itemDBRepository.findById(itemId).get();
    }

    static List<Price> getPriceHistory(String itemId) {
        List<Price> priceHistoryList = itemDBRepository.findPriceById(itemId);
        return priceHistoryList;
    }
}

