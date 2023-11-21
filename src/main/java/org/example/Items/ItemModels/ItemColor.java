package org.example.Items.ItemModels;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class ItemColor {
    private String color;
    private String imageUrl;

    public ItemColor(String color, String imageUrl) {
        this.color = color;
        setImageUrl(imageUrl);
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = replaceAfterSubstring(imageUrl, "imwidth=", "1800");
    }

    public static String replaceAfterSubstring(String input, String substring, String replacement) {
        int index = input.indexOf(substring);
        if (index != -1) {
            String prefix = input.substring(0, index + substring.length());
            return prefix + replacement;
        }

        return input;
    }
}
