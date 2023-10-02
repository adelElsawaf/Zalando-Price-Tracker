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
    private String color;
    private String availability;
    @Embedded
    private Price price;


    public ItemVariation(String size, String color, String availability, Price price) {
        this.size = size;
        this.color = color;
        this.availability = availability;
        this.price = price;
    }
}
