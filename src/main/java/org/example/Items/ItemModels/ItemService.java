package org.example.Items.ItemModels;

import lombok.NoArgsConstructor;
import org.example.Items.ItemModels.Database.ItemDbRepository;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@NoArgsConstructor
@Component
@Service
public class ItemService {
    @Autowired
    private static ItemDbRepository itemRepository;


    @Autowired

    public ItemService(ItemDbRepository itemRepository) {
        ItemService.itemRepository = itemRepository;
    }

    public static List<ItemModel> readItems() {
        return itemRepository.findAll();
    }

//    public static String exportToFile(String destinationFilePath, String destinationSheetName) throws IOException, DbxException {
//        List<ItemModel> allItems = itemRepository.findAll();
//        XlsxFile itemsFile = new XlsxFile(destinationFilePath, destinationSheetName);
//        for (int i = 0; i < allItems.size(); i++) {
//            itemsFile.appendRow(allItems.get(i).getItemAsStringList());
//        }
//        DropboxModel.upload(destinationFilePath, "/database/items_data.xlsx");
//        return DropboxModel.getDownloadLink("/database/items_data.xlsx");
//    }
    static ItemModel getItemById(String itemId){
        return itemRepository.findById(itemId).get();
    }
    static List<Price> getPriceHistory(String itemId,String size ){
        List<String> priceStrings = itemRepository.getItemPriceHistory(itemId,size);
        List<Price> priceHistory = new ArrayList<>();
        for (String priceString: priceStrings) {
                priceHistory.add(Price.extractFromString(priceString));
        }
       return priceHistory;
    }
}

