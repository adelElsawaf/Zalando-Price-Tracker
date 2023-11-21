package org.example.Items.ItemModels.Database;

import com.dropbox.core.DbxException;
import org.example.FileHandler.DropBox.DropboxModel;
import org.example.FileHandler.Excel.XlsxFile;
import org.example.Items.ItemModels.ItemModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemFileRepository {

    public static String saveAllToExcelFile(String filePath, String sheetName, List<ItemModel> allItems) throws IOException, DbxException {
        XlsxFile itemsFile = new XlsxFile(filePath, sheetName);
        for (int i = 0; i < allItems.size(); i++) {
            List<String> itemAsStrings = new ArrayList<>();
            for (int j = 0; j < allItems.get(i).getVariationList().size(); j++) {
                itemAsStrings.add(allItems.get(i).getId());
                itemAsStrings.add(allItems.get(i).getBrandName());
                itemAsStrings.add(allItems.get(i).getModelName());
                itemAsStrings.add(allItems.get(i).getItemColor().getColor());
                itemAsStrings.add(allItems.get(i).getItemColor().getImageUrl());
                itemAsStrings.add(allItems.get(i).getVariationList().get(j).getSize());
                itemAsStrings.add(allItems.get(i).getVariationList().get(j).getAvailability());
                itemAsStrings.add(String.valueOf(allItems.get(i).getVariationList().get(j).getPrice().getBeforeDiscount()));
                itemAsStrings.add(String.valueOf(allItems.get(i).getVariationList().get(j).getPrice().getAfterDiscount()));
                itemAsStrings.add(allItems.get(i).getVariationList().get(j).getPrice().getCurrency());
                itemsFile.appendRow(itemAsStrings);
                itemAsStrings = new ArrayList<>();
            }
        }
        DropboxModel.upload(filePath, "/database/items_data.xlsx");
        return DropboxModel.getDownloadLink("/database/items_data.xlsx");
    }
}
