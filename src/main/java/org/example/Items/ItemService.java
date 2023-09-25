package org.example.Items;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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





    public static List<ItemModel> readItems() {
        return itemRepository.findAll();
    }
}

