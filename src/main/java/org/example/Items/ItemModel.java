package org.example.Items;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@AllArgsConstructor
public class ItemModel {
    @Id
    private String itemId;
    private String brandName;
    private String modelName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            nullable = false,
            name = "item_id",
            referencedColumnName = "itemId"
    )
    private List<ItemVariation>variationList;

    public ItemModel(String id, String brandName, String modelName) {
        this.itemId = id;
        this.brandName = brandName;
        this.modelName = modelName;
        variationList = new ArrayList<>();
    }
}
