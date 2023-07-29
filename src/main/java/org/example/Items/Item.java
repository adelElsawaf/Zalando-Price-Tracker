package org.example.Items;

public class Item {
    private String brandName;
    private String modelName;
    //private String size;
    private String color;
    private boolean IsInStock;
    private Price price;

    public Item(String brandName, String modelName, String color, boolean isInStock, Price price) {
        setBrandName(brandName);
        setColor(color);
        setInStock(isInStock);
        setModelName(modelName);
        setPrice(price);
    }

    public Item(String brandName, String modelName, String color, String availability, Price price) {
        setBrandName(brandName);
        setColor(color);
        setInStock(availability);
        setModelName(modelName);
        setPrice(price);
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getIsInStock() {
        return IsInStock;
    }

    public void setInStock(boolean inStock) {
        this.IsInStock = inStock;
    }

    public void setInStock(String availability) {
        if (availability.isEmpty()) {
            this.IsInStock = true;
        }
        else {
            this.IsInStock = false;
        }
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
