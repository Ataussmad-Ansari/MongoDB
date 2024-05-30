package com.example.usemongodb;

public class Food {
    private String name, calories, price, category_name;
    private int imageResId;

    public Food(String name, String calories, String price, String category_name, int imageResId) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.category_name = category_name;
        this.imageResId = imageResId;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}


