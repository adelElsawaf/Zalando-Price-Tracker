package org.example.Items.ItemModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String entryId;
    private String size;
    private String availability;
    @ManyToOne
    @JoinColumn(name = "item_id",insertable = false,updatable = false)
    @JsonIgnore
    private ItemModel item;
    @Embedded
    private Price price;


    public ItemVariation(String size, String availability, Price price) {
        this.size = size;
        this.availability = availability;
        this.price = price;
    }
}
