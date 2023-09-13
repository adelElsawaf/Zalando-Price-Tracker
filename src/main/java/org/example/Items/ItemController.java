package org.example.Items;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.example.FileHandler.*;

import java.io.IOException;
import java.util.ArrayList;
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
    public List<Item> readAllItems() {
        return itemService.readItems();
    }




}

