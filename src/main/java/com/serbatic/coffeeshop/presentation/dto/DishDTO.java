package com.serbatic.coffeeshop.presentation.dto;

public class DishDTO {
    private String dishName;
    private Double dishPrice;

    public DishDTO(String dishName, Double dishPrice) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
    }

    public DishDTO() {
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishName:'" + dishName + '\'' +
                ", dishPrice:" + dishPrice +
                '}';
    }
}
