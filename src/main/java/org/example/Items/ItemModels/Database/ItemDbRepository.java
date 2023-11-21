package org.example.Items.ItemModels.Database;

import jakarta.persistence.TypedQuery;
import org.example.Items.ItemModels.ItemModel;
import org.example.Items.ItemModels.Price;
import org.postgresql.util.PGobject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDbRepository extends JpaRepository<ItemModel, String> {
    @Query(value = "select before_discount , after_discount , currency,scrapped_at from item_variation where item_variation.item_id = :itemId and size = :size",
            nativeQuery = true)
    List<String> getItemPriceHistory(@Param("itemId") String itemId,
                                       @Param("size") String size);

}
