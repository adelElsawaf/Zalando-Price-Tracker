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
import java.util.Objects;

@Repository
public interface ItemDbRepository extends JpaRepository<ItemModel, String> {
    @Query("SELECT iv.price FROM ItemVariation iv WHERE iv.item.id = :id")
    List<Price> findPriceById(@Param("id") String id);

}
