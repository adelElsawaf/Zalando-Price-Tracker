package org.example.Items.ItemModels;

import lombok.NoArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/items")
@NoArgsConstructor
@CrossOrigin
public class ItemController {
    ItemService itemService = new ItemService();

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/readAll")
    public List<ItemModel> readAllItems() {
        return itemService.readItems();
    }

    @GetMapping("/{itemId}")
    public ItemModel getItemById(@PathVariable(name = "itemId") String itemId){
        return ItemService.getItemById(itemId);
    }
    @GetMapping("/{itemId}/price")
    public List<Price> getItemPriceHistory(@PathVariable(name = "itemId") String itemId, @RequestParam( name = "size") String size){
        return ItemService.getPriceHistory(itemId,size);
    }

}

