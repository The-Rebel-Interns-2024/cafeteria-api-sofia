package com.serbatic.coffeeshop.data.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Dish ID")
    private Long id;

    @Column(name = "dish_price")
    @Schema(description = "Price per dish")
    private Double dishPrice;

    @Column(name = "dish_date")
    @Schema(description = "Date in which this dish is served")
    private LocalDate dishDate;

    @Column(name = "dish_name")
    @Schema(description = "Name of the dish")
    private String dishName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public LocalDate getDishDate() {
        return dishDate;
    }

    public void setDishDate(LocalDate dishDate) {
        this.dishDate = dishDate;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", dishPrice=" + dishPrice +
                ", dishDate=" + dishDate +
                ", dishName='" + dishName + '\'' +
                '}';
    }
}
