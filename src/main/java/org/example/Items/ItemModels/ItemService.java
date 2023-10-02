package org.example.Items.ItemModels;

import lombok.NoArgsConstructor;
import org.example.Items.ItemModels.Database.ItemDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@NoArgsConstructor
@Component
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
}

