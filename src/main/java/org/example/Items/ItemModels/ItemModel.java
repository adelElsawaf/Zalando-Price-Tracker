package org.example.Items.ItemModels;

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
    private String id;
    private String brandName;
    private String modelName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            nullable = false,
            name = "item_id",
            referencedColumnName = "id"
    )
    private List<ItemVariation> variationList;

    public ItemModel(String id, String brandName, String modelName) {
        this.id = id;
        this.brandName = brandName;
        this.modelName = modelName;
        variationList = new ArrayList<>();
    }



}
