package org.example.Items.ItemModels;

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
    @Embedded
    private Price price;


    public ItemVariation(String size, String availability, Price price) {
        this.size = size;
        this.availability = availability;
        this.price = price;
    }
}
