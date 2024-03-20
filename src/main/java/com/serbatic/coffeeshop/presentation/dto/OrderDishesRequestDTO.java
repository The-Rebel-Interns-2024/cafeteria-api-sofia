package com.serbatic.coffeeshop.presentation.dto;

public class OrderDishesRequestDTO {
    private String dishName;
    private Integer quantity;

    public OrderDishesRequestDTO() {
    }

    public OrderDishesRequestDTO(String dishName, Integer quantity) {
        this.dishName = dishName;
        this.quantity = quantity;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "{dishName='" + dishName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
