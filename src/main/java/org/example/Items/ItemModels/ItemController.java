package org.example.Items.ItemModels;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@NoArgsConstructor
public class ItemController {
    ItemService itemService = new ItemService();

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/readAll")
    public List<ItemModel> readAllItems() {
        return itemService.readItems();
    }




}

