package org.example.Items.ItemModels.Database;

import org.example.Items.ItemModels.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDbRepository extends JpaRepository<ItemModel,String > {
        @Query(value = "select * from item_model inner join item_variation on item_model.id = item_variation.item_id order by item_model.id",
                nativeQuery = true)
            List<ItemModel>getAllItemsWithVariations();

}
