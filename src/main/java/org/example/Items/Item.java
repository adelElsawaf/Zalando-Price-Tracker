package org.example.Items;

public class Item {


    private String Id;
    private String brandName;
    private String modelName;
    private String size;
    private String color;
    private String availability;
    private Price price;

    public Item(String id,String brandName, String modelName , String size, String color, String isInStock, Price price) {
        setId(id);
        setBrandName(brandName);
        setColor(color);
        setSize(size);
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
    public Item(String brandName, String modelName,String size ,String color, String availability) {
        setBrandName(brandName);
        setColor(color);
        setSize(size);
        setInStock(availability);
        setModelName(modelName);

    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSizes() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getAvailability() {
        return availability;
    }

    public void setInStock(String inStock) {
        this.availability = inStock;
    }


    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
